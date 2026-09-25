class LegacyEnumeration:
    def __init__(self, items):
        self.items = items
        self.index = 0

    def has_more_elements(self):
        return self.index < len(self.items)

    def next_element(self):
        item = self.items[self.index]
        self.index += 1
        return item


class EnumerationAdapter:
    def __init__(self, enumeration):
        self.enumeration = enumeration

    def __iter__(self):
        return self

    def __next__(self):
        if not self.enumeration.has_more_elements():
            raise StopIteration

        return self.enumeration.next_element()


legacy = LegacyEnumeration(["Apple", "Banana", "Orange"])
adapter = EnumerationAdapter(legacy)

for item in adapter:
    print(item)