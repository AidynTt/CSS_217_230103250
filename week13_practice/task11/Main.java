package week13_practice.task11;

import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;
import java.util.zip.*;
import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;


interface BackupStrategy {
    List<Path> selectFiles(List<Path> files, BackupState state)
            throws Exception;
}


class BackupState {
    Map<Path, String> lastBackup = new HashMap<>();
    Map<Path, String> lastFullBackup = new HashMap<>();

    public String fingerprint(Path file) throws Exception {
        byte[] data = Files.readAllBytes(file);

        return HexFormat.of().formatHex(
                MessageDigest.getInstance("SHA-256").digest(data));
    }
}


class FullBackupStrategy implements BackupStrategy {
    public List<Path> selectFiles(List<Path> files,
                                  BackupState state) {
        return new ArrayList<>(files);
    }
}

class IncrementalBackupStrategy implements BackupStrategy {
    public List<Path> selectFiles(List<Path> files,
                                  BackupState state)
            throws Exception {

        List<Path> selected = new ArrayList<>();

        for (Path file : files) {
            String current = state.fingerprint(file);

            if (!current.equals(state.lastBackup.get(file))) {
                selected.add(file);
            }
        }

        return selected;
    }
}

class DifferentialBackupStrategy implements BackupStrategy {
    public List<Path> selectFiles(List<Path> files,
                                  BackupState state)
            throws Exception {

        List<Path> selected = new ArrayList<>();

        for (Path file : files) {
            String current = state.fingerprint(file);

            if (!current.equals(state.lastFullBackup.get(file))) {
                selected.add(file);
            }
        }

        return selected;
    }
}

class FileScanner {
    public List<Path> scan(String directory) throws IOException {
        try (var paths = Files.walk(Path.of(directory))) {
            return paths.filter(Files::isRegularFile).toList();
        }
    }
}

class AesEncryptor {
    private final SecretKey key;

    public AesEncryptor() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);
        key = generator.generateKey();
    }

    public byte[] encrypt(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        byte[] iv = new byte[12];
        new java.security.SecureRandom().nextBytes(iv);

        cipher.init(Cipher.ENCRYPT_MODE, key,
                new GCMParameterSpec(128, iv));

        byte[] encrypted = cipher.doFinal(data);

        ByteArrayOutputStream result = new ByteArrayOutputStream();

        result.write(iv);
        result.write(encrypted);

        return result.toByteArray();
    }
}

class CloudStorageClient {
    private final Path storage = Path.of("cloud_backup");

    public void upload(byte[] data, String name)
            throws IOException {

        Files.createDirectories(storage);
        Files.write(storage.resolve(name), data);

        System.out.println("Uploaded: " + name);
    }

    public Path getStorage() {
        return storage;
    }
}

class RetentionCleaner {
    public void clean(Path storage, int retentionDays)
            throws IOException {

        long expiration = System.currentTimeMillis()
                - retentionDays * 24L * 60 * 60 * 1000;

        try (var paths = Files.list(storage)) {
            for (Path file : paths.toList()) {
                if (Files.isRegularFile(file)
                        && file.toString().endsWith(".enc")
                        && Files.getLastModifiedTime(file)
                                .toMillis() < expiration) {

                    Files.delete(file);
                    System.out.println("Deleted expired backup: "
                            + file.getFileName());
                }
            }
        }

        System.out.println("Retention cleanup completed");
    }
}

class BackupFacade {
    private BackupStrategy strategy;

    private final BackupState state = new BackupState();
    private final FileScanner scanner = new FileScanner();
    private final AesEncryptor encryptor;
    private final CloudStorageClient cloud =
            new CloudStorageClient();
    private final RetentionCleaner cleaner =
            new RetentionCleaner();

    public BackupFacade(BackupStrategy strategy)
            throws Exception {

        this.strategy = strategy;
        encryptor = new AesEncryptor();
    }

    public void setStrategy(BackupStrategy strategy) {
        this.strategy = strategy;
    }

    public void runBackupJob(String directory) {
        try {
            List<Path> files = scanner.scan(directory);

            List<Path> selected =
                    strategy.selectFiles(files, state);

            System.out.println("Files scanned: " + files.size());
            System.out.println("Files selected: " + selected.size());

            if (!selected.isEmpty()) {
                ByteArrayOutputStream buffer =
                        new ByteArrayOutputStream();

                try (ZipOutputStream zip =
                             new ZipOutputStream(buffer)) {

                    for (Path file : selected) {
                        String name = Path.of(directory)
                                .relativize(file).toString();

                        zip.putNextEntry(new ZipEntry(name));
                        zip.write(Files.readAllBytes(file));
                        zip.closeEntry();

                        System.out.println("Backing up: " + name);
                    }
                }

                byte[] encrypted =
                        encryptor.encrypt(buffer.toByteArray());

                String archiveName =
                        strategy.getClass().getSimpleName()
                        + "_" + UUID.randomUUID() + ".enc";

                cloud.upload(encrypted, archiveName);

                for (Path file : selected) {
                    state.lastBackup.put(
                            file, state.fingerprint(file));
                }

                if (strategy instanceof FullBackupStrategy) {
                    state.lastFullBackup.clear();
                    state.lastFullBackup.putAll(state.lastBackup);
                }
            } else {
                System.out.println("No changed files");
            }

            cleaner.clean(cloud.getStorage(), 7);

            System.out.println("Backup completed!");

        } catch (Exception e) {
            System.out.println("Backup failed: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) throws Exception {

        Path directory = Path.of("backup_source");

        Files.createDirectories(directory);

        Files.writeString(directory.resolve("file1.txt"), "Hello");
        Files.writeString(directory.resolve("file2.txt"), "Java");
        Files.writeString(directory.resolve("file3.txt"), "Patterns");

        BackupFacade facade =
                new BackupFacade(new FullBackupStrategy());

        System.out.println("Full Backup:");
        facade.runBackupJob("backup_source");

        Files.writeString(directory.resolve("file1.txt"), "Updated");

        System.out.println("\nIncremental Backup:");
        facade.setStrategy(new IncrementalBackupStrategy());
        facade.runBackupJob("backup_source");

        Files.writeString(directory.resolve("file2.txt"), "New Java");

        System.out.println("\nDifferential Backup:");
        facade.setStrategy(new DifferentialBackupStrategy());
        facade.runBackupJob("backup_source");
    }
}

