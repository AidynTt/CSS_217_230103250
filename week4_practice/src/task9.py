class SpeedometerAdapter:
    def __init__(self, kmh=0):
        self.kmh = kmh

    def get_kmh(self):
        return self.kmh

    def set_kmh(self, value):
        self.kmh = value

    def get_mph(self):
        return self.kmh * 0.621371

    def set_mph(self, value):
        self.kmh = value * 1.60934


speedometer = SpeedometerAdapter()

speedometer.set_kmh(100)

print("KM/H:", speedometer.get_kmh())
print("MPH:", round(speedometer.get_mph(), 2))

speedometer.set_mph(60)

print("KM/H:", round(speedometer.get_kmh(), 2))
print("MPH:", speedometer.get_mph())