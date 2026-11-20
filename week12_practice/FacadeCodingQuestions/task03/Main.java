package week12_practice.FacadeCodingQuestions.task03;

public class Main {

    public static void main(String[] args) {

        OrderFacade orderFacade = new OrderFacade();

        System.out.println("=== Order 1: Successful Order ===");
        boolean result1 = orderFacade.placeOrder(
            "P001", 2, "Visa", "Almaty"
        );
        System.out.println("Order result: " + result1);

        System.out.println();

        System.out.println("=== Order 2: Out of Stock ===");
        boolean result2 = orderFacade.placeOrder(
            "P002", 15, "Visa", "Astana"
        );
        System.out.println("Order result: " + result2);

        System.out.println();

        System.out.println("=== Order 3: Payment Failed ===");
        boolean result3 = orderFacade.placeOrder(
            "P003", 3, "", "Shymkent"
        );
        System.out.println("Order result: " + result3);
    }
}