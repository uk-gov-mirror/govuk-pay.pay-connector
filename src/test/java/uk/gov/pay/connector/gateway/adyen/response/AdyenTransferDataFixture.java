package uk.gov.pay.connector.gateway.adyen.response;

import uk.gov.pay.connector.gateway.adyen.request.json.Amount;
import uk.gov.pay.connector.gateway.adyen.response.transfer.AdyenAccountHolder;
import uk.gov.pay.connector.gateway.adyen.response.transfer.AdyenBalance;
import uk.gov.pay.connector.gateway.adyen.response.transfer.AdyenBalanceAccount;
import uk.gov.pay.connector.gateway.adyen.response.transfer.AdyenPlatformPaymentCategory;
import uk.gov.pay.connector.gateway.adyen.response.transfer.AdyenTransferData;
import uk.gov.pay.connector.gateway.adyen.response.transfer.Tracking;
import uk.gov.pay.connector.gateway.adyen.response.transfer.TransferEvent;

import java.util.List;

public class AdyenTransferDataFixture {

    String id = "12345";
    String type = "bankTransfer";
    AdyenAccountHolder accountHolder = new AdyenAccountHolder("account holder desc",
            "678910",
            "account holder reference");
    Amount amount = new Amount("GBP", 1000L);
    AdyenBalanceAccount balanceAccount = new AdyenBalanceAccount("balance-account-id",
            "balance-account-reference",
            "balance-account-description");
    String balancePlatform = "balance-platform-id";
    List<AdyenBalance> balances = List.of(new AdyenBalance("GBP", 10L));
    String category = "bank";
    AdyenPlatformPaymentCategory categoryData = new AdyenPlatformPaymentCategory("merchant-reference",
            "merchant-psp-reference",
            "payment-merchant-reference",
            "Commission",
            "psp-payment-reference",
            "platformPayment");
    String createdAt = "2026-09-13T18:50:00.000000Z";
    String referenceForBeneficiary = "some statement reference";
    String description = "some description";
    String direction = "outgoing";
    String reason = "some reason";
    String reference = "some reference";
    Integer sequenceNumber = 1;
    String status = "received";
    Tracking tracking = new Tracking("2026-10-13T18:50:00.000000Z");
    List<TransferEvent> events = List.of(new TransferEvent(
                    null,
                    null,
                    "received"),
            new TransferEvent(
                    "transaction_id",
                    "failure_reason_code",
                    "booked"));


    public static AdyenTransferDataFixture anAdyenTransferDataFixture() {
        return new AdyenTransferDataFixture();
    }

    public AdyenTransferData build() {
        return new AdyenTransferData(
                id,
                type,
                accountHolder,
                amount,
                balanceAccount,
                balancePlatform,
                balances,
                category,
                categoryData,
                createdAt,
                referenceForBeneficiary,
                description,
                direction,
                reason,
                reference,
                sequenceNumber,
                status,
                tracking,
                events);
    }

    public AdyenTransferDataFixture withId(String id) {
        this.id = id;
        return this;
    }

    public AdyenTransferDataFixture withType(String type) {
        this.type = type;
        return this;
    }

    public AdyenTransferDataFixture withAccountHolder(AdyenAccountHolder accountHolder) {
        this.accountHolder = accountHolder;
        return this;
    }

    public AdyenTransferDataFixture withAmount(Amount amount) {
        this.amount = amount;
        return this;
    }

    public AdyenTransferDataFixture withBalanceAccount(AdyenBalanceAccount balanceAccount) {
        this.balanceAccount = balanceAccount;
        return this;
    }

    public AdyenTransferDataFixture withBalancePlatform(String balancePlatform) {
        this.balancePlatform = balancePlatform;
        return this;
    }

    public AdyenTransferDataFixture withBalances(List<AdyenBalance> balances) {
        this.balances = balances;
        return this;
    }

    public AdyenTransferDataFixture withCategory(String category) {
        this.category = category;
        return this;
    }

    public AdyenTransferDataFixture withCategoryData(AdyenPlatformPaymentCategory categoryData) {
        this.categoryData = categoryData;
        return this;
    }

    public AdyenTransferDataFixture withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public AdyenTransferDataFixture withReferenceForBeneficiary(String referenceForBeneficiary) {
        this.referenceForBeneficiary = referenceForBeneficiary;
        return this;
    }

    public AdyenTransferDataFixture withDescription(String description) {
        this.description = description;
        return this;
    }

    public AdyenTransferDataFixture withDirection(String direction) {
        this.direction = direction;
        return this;
    }

    public AdyenTransferDataFixture withReason(String reason) {
        this.reason = reason;
        return this;
    }

    public AdyenTransferDataFixture withReference(String reference) {
        this.reference = reference;
        return this;
    }

    public AdyenTransferDataFixture withSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        return this;
    }

    public AdyenTransferDataFixture withStatus(String status) {
        this.status = status;
        return this;
    }

    public AdyenTransferDataFixture withTracking(Tracking tracking) {
        this.tracking = tracking;
        return this;
    }

    public AdyenTransferDataFixture withEvents(List<TransferEvent> events) {
        this.events = events;
        return this;
    }
}
