package week13_practice.task03;

// Strategy interface
interface ShippingRateStrategy {
    double calculateCost(double weight, double distance);
}

// Concrete strategies
class StandardGroundStrategy implements ShippingRateStrategy {
    public double calculateCost(double weight, double distance) {
        return weight * 2 + distance * 0.1;
    }
}

class ExpressAirStrategy implements ShippingRateStrategy {
    public double calculateCost(double weight, double distance) {
        return weight * 5 + distance * 0.3;
    }
}

class SameDayCourierStrategy implements ShippingRateStrategy {
    public double calculateCost(double weight, double distance) {
        return weight * 8 + distance * 0.5;
    }
}

// Order
class Order {
    String orderId;
    double weight;
    double distance;

    public Order(String orderId, double weight, double distance) {
        this.orderId = orderId;
        this.weight = weight;
        this.distance = distance;
    }
}

// Subsystems
class BoxSelector {
    public String selectBox(double weight) {
        if (weight <= 2) {
            return "Small box";
        } else if (weight <= 5) {
            return "Medium box";
        } else {
            return "Large box";
        }
    }
}

class RateCalculator {
    public double calculate(Order order, ShippingRateStrategy strategy) {
        return strategy.calculateCost(order.weight, order.distance);
    }
}

class LabelPrinter {
    public void printLabel(Order order, String box, double cost) {
        System.out.println("Order ID: " + order.orderId);
        System.out.println("Weight: " + order.weight + " kg");
        System.out.println("Distance: " + order.distance + " km");
        System.out.println("Package: " + box);
        System.out.printf("Shipping cost: $%.2f%n", cost);
    }
}

// Facade
class ShippingFacade {
    private ShippingRateStrategy strategy;

    private final BoxSelector boxSelector = new BoxSelector();
    private final RateCalculator rateCalculator = new RateCalculator();
    private final LabelPrinter labelPrinter = new LabelPrinter();

    public ShippingFacade(ShippingRateStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(ShippingRateStrategy strategy) {
        this.strategy = strategy;
    }

    public void dispatchOrder(Order order) {
        String box = boxSelector.selectBox(order.weight);
        double cost = rateCalculator.calculate(order, strategy);
        labelPrinter.printLabel(order, box, cost);
    }
}

// Client
public class Main {
    public static void main(String[] args) {
        Order order = new Order("ORD001", 4, 100);

        ShippingFacade facade =
                new ShippingFacade(new StandardGroundStrategy());

        System.out.println("Standard Ground:");
        facade.dispatchOrder(order);

        System.out.println("\nExpress Air:");
        facade.setStrategy(new ExpressAirStrategy());
        facade.dispatchOrder(order);

        System.out.println("\nSame Day Courier:");
        facade.setStrategy(new SameDayCourierStrategy());
        facade.dispatchOrder(order);
    }
}
