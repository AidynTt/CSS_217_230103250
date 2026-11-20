package week12_practice.FacadeCodingQuestions.task02;

public class BankingFacade {

    private AccountService accountService;
    private CreditScoreService creditScoreService;
    private LoanLedger loanLedger;

    public BankingFacade() {
        accountService = new AccountService();
        creditScoreService = new CreditScoreService();
        loanLedger = new LoanLedger();
    }

    public boolean applyForLoan(String customerId, double amount) {

        System.out.println("Processing loan application...");

        if (!accountService.isAccountActive(customerId)) {
            System.out.println("Loan rejected: Account is inactive");
            return false;
        }

        if (!creditScoreService.hasGoodCreditScore(customerId)) {
            System.out.println("Loan rejected: Poor credit score");
            return false;
        }

        loanLedger.recordLoan(customerId, amount);

        System.out.println("Loan approved!");
        return true;
    }
}