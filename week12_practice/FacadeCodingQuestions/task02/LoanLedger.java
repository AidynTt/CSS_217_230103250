package week12_practice.FacadeCodingQuestions.task02;

public class LoanLedger {

    public void recordLoan(String customerId, double amount) {
        System.out.println("Recording loan for customer: " + customerId);
        System.out.println("Loan amount: $" + amount);
        System.out.println("Loan successfully recorded");
    }
}