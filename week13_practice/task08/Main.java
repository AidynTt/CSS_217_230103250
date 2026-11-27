package week13_practice.task08;

interface PaymentStrategy {
    boolean authorize(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    public boolean authorize(double amount) {
        System.out.println("Checking credit card...");
        System.out.println("Credit card authorized");
        return true;
    }
}

class DigitalWalletPayment implements PaymentStrategy {
    public boolean authorize(double amount) {
        System.out.println("Checking digital wallet balance...");
        System.out.println("Digital wallet authorized");
        return true;
    }
}

class BankTransferPayment implements PaymentStrategy {
    public boolean authorize(double amount) {
        System.out.println("Checking bank account...");
        System.out.println("Insufficient funds");
        return false;
    }
}

class Order {
    String orderId;
    String product;
    double price;

    public Order(String orderId, String product, double price) {
        this.orderId = orderId;
        this.product = product;
        this.price = price;
    }
}

// Subsystems
class InventoryLockService {
    private boolean locked = false;

    public boolean lock(Order order) {
        if (locked) {
            return false;
        }

        locked = true;
        System.out.println("Product reserved: " + order.product);
        return true;
    }

    public void release(Order order) {
        locked = false;
        System.out.println("Product reservation released");
    }

    public void confirm(Order order) {
        locked = false;
        System.out.println("Product purchase confirmed");
    }
}

class TaxEngine {
    public double calculateTotal(double price) {
        return price + price * 0.10;
    }
}

class PaymentProcessor {
    public boolean process(double amount,
                           PaymentStrategy strategy) {

        System.out.printf("Payment amount: $%.2f%n", amount);

        return strategy.authorize(amount);
    }
}

class ReceiptGenerator {
    public void generate(Order order, double total) {
        System.out.println("Receipt generated");
        System.out.println("Order ID: " + order.orderId);
        System.out.println("Product: " + order.product);
        System.out.printf("Total: $%.2f%n", total);
    }
}

// Facade
class CheckoutFacade {
    private PaymentStrategy strategy;

    private final InventoryLockService inventory =
            new InventoryLockService();

    private final TaxEngine taxEngine =
            new TaxEngine();

    private final PaymentProcessor processor =
            new PaymentProcessor();

    private final ReceiptGenerator receipt =
            new ReceiptGenerator();

    public CheckoutFacade(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void checkout(Order order) {
        if (!inventory.lock(order)) {
            System.out.println("Product unavailable");
            return;
        }

        boolean success = false;

        try {
            double total =
                    taxEngine.calculateTotal(order.price);

            success = processor.process(total, strategy);

            if (success) {
                inventory.confirm(order);
                receipt.generate(order, total);
                System.out.println("Checkout successful!");
            } else {
                System.out.println("Payment failed!");
            }

        } finally {
            if (!success) {
                inventory.release(order);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Order order1 = new Order("ORD001", "Laptop", 1000);
        Order order2 = new Order("ORD002", "Keyboard", 100);
        Order order3 = new Order("ORD003", "Mouse", 50);

        CheckoutFacade facade =
                new CheckoutFacade(new CreditCardPayment());

        System.out.println("Credit Card Payment:");
        facade.checkout(order1);

        System.out.println("\nBank Transfer Payment:");
        facade.setStrategy(new BankTransferPayment());
        facade.checkout(order2);

        System.out.println("\nDigital Wallet Payment:");
        facade.setStrategy(new DigitalWalletPayment());
        facade.checkout(order3);
    }
}
