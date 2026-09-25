class LegacyStudentDirectory:
    def __init__(self):
        self.students = ["John", "Alice", "Bob"]

    def total_entries(self):
        return len(self.students)

    def get_student_at(self, one_based_index):
        return self.students[one_based_index - 1]


class StudentDirectoryAdapter:
    def __init__(self, legacy_directory):
        self.legacy_directory = legacy_directory

    def size(self):
        return self.legacy_directory.total_entries()

    def get(self, zero_based_index):
        if zero_based_index < 0 or zero_based_index >= self.size():
            raise IndexError("Index out of range")

        one_based_index = zero_based_index + 1

        return self.legacy_directory.get_student_at(one_based_index)


legacy = LegacyStudentDirectory()
adapter = StudentDirectoryAdapter(legacy)

print(adapter.size())
print(adapter.get(0))
print(adapter.get(1))
print(adapter.get(2))