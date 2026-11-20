package week12_practice.FacadeCodingQuestions.task03;

public class OrderFacade {

    private InventoryManager inventoryManager;
    private PaymentGateway paymentGateway;
    private ShippingService shippingService;

    public OrderFacade() {
        inventoryManager = new InventoryManager();
        paymentGateway = new PaymentGateway();
        shippingService = new ShippingService();
    }

    public boolean placeOrder(String productId, int quantity,
                              String paymentInfo, String address) {

        System.out.println("Processing order...");

        if (!inventoryManager.checkStock(productId, quantity)) {
            System.out.println("Order failed: Product out of stock");
            return false;
        }

        if (!paymentGateway.processPayment(paymentInfo)) {
            System.out.println("Order failed: Payment unsuccessful");
            return false;
        }

        shippingService.arrangeShipping(productId, quantity, address);

        System.out.println("Order placed successfully!");
        return true;
    }
}