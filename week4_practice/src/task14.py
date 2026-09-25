class LegacyTelemetry:
    def get_raw_data(self):
        return "TEMP=24.5;HUMID=60;STATUS=OK;"


class TelemetryAdapter:
    def __init__(self, telemetry):
        self.telemetry = telemetry

    def get_data(self):
        raw_data = self.telemetry.get_raw_data()

        result = {}

        parts = raw_data.split(";")

        for part in parts:
            part = part.strip()

            if not part:
                continue

            if "=" not in part:
                continue

            key, value = part.split("=", 1)

            result[key.strip()] = value.strip()

        return result


legacy = LegacyTelemetry()
adapter = TelemetryAdapter(legacy)

print(adapter.get_data())