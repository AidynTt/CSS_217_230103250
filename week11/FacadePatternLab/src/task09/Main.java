
package task09;

import java.util.*;

//I used ChatGPT to help write this code.

class MemberValidator {
    private Set<String> students = new HashSet<>();

    MemberValidator() {
        students.add("S101");
        students.add("S102");
    }

    boolean isActive(String studentId) {
        return students.contains(studentId);
    }
}

class CatalogService {
    private Map<String, Boolean> books = new HashMap<>();

    CatalogService() {
        books.put("ISBN001", true);
        books.put("ISBN002", true);
    }

    boolean isAvailable(String isbn) {
        return Boolean.TRUE.equals(books.get(isbn));
    }

    void borrow(String isbn) {
        books.put(isbn, false);
    }

    void returnBook(String isbn) {
        books.put(isbn, true);
    }
}

class FineCalculator {
    private Map<String, Double> fines = new HashMap<>();

    FineCalculator() {
        fines.put("S101", 0.0);
        fines.put("S102", 15.0);
    }

    boolean hasNoFines(String studentId) {
        return fines.getOrDefault(studentId, 0.0) == 0;
    }
}

class LoanRecorder {
    private Map<String, String> loans = new HashMap<>();

    void recordLoan(String studentId, String isbn) {
        loans.put(isbn, studentId);
        System.out.println("Loan recorded for " + studentId);
    }

    boolean hasLoan(String studentId, String isbn) {
        return studentId.equals(loans.get(isbn));
    }

    void removeLoan(String isbn) {
        loans.remove(isbn);
    }
}

// Facade checks all conditions before borrowing a book.
class LibraryCheckoutFacade {
    private MemberValidator members = new MemberValidator();
    private CatalogService catalog = new CatalogService();
    private FineCalculator fines = new FineCalculator();
    private LoanRecorder loans = new LoanRecorder();

    boolean borrowBook(String studentId, String isbn) {
        System.out.println("Student: " + studentId);
        System.out.println("Book: " + isbn);

        if (!members.isActive(studentId)) {
            System.out.println("Student is not active");
            return false;
        }

        if (!fines.hasNoFines(studentId)) {
            System.out.println("Student has unpaid fines");
            return false;
        }

        if (!catalog.isAvailable(isbn)) {
            System.out.println("Book is not available");
            return false;
        }

        loans.recordLoan(studentId, isbn);
        catalog.borrow(isbn);

        System.out.println("Book borrowed successfully");
        return true;
    }

    void returnBook(String studentId, String isbn) {
        if (!loans.hasLoan(studentId, isbn)) {
            System.out.println("No matching loan found");
            return;
        }

        loans.removeLoan(isbn);
        catalog.returnBook(isbn);

        System.out.println("Book returned successfully");
    }
}

public class Main {
    public static void main(String[] args) {
        LibraryCheckoutFacade library = new LibraryCheckoutFacade();

        library.borrowBook("S101", "ISBN001");

        System.out.println();

        library.borrowBook("S102", "ISBN002");

        System.out.println();

        library.borrowBook("S101", "ISBN001");

        System.out.println();

        library.returnBook("S101", "ISBN001");

        System.out.println();

        library.borrowBook("S101", "ISBN001");
    }
}
