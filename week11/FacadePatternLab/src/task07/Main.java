package task07;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

class SpellChecker {
    String check(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Document is empty");
        }
        System.out.println("Text checked");
        return text;
    }
}

class PageFormatter {
    String format(String text) {
        System.out.println("A4 page format selected");
        return text.trim();
    }
}

class PdfConverter {
    byte[] convert(String text) {
        String safe = text.replace("\\", "\\\\")
                .replace("(", "\\(")
                .replace(")", "\\)");
        String content = "BT /F1 12 Tf 50 790 Td (" + safe + ") Tj ET";

        String[] objects = {
            "<< /Type /Catalog /Pages 2 0 R >>",
            "<< /Type /Pages /Kids [3 0 R] /Count 1 >>",
            "<< /Type /Page /Parent 2 0 R /MediaBox [0 0 595 842] /Resources << /Font << /F1 4 0 R >> >> /Contents 5 0 R >>",
            "<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>",
            "<< /Length " + content.length() + " >>\nstream\n" + content + "\nendstream"
        };

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        write(out, "%PDF-1.4\n");
        int[] offsets = new int[6];

        for (int i = 0; i < objects.length; i++) {
            offsets[i + 1] = out.size();
            write(out, (i + 1) + " 0 obj\n" + objects[i] + "\nendobj\n");
        }

        int xref = out.size();
        write(out, "xref\n0 6\n0000000000 65535 f \n");

        for (int i = 1; i < offsets.length; i++) {
            write(out, String.format(Locale.US, "%010d 00000 n \n", offsets[i]));
        }

        write(out, "trailer\n<< /Size 6 /Root 1 0 R >>\nstartxref\n" + xref + "\n%%EOF");
        return out.toByteArray();
    }

    private void write(ByteArrayOutputStream out, String text) {
        out.writeBytes(text.getBytes(StandardCharsets.US_ASCII));
    }
}

class PrinterSpooler {
    void print(String text) {
        System.out.println("Print job added: " + text);
    }
}

class DocumentFacade {
    private final SpellChecker checker = new SpellChecker();
    private final PageFormatter formatter = new PageFormatter();
    private final PdfConverter converter = new PdfConverter();
    private final PrinterSpooler printer = new PrinterSpooler();

    void printCleanDocument(String text) {
        String formatted = formatter.format(checker.check(text));
        printer.print(formatted);
    }

    void exportAsPdf(String text, String fileName) throws IOException {
        String formatted = formatter.format(checker.check(text));
        Files.write(Path.of(fileName), converter.convert(formatted));
        System.out.println("Saved PDF: " + fileName);
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        DocumentFacade document = new DocumentFacade();

        document.printCleanDocument("Hello, this is my Java lab.");

        System.out.println();

        document.exportAsPdf("Hello, this is my PDF document.", "task07.pdf");
    }
}
