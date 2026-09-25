class LegacyUserService:
    def get_next_user(self):
        return "101,John Doe,ADMIN"


class UserProfile:
    def __init__(self, user_id, full_name, role):
        self.user_id = user_id
        self.full_name = full_name
        self.role = role

    def __str__(self):
        return f"{self.user_id}, {self.full_name}, {self.role}"


class UserServiceAdapter:
    def __init__(self, legacy_service):
        self.legacy_service = legacy_service

    def get_next_user(self):
        data = self.legacy_service.get_next_user()

        if data is None:
            raise ValueError("Invalid user data")

        parts = data.split(",")

        if len(parts) != 3:
            raise ValueError("Invalid user data")

        user_id = int(parts[0].strip())
        full_name = parts[1].strip()
        role = parts[2].strip()

        return UserProfile(user_id, full_name, role)


legacy = LegacyUserService()
adapter = UserServiceAdapter(legacy)

user = adapter.get_next_user()

print(user)