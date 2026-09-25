class MetricBox:
    def __init__(self, width_cm, height_cm):
        self.width_cm = width_cm
        self.height_cm = height_cm

    def get_width_cm(self):
        return self.width_cm

    def get_height_cm(self):
        return self.height_cm


class ImperialBoxAdapter:
    def __init__(self, metric_box):
        self.metric_box = metric_box

    def get_width_inches(self):
        return self.metric_box.get_width_cm() / 2.54

    def get_height_inches(self):
        return self.metric_box.get_height_cm() / 2.54

    def get_area_square_inches(self):
        width = self.get_width_inches()
        height = self.get_height_inches()

        return width * height


box = MetricBox(25.4, 50.8)
adapter = ImperialBoxAdapter(box)

print("Width:", adapter.get_width_inches())
print("Height:", adapter.get_height_inches())
print("Area:", adapter.get_area_square_inches())