
package task15;

import java.util.HashMap;
import java.util.Map;

// Stores available ingredients
class IngredientStock {
    private Map<String, Integer> stock = new HashMap<>();

    IngredientStock() {
        stock.put("Chicken", 2);
        stock.put("Beef", 1);
        stock.put("Greek", 2);
        stock.put("Caesar", 1);
    }

    boolean isAvailable(String item) {
        return stock.getOrDefault(item, 0) > 0;
    }

    void useIngredient(String item) {
        int amount = stock.get(item);
        stock.put(item, amount - 1);
        System.out.println(item + " stock left: " + (amount - 1));
    }
}

// Prepares the main dish
class GrillStation {
    void cook(String dish) {
        System.out.println("Cooking " + dish);
    }
}

// Prepares the side salad
class SaladStation {
    void prepare(String salad) {
        System.out.println("Preparing " + salad + " salad");
    }
}

// Puts the meal together
class PlatingCounter {
    void plate(String dish, String salad) {
        System.out.println("Plating " + dish + " with " + salad + " salad");
    }
}

// Calls the waiter
class WaiterCallSystem {
    void notifyWaiter(int table) {
        System.out.println("Order ready for table " + table);
    }
}

class KitchenFacade {
    private IngredientStock stock;
    private GrillStation grill;
    private SaladStation salad;
    private PlatingCounter plating;
    private WaiterCallSystem waiter;

    KitchenFacade(IngredientStock stock, GrillStation grill,
                  SaladStation salad, PlatingCounter plating,
                  WaiterCallSystem waiter) {
        this.stock = stock;
        this.grill = grill;
        this.salad = salad;
        this.plating = plating;
        this.waiter = waiter;
    }

    // Checks stock before preparing a combo meal
    boolean prepareComboMeal(String mainDish, String sideSalad,
                             int tableNumber) {
        System.out.println("Order for table " + tableNumber);

        if (tableNumber <= 0) {
            System.out.println("Invalid table number");
            return false;
        }

        if (!stock.isAvailable(mainDish)) {
            System.out.println("Main dish is not available");
            return false;
        }

        if (!stock.isAvailable(sideSalad)) {
            System.out.println("Salad is not available");
            return false;
        }

        stock.useIngredient(mainDish);
        stock.useIngredient(sideSalad);

        grill.cook(mainDish);
        salad.prepare(sideSalad);
        plating.plate(mainDish, sideSalad);
        waiter.notifyWaiter(tableNumber);

        System.out.println("Combo meal completed");
        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        KitchenFacade kitchen = new KitchenFacade(
            new IngredientStock(),
            new GrillStation(),
            new SaladStation(),
            new PlatingCounter(),
            new WaiterCallSystem()
        );

        // First order
        kitchen.prepareComboMeal("Chicken", "Greek", 5);

        System.out.println();

        // Second order
        kitchen.prepareComboMeal("Beef", "Caesar", 8);

        System.out.println();

        // Third order
        kitchen.prepareComboMeal("Chicken", "Greek", 3);

        System.out.println();

        // Not enough ingredients
        kitchen.prepareComboMeal("Chicken", "Greek", 1);
    }
}
