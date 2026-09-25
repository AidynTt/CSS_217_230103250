import hashlib


class LegacyAuthentication:
    def login_with_md5(self, username, password_hash):
        correct_hash = hashlib.md5(
            b"password123"
        ).hexdigest()

        return password_hash == correct_hash


class AuthenticationAdapter:
    def __init__(self, legacy_authentication):
        self.legacy_authentication = legacy_authentication

    def login(self, username, password):
        password_hash = hashlib.md5(
            password.encode()
        ).hexdigest().lower()

        return self.legacy_authentication.login_with_md5(
            username,
            password_hash
        )


legacy = LegacyAuthentication()
adapter = AuthenticationAdapter(legacy)

print(adapter.login("john", "password123"))
print(adapter.login("john", "wrongpassword"))