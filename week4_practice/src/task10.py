class LegacyConfig:
    def get_config(self):
        return {
            "database": {
                "host": "localhost",
                "port": 5432
            },
            "app": {
                "name": "MyApp"
            }
        }


class ConfigAdapter:
    def __init__(self, legacy_config):
        self.legacy_config = legacy_config

    def get_string(self, key):
        data = self.legacy_config.get_config()

        parts = key.split(".")

        for part in parts:
            if not isinstance(data, dict) or part not in data:
                return None

            data = data[part]

        return str(data)


legacy = LegacyConfig()
adapter = ConfigAdapter(legacy)

print(adapter.get_string("database.host"))
print(adapter.get_string("database.port"))
print(adapter.get_string("app.name"))
print(adapter.get_string("unknown.value"))