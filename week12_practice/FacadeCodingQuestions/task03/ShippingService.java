package week12_practice.FacadeCodingQuestions.task03;

public class ShippingService {

    public void arrangeShipping(String productId, int quantity, String address) {
        System.out.println("Arranging shipping...");

        System.out.println("Product: " + productId);
        System.out.println("Quantity: " + quantity);
        System.out.println("Shipping address: " + address);

        System.out.println("Shipping successfully arranged!");
    }
}