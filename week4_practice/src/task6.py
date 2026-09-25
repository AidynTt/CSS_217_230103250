class RecordNotFoundException(Exception):
    pass


class DatabaseLockedException(Exception):
    pass


class LegacyUserRepository:
    def find_user(self, user_id, out_buffer):
        if user_id == 1:
            out_buffer[0] = "John"
            return 0

        if user_id == 2:
            return -1

        if user_id == 3:
            return -2

        return -1


class UserRepositoryAdapter:
    def __init__(self, legacy_repository):
        self.legacy_repository = legacy_repository

    def find_by_id(self, user_id):
        out_buffer = [None]

        code = self.legacy_repository.find_user(
            user_id,
            out_buffer
        )

        if code == 0:
            return out_buffer[0]

        if code == -1:
            raise RecordNotFoundException("User not found")

        if code == -2:
            raise DatabaseLockedException("Database is locked")

        raise RuntimeError("Unknown error code")


legacy = LegacyUserRepository()
adapter = UserRepositoryAdapter(legacy)

print(adapter.find_by_id(1))

try:
    adapter.find_by_id(2)
except RecordNotFoundException as e:
    print(e)

try:
    adapter.find_by_id(3)
except DatabaseLockedException as e:
    print(e)