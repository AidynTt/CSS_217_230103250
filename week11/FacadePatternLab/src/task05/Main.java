package task05;

class InventoryService {
    void reserveStock(String itemId) {
        System.out.println("Reserved item: " + itemId);
    }
}

class PaymentProcessor {
    void chargeCustomer(double amount) {
        System.out.println("Payment successful: $" + amount);
    }
}

class ShippingService {
    void generateShippingLabel(String address) {
        System.out.println("Shipping label created for: " + address);
    }
}


class OrderFacade {
    private final InventoryService inventory;
    private final PaymentProcessor payment;
    private final ShippingService shipping;

    OrderFacade(InventoryService inventory,
                PaymentProcessor payment,
                ShippingService shipping) {
        this.inventory = inventory;
        this.payment = payment;
        this.shipping = shipping;
    }

    
    void placeOrder(String itemId, double amount, String address) {
        System.out.println("Placing Order");
        inventory.reserveStock(itemId);
        payment.chargeCustomer(amount);
        shipping.generateShippingLabel(address);
        System.out.println("Order placed successfully");
    }
}


public class Main {
    public static void main(String[] args) {
        OrderFacade order = new OrderFacade(
            new InventoryService(),
            new PaymentProcessor(),
            new ShippingService()
        );

        
        order.placeOrder("Laptop", 1200.0, "Almaty, Kazakhstan");

        System.out.println();

     
        order.placeOrder("Phone", 800.0, "Astana, Kazakhstan");
    }
}

