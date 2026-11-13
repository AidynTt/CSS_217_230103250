
package task13;

import java.util.*;

//I used ChatGPT to help write this code

// Checks if the bank card exists
class CardReader {
    private Set<String> cards = new HashSet<>(
        Arrays.asList("1111", "2222")
    );

    boolean checkCard(String card) {
        return cards.contains(card);
    }
}

// Checks the card PIN
class PinAuthenticator {
    private Map<String, Integer> pins = new HashMap<>();

    PinAuthenticator() {
        pins.put("1111", 1234);
        pins.put("2222", 4321);
    }

    boolean checkPin(String card, int pin) {
        return pins.containsKey(card) && pins.get(card) == pin;
    }
}

// Stores account balances
class AccountLedger {
    private Map<String, Double> balances = new HashMap<>();

    AccountLedger() {
        balances.put("1111", 500.0);
        balances.put("2222", 800.0);
    }

    boolean hasMoney(String card, double amount) {
        return balances.getOrDefault(card, 0.0) >= amount;
    }

    void withdraw(String card, double amount) {
        double balance = balances.get(card);
        balances.put(card, balance - amount);
    }

    double getBalance(String card) {
        return balances.getOrDefault(card, 0.0);
    }
}

// Controls the cash available in the ATM
class CashDispenser {
    private double cash = 400.0;

    boolean hasCash(double amount) {
        return cash >= amount;
    }

    void dispense(double amount) {
        cash -= amount;
        System.out.println("Cash dispensed: $" + amount);
    }
}

// Records successful and rejected operations
class AuditLogger {
    void log(String card, String result) {
        System.out.println("Audit: " + card + " - " + result);
    }
}

class AtmFacade {
    private CardReader reader = new CardReader();
    private PinAuthenticator auth = new PinAuthenticator();
    private AccountLedger account = new AccountLedger();
    private CashDispenser dispenser = new CashDispenser();
    private AuditLogger logger = new AuditLogger();

    // Records a rejected withdrawal
    private boolean reject(String card, String reason) {
        System.out.println(reason);
        logger.log(card, "REJECTED: " + reason);
        return false;
    }

    // Checks all conditions before giving cash
    boolean withdrawCash(String cardNumber, int pin, double amount) {
        System.out.println("ATM Withdrawal");

        if (!reader.checkCard(cardNumber)) {
            return reject(cardNumber, "Invalid card");
        }

        if (!auth.checkPin(cardNumber, pin)) {
            return reject(cardNumber, "Wrong PIN");
        }

        if (!Double.isFinite(amount) || amount <= 0) {
            return reject(cardNumber, "Invalid amount");
        }

        if (!account.hasMoney(cardNumber, amount)) {
            return reject(cardNumber, "Not enough money in account");
        }

        if (!dispenser.hasCash(amount)) {
            return reject(cardNumber, "ATM does not have enough cash");
        }

        account.withdraw(cardNumber, amount);
        dispenser.dispense(amount);

        System.out.println("Withdrawal successful");
        System.out.println("Balance: $" + account.getBalance(cardNumber));

        logger.log(cardNumber, "SUCCESS");
        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        AtmFacade atm = new AtmFacade();

        // Successful withdrawal
        atm.withdrawCash("1111", 1234, 200);

        System.out.println();

        // Wrong PIN
        atm.withdrawCash("1111", 9999, 50);

        System.out.println();

        // Not enough money in account
        atm.withdrawCash("1111", 1234, 400);

        System.out.println();

        // Not enough cash in ATM
        atm.withdrawCash("2222", 4321, 300);

        System.out.println();

        // Another successful withdrawal
        atm.withdrawCash("2222", 4321, 100);
    }
}
