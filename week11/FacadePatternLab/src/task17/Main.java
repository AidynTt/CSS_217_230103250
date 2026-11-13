package task17;

import java.util.HashMap;
import java.util.Map;


class ComplianceScreener {
    boolean checkTransfer(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount");
            return false;
        }
        return true;
    }
}

class ExchangeRateService {
    double convert(double amount, String currency) {
        if (currency.equals("EUR")) {
            return amount * 0.9;
        }
        return amount;
    }
}

class AccountService {
    private Map<String, Double> accounts = new HashMap<>();

    AccountService() {
        accounts.put("A100", 1000.0);
        accounts.put("B200", 500.0);
    }

    boolean hasBalance(String account, double amount) {
        return accounts.getOrDefault(account, 0.0) >= amount;
    }

   
}

class LedgerService {
    void log(String message) {
        System.out.println("Log: " + message);
    }
}

class WireTransferFacade {
    private ComplianceScreener compliance = new ComplianceScreener();
    private ExchangeRateService exchange = new ExchangeRateService();
    private AccountService accounts = new AccountService();
    private LedgerService ledger = new LedgerService();

    boolean executeTransfer(String fromAcct, String toAcct,
                            double amount, String targetCurrency) {

        System.out.println("Starting transfer");

        if (!compliance.checkTransfer(amount)) {
            ledger.log("Transfer rejected");
            return false;
        }

        if (!accounts.hasBalance(fromAcct, amount)) {
            ledger.log("Not enough balance");
            return false;
        }

        double converted = exchange.convert(amount, targetCurrency);

        System.out.println("From: " + fromAcct);
        System.out.println("To: " + toAcct);
        System.out.println("Converted amount: " + converted);

       

        ledger.log("Transfer not completed yet");
        return false;
    }
}

public class Main {
    public static void main(String[] args) {
        WireTransferFacade bank = new WireTransferFacade();

        bank.executeTransfer("A100", "B200", 100, "EUR");
    }
}

