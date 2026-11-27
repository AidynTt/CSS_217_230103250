
package week13_practice.task04;

import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.zip.DeflaterOutputStream;


interface CompressionStrategy {
    byte[] compress(byte[] data) throws IOException;
}

class RunLengthCompression implements CompressionStrategy {
    @Override
    public byte[] compress(byte[] data) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();

        if (data.length == 0) {
            return result.toByteArray();
        }

        int count = 1;

        for (int i = 1; i < data.length; i++) {
            if (data[i] == data[i - 1] && count < 255) {
                count++;
            } else {
                result.write(data[i - 1]);
                result.write(count);
                count = 1;
            }
        }

        result.write(data[data.length - 1]);
        result.write(count);

        return result.toByteArray();
    }
}

class DictionaryCompression implements CompressionStrategy {
    @Override
    public byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();

        try (DeflaterOutputStream compressor =
                     new DeflaterOutputStream(result)) {
            compressor.write(data);
        }

        return result.toByteArray();
    }
}

class NoCompression implements CompressionStrategy {
    @Override
    public byte[] compress(byte[] data) {
        return data;
    }
}


class FileReader {
    public byte[] read(String path) throws IOException {
        return Files.readAllBytes(Path.of(path));
    }
}

class ChecksumGenerator {
    public String generate(byte[] data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return HexFormat.of().formatHex(digest.digest(data));
    }
}

class ArchivePackager {
    public void writeFile(DataOutputStream output,
                          String name,
                          String checksum,
                          byte[] data) throws IOException {
        output.writeUTF(name);
        output.writeUTF(checksum);
        output.writeInt(data.length);
        output.write(data);
    }
}

class ArchiveFacade {
    private CompressionStrategy strategy;

    private final FileReader reader = new FileReader();
    private final ChecksumGenerator checksumGenerator =
            new ChecksumGenerator();
    private final ArchivePackager packager =
            new ArchivePackager();

    public ArchiveFacade(CompressionStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(CompressionStrategy strategy) {
        this.strategy = strategy;
    }

    public void createArchive(String[] sourceFiles,
                              String destination) {
        try (DataOutputStream output = new DataOutputStream(
                Files.newOutputStream(Path.of(destination)))) {

            output.writeUTF(strategy.getClass().getSimpleName());
            output.writeInt(sourceFiles.length);

            for (String file : sourceFiles) {
                byte[] original = reader.read(file);

                String checksum =
                        checksumGenerator.generate(original);

                byte[] compressed = strategy.compress(original);

                packager.writeFile(
                        output, file, checksum, compressed
                );

                System.out.println("File: " + file);
                System.out.println("Original size: "
                        + original.length + " bytes");
                System.out.println("Compressed size: "
                        + compressed.length + " bytes");
            }

            System.out.println("Archive created: " + destination);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) throws IOException {

        Files.writeString(
                Path.of("sample.txt"),
                "AAAAAAAAAABBBBBBBBBBCCCCCCCCCC"
        );

        String[] files = {"sample.txt"};

        ArchiveFacade facade =
                new ArchiveFacade(new RunLengthCompression());

        System.out.println("Run Length Compression:");
        facade.createArchive(files, "rle.arc");

        System.out.println("\nDictionary Compression:");
        facade.setStrategy(new DictionaryCompression());
        facade.createArchive(files, "dictionary.arc");

        System.out.println("\nNo Compression:");
        facade.setStrategy(new NoCompression());
        facade.createArchive(files, "original.arc");
    }
}
