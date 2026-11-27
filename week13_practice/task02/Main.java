package week13_practice.task02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// Strategy interface
interface ExportStrategy {
    String export(String[][] document);
}

// Concrete strategies
class PlainTextExport implements ExportStrategy {
    public String export(String[][] document) {
        StringBuilder result = new StringBuilder();

        for (String[] row : document) {
            result.append(String.join(" ", row)).append("\n");
        }

        return result.toString();
    }
}

class CsvExport implements ExportStrategy {
    public String export(String[][] document) {
        StringBuilder result = new StringBuilder();

        for (String[] row : document) {
            result.append(String.join(",", row)).append("\n");
        }

        return result.toString();
    }
}

class MarkdownExport implements ExportStrategy {
    public String export(String[][] document) {
        StringBuilder result = new StringBuilder();

        for (String[] row : document) {
            result.append("- ")
                  .append(String.join(" | ", row))
                  .append("\n");
        }

        return result.toString();
    }
}

// Subsystems
class ContentValidator {
    public boolean validate(String[][] document) {
        if (document == null || document.length == 0) {
            return false;
        }

        for (String[] row : document) {
            if (row == null || row.length == 0) {
                return false;
            }

            for (String value : row) {
                if (value == null) {
                    return false;
                }
            }
        }

        return true;
    }
}

class MetadataStamp {
    public String[][] stamp(String[][] document) {
        String[][] result = new String[document.length + 1][];

        result[0] = new String[]{"Created", 
                java.time.LocalDate.now().toString()};

        for (int i = 0; i < document.length; i++) {
            result[i + 1] = document[i];
        }

        return result;
    }
}

class DiskWriter {
    public void write(String content, String outputPath)
            throws IOException {
        Files.writeString(Path.of(outputPath), content);
        System.out.println("File saved: " + outputPath);
    }
}

// Facade
class DocumentExportFacade {
    private ExportStrategy strategy;

    private final ContentValidator validator =
            new ContentValidator();
    private final MetadataStamp metadata =
            new MetadataStamp();
    private final DiskWriter writer =
            new DiskWriter();

    public DocumentExportFacade(ExportStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(ExportStrategy strategy) {
        this.strategy = strategy;
    }

    public void exportReport(String[][] document,
                             String outputPath) {
        if (!validator.validate(document)) {
            System.out.println("Invalid document");
            return;
        }

        String[][] stampedDocument = metadata.stamp(document);
        String content = strategy.export(stampedDocument);

        try {
            writer.write(content, outputPath);
        } catch (IOException e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }
}

// Client
public class Main {
    public static void main(String[] args) {
        String[][] document = {
            {"Name", "Score"},
            {"Alex", "95"},
            {"John", "88"}
        };

        DocumentExportFacade facade =
                new DocumentExportFacade(new PlainTextExport());

        facade.exportReport(document, "report.txt");

        facade.setStrategy(new CsvExport());
        facade.exportReport(document, "report.csv");

        facade.setStrategy(new MarkdownExport());
        facade.exportReport(document, "report.md");
    }
}
