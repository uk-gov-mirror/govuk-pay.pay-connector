package uk.gov.pay.connector.queue.tasks.handlers.adyen;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.pay.connector.events.model.payout.PayoutCreated;
import uk.gov.pay.connector.events.model.payout.PayoutUpdated;
import uk.gov.pay.connector.gateway.adyen.response.transfer.AdyenTransferNotification;
import uk.gov.pay.connector.gateway.adyen.response.transfer.TransferEventStatus;
import uk.gov.pay.connector.gateway.adyen.webhook.AdyenWebhookDeserialiser;
import uk.gov.pay.connector.gatewayaccountcredentials.service.GatewayAccountCredentialsService;
import uk.gov.pay.connector.payout.PayoutEmitterService;

public class AdyenTransferNotificationHandler {

    private final PayoutEmitterService payoutEmitterService;
    private final AdyenWebhookDeserialiser adyenWebhookDeserialiser;
    private final GatewayAccountCredentialsService gatewayAccountCredentialsService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AdyenTransferNotificationHandler.class);

    @Inject
    public AdyenTransferNotificationHandler(PayoutEmitterService payoutEmitterService,
                                            AdyenWebhookDeserialiser adyenWebhookDeserialiser,
                                            GatewayAccountCredentialsService gatewayAccountCredentialsService) {
        this.payoutEmitterService = payoutEmitterService;
        this.adyenWebhookDeserialiser = adyenWebhookDeserialiser;
        this.gatewayAccountCredentialsService = gatewayAccountCredentialsService;
    }

    @Transactional
    public void process(String payload) {

        var transferNotificationData = adyenWebhookDeserialiser.deserialisePayload(payload, AdyenTransferNotification.class).data();

        if (transferNotificationData == null) {
            LOGGER.error("Adyen transfer notification contains is empty");
            throw new RuntimeException("Adyen transfer notification contains is empty");
        }

        if (transferNotificationData.balanceAccount() == null) {
            LOGGER.error("Data for Adyen transfer notification is missing, event id: {}", transferNotificationData.id());
            throw new RuntimeException("Data for Adyen transfer notification is missing");
        }

        if ("bankTransfer".equals(transferNotificationData.type())) {

            var gatewayAccountId = gatewayAccountCredentialsService.findGatewayAccountForCredentialKeyAndValue(
                            "balance_account_id",
                            transferNotificationData.balanceAccount().id())
                    .getId();

            var status = TransferEventStatus.fromValue(transferNotificationData.status());
            switch (status) {
                case RECEIVED: {
                    var payoutCreatedEvent = PayoutCreated.from(transferNotificationData, gatewayAccountId);
                    payoutEmitterService.emitPayoutEvent(payoutCreatedEvent, gatewayAccountId.toString());
                    break;
                }
                case AUTHORISED, BOOKED: {
                    var payoutUpdatedEvent = PayoutUpdated.from(transferNotificationData);
                    payoutEmitterService.emitPayoutEvent(payoutUpdatedEvent, gatewayAccountId.toString());
                    break;
                }
                case FAILED, RETURNED, REFUSED: break;
                case null:
                    LOGGER.error("Could not determine status of transfer notification {} as status is missing", transferNotificationData.id());
                    throw new RuntimeException("Could not determine status of transfer notification " + transferNotificationData.id() + " as status is missing");
            }
        }
    }
}
