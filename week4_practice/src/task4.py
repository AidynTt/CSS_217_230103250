from datetime import datetime, timezone


class LegacyTimestampService:
    def get_timestamp(self):
        return 1710000000


class DateServiceAdapter:
    def __init__(self, timestamp_service):
        self.timestamp_service = timestamp_service

    def get_date(self):
        timestamp = self.timestamp_service.get_timestamp()

        date = datetime.fromtimestamp(
            timestamp,
            timezone.utc
        ).date()

        return date


legacy = LegacyTimestampService()
adapter = DateServiceAdapter(legacy)

print(adapter.get_date())