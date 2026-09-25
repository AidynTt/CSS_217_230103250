class FahrenheitSensor:
    def read_raw_temperature(self):
        return "77.0 F"


class TemperatureSensorAdapter:
    def __init__(self, sensor):
        self.sensor = sensor

    def get_temperature_in_celsius(self):
        raw_temperature = self.sensor.read_raw_temperature()

        fahrenheit = float(raw_temperature.removesuffix(" F"))

        celsius = (fahrenheit - 32) * (5.0 / 9.0)

        return round(celsius, 2)


sensor = FahrenheitSensor()
adapter = TemperatureSensorAdapter(sensor)

print(adapter.get_temperature_in_celsius())