class LegacyArrayStack:
    def __init__(self):
        self.items = []

    def push_item(self, item):
        self.items.append(item)

    def pop_item(self):
        return self.items.pop()

    def is_empty_stack(self):
        return len(self.items) == 0


class StackAdapter:
    def __init__(self, legacy_stack):
        self.legacy_stack = legacy_stack

    def push(self, item):
        self.legacy_stack.push_item(item)

    def pop(self):
        return self.legacy_stack.pop_item()

    def is_empty(self):
        return self.legacy_stack.is_empty_stack()


legacy = LegacyArrayStack()
stack = StackAdapter(legacy)

stack.push("A")
stack.push("B")

print(stack.pop())
print(stack.pop())
print(stack.is_empty())