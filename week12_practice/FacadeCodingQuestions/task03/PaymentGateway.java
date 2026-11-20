package week12_practice.FacadeCodingQuestions.task03;

public class PaymentGateway {

    public boolean processPayment(String paymentInfo) {
        System.out.println("Processing payment...");

        if (paymentInfo == null || paymentInfo.isEmpty()) {
            System.out.println("Payment failed: Invalid payment information");
            return false;
        }

        System.out.println("Payment successful!");
        return true;
    }
}