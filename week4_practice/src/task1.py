class LegacyBillingSystem:
    def charge_customer_in_cents(self, customer_id, amount_in_cents):
        print(
            f"Billed customer {customer_id}: "
            f"{amount_in_cents} cents"
        )


class PaymentGatewayAdapter:
    def __init__(self, legacy_billing):
        self.legacy_billing = legacy_billing

    def process_payment(self, customer_id, amount_in_dollars):
        if amount_in_dollars is None or amount_in_dollars < 0:
            raise ValueError("Amount cannot be negative or None")

        amount_in_cents = round(amount_in_dollars * 100)

        self.legacy_billing.charge_customer_in_cents(
            customer_id,
            amount_in_cents
        )

legacy = LegacyBillingSystem()
adapter = PaymentGatewayAdapter(legacy)

adapter.process_payment(101, 10.50)