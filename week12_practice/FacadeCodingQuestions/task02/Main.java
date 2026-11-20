package week12_practice.FacadeCodingQuestions.task02;

public class Main {

    public static void main(String[] args) {

        BankingFacade bankingFacade = new BankingFacade();

        boolean result = bankingFacade.applyForLoan("C001", 5000.0);

        System.out.println("Loan application result: " + result);
    }
}