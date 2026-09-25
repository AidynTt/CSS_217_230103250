class LegacyCloudStorage:
    def save_blob(self, path, data):
        print(f"Saved to {path}: {data}")


class FileStoreAdapter:
    def __init__(self, cloud_storage):
        self.cloud_storage = cloud_storage

    def save(self, bucket, object_key, data):
        bucket = bucket.strip("/")
        object_key = object_key.strip("/")

        path = f"/var/data/{bucket}/{object_key}"

        self.cloud_storage.save_blob(path, data)


legacy = LegacyCloudStorage()
adapter = FileStoreAdapter(legacy)

adapter.save(
    "my-bucket",
    "folder/file.txt",
    "Hello"
)