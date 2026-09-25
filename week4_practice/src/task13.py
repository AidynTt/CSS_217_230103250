class AlertMessage:
    def __init__(self, user_phone, title, body):
        self.user_phone = user_phone
        self.title = title
        self.body = body


class LegacySmsService:
    def send_sms(self, phone, message):
        print(f"SMS to {phone}: {message}")


class SmsNotificationAdapter:
    def __init__(self, sms_service):
        self.sms_service = sms_service

    def notify(self, alert):
        if alert is None:
            raise ValueError("Alert cannot be None")

        phone = alert.user_phone

        if not phone.startswith("+"):
            phone = "+" + phone

        message = f"[{alert.title}] {alert.body}"

        self.sms_service.send_sms(phone, message)


sms_service = LegacySmsService()
adapter = SmsNotificationAdapter(sms_service)

alert = AlertMessage(
    "77001234567",
    "Warning",
    "Temperature is high"
)

adapter.notify(alert)