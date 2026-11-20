package week12_practice.FacadeCodingQuestions.task03;

public class InventoryManager {
    public boolean  checkStock(String productId, int quantity) {
        System.out.println("Checking stock for product: " + productId);
        System.out.println("Requested quantity: " + quantity);

        return quantity>0 && quantity <=10; 

    }
    
}
