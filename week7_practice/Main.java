package week7_practice;

import java.util.Arrays;

interface DiscountStrategy{
    double applyDiscount(double price);
}

class FlatDiscount implements DiscountStrategy {
    public double applyDiscount(double price) {
        return Math.max(0, price - 50.0);
    }
}

class PercentageDiscount implements DiscountStrategy {
    public double applyDiscount(double price) {
        return price * 0.85;
    }
}

class SeasonalDiscount implements DiscountStrategy {
    public double applyDiscount(double price) {
        return price > 500 ? price * 0.75 : price * 0.95;
    }
}

class ShoppingCart {
    private DiscountStrategy strategy;

    ShoppingCart(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public double checkout(double total) {
        return strategy.applyDiscount(total);
    }

    public void setStrategy(DiscountStrategy strategy) {
        this.strategy = strategy;
    }
}

//task2

interface Beverage {
    String getDescription();
    double getCost();
}

class Espresso implements Beverage {
    public String getDescription() {
        return "Espresso";
    }

    public double getCost() {
        return 100.0;
    }
}

abstract class BeverageDecorator implements Beverage {
    protected Beverage beverage;

    BeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }
}

class MilkDecorator extends BeverageDecorator {
    MilkDecorator(Beverage beverage) {
        super(beverage);
    }

    public String getDescription() {
        return beverage.getDescription() + ", Milk";
    }

    public double getCost() {
        return beverage.getCost() + 20.0;
    }
}

class CaramelDecorator extends BeverageDecorator {
    CaramelDecorator(Beverage beverage) {
        super(beverage);
    }

    public String getDescription() {
        return beverage.getDescription() + ", Caramel";
    }

    public double getCost() {
        return beverage.getCost() + 35.0;
    }
}

class WhippedCreamDecorator extends BeverageDecorator {
    WhippedCreamDecorator(Beverage beverage) {
        super(beverage);
    }

    public String getDescription() {
        return beverage.getDescription() + ", Whipped Cream";
    }

    public double getCost() {
        return beverage.getCost() + 25.0;
    }
}

//task3

interface CelsiusThermometer{
    double getTemperatureC();
}
class FahrenheitSensor {
    private double temperature;

    FahrenheitSensor(double temperature) {
        this.temperature = temperature;
    }

    public double readFahrenheit() {
        return temperature;
    }
}

class SensorAdapter implements CelsiusThermometer {
    private FahrenheitSensor sensor;

    SensorAdapter(FahrenheitSensor sensor) {
        this.sensor = sensor;
    }

    public double getTemperatureC() {
        return (sensor.readFahrenheit() - 32.0) * 5.0 / 9.0;
    }
}

//task4

interface SortStrategy{
    void sort(int[] data);
}

class BubbleSortStrategy implements SortStrategy {
    public void sort(int[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            for (int j = 0; j < data.length - 1 - i; j++) {
                if (data[j] > data[j + 1]) {
                    int temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }
            }
        }
    }
}

class SelectionSortStrategy implements SortStrategy {
    public void sort(int[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            int min = i;

            for (int j = i + 1; j < data.length; j++) {
                if (data[j] < data[min]) {
                    min = j;
                }
            }

            int temp = data[i];
            data[i] = data[min];
            data[min] = temp;
        }
    }
}

class InsertionSortStrategy implements SortStrategy {
    public void sort(int[] data) {
        for (int i = 1; i < data.length; i++) {
            int key = data[i];
            int j = i - 1;

            while (j >= 0 && data[j] > key) {
                data[j + 1] = data[j];
                j--;
            }

            data[j + 1] = key;
        }
    }
}

class DataProcessor {
    private SortStrategy strategy;

    DataProcessor(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void process(int[] data) {
        strategy.sort(data);
        System.out.println(Arrays.toString(data));
    }
}

//task5

interface TextComponent {
    String format(String input);
}

class PlainText implements TextComponent {
    public String format(String input) {
        return input;
    }
}

abstract class TextDecorator implements TextComponent {
    protected TextComponent component;

    TextDecorator(TextComponent component) {
        this.component = component;
    }
}

class UpperCaseDecorator extends TextDecorator {
    UpperCaseDecorator(TextComponent component) {
        super(component);
    }

    public String format(String input) {
        return component.format(input).toUpperCase();
    }
}

class TrimDecorator extends TextDecorator {
    TrimDecorator(TextComponent component) {
        super(component);
    }

    public String format(String input) {
        return component.format(input).trim();
    }
}

class BracketDecorator extends TextDecorator {
    BracketDecorator(TextComponent component) {
        super(component);
    }

    public String format(String input) {
        return "[" + component.format(input) + "]";
    }
}

//task6
interface ModernPrinter {
    void printDocument(String text);
}

class DotMatrixPrinter {
    public void printCharArray(char[] buffer) {
        for (int i = 0; i < buffer.length; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(buffer[i]);
        }
        System.out.println();
    }
}

class PrinterAdapter implements ModernPrinter {
    private DotMatrixPrinter printer;

    PrinterAdapter(DotMatrixPrinter printer) {
        this.printer = printer;
    }

    public void printDocument(String text) {
        printer.printCharArray(text.toCharArray());
    }
}

//task7

interface ShippingStrategy {
    double calculate(double weightKg, double distanceKm);
}

class StandardShipping implements ShippingStrategy {
    public double calculate(double weightKg, double distanceKm) {
        return weightKg * 2.0 + distanceKm * 0.5;
    }
}

class ExpressShipping implements ShippingStrategy {
    public double calculate(double weightKg, double distanceKm) {
        return weightKg * 5.0 + distanceKm * 1.2 + 50.0;
    }
}

class DroneShipping implements ShippingStrategy {
    public double calculate(double weightKg, double distanceKm) {
        return weightKg < 5.0 ? 100.0 : -1.0;
    }
}

//task8

interface Pizza{
    String getDetails();
    int getPrice();

}

class Margherita implements Pizza {
    public String getDetails(){
        return "Margherita";


    }
    public int getPrice(){
        return 200;
    }
}
abstract class PizzaDecorator implements Pizza{
    protected Pizza pizza;

    PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;
    }

}

class ExtraCheese extends PizzaDecorator {
    ExtraCheese(Pizza pizza) {
        super(pizza);
    }

    public String getDetails() {
        return pizza.getDetails() + ", Extra Cheese";
    }

    public int getPrice() {
        return pizza.getPrice() + 40;
    }
}

class Olives extends PizzaDecorator {
    Olives(Pizza pizza) {
        super(pizza);
    }

    public String getDetails() {
        return pizza.getDetails() + ", Olives";
    }

    public int getPrice() {
        return pizza.getPrice() + 30;
    }
}

class Jalapenos extends PizzaDecorator {
    Jalapenos(Pizza pizza) {
        super(pizza);
    }

    public String getDetails() {
        return pizza.getDetails() + ", Jalapenos";
    }

    public int getPrice() {
        return pizza.getPrice() + 25;
    }
}

//task9

interface MetricSpeedometer{
    double getSpeedKmh();
}
class ImperialSpeedometer {
    private double speedMph;

    ImperialSpeedometer(double speedMph) {
        this.speedMph = speedMph;
    }

    public double getSpeedMph(){
        return speedMph;

    }
}

class SpeedAdapter implements MetricSpeedometer {
    private ImperialSpeedometer speedometer;

    SpeedAdapter(ImperialSpeedometer speedometer) {
        this.speedometer = speedometer;
    }

    public double getSpeedKmh() {
        return speedometer.getSpeedMph() * 1.60934;
    }
}

//task10

interface ValidationStrategy{
    boolean isValid(String password);

}

class LengthValidator implements ValidationStrategy{
    public boolean isValid(String password) {
        return password.length() >= 8;
    }
}

class DigitValidator implements ValidationStrategy {
    public boolean isValid(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) >= '0'
                    && password.charAt(i) <= '9') {
                return true;
            }
        }
        return false;
    }
}

class NoSpaceValidator implements ValidationStrategy {
    public boolean isValid(String password) {
        return !password.contains(" ");
    }
}




















//main
public class Main {
    public static void main(String[] args) {

        ShoppingCart cart = new ShoppingCart(new FlatDiscount());
        System.out.println("Task 1: " + cart.checkout(200));

        Beverage coffee = new Espresso();
        coffee = new MilkDecorator(coffee);
        coffee = new CaramelDecorator(coffee);
        coffee = new WhippedCreamDecorator(coffee);
        System.out.println("Task 2: " + coffee.getDescription()
                + " = " + coffee.getCost());

        CelsiusThermometer thermometer = new SensorAdapter(new FahrenheitSensor(98.6));
        System.out.println("Task 3: "
                + thermometer.getTemperatureC());

        int[] numbers = {5, 2, 8, 1, 3};
        DataProcessor processor = new DataProcessor(new BubbleSortStrategy());
        System.out.print("Task 4: ");
        processor.process(numbers);

        processor.setStrategy(new InsertionSortStrategy());
        System.out.print("Task 4 (Insertion): ");
        processor.process(new int[]{9, 4, 2, 7});

        TextComponent text = new PlainText();
        text = new TrimDecorator(text);
        text = new UpperCaseDecorator(text);
        text = new BracketDecorator(text);
        System.out.println("Task 5: "
                + text.format("  hello world  "));

        ModernPrinter printer = new PrinterAdapter(new DotMatrixPrinter());
        System.out.print("Task 6: ");
        printer.printDocument("HELLO");

        ShippingStrategy shipping = new StandardShipping();
        System.out.println("Task 7: "
                + shipping.calculate(10, 20));

        Pizza pizza = new Margherita();
        pizza = new ExtraCheese(pizza);
        pizza = new Olives(pizza);
        pizza = new Jalapenos(pizza);
        System.out.println("Task 8: " + pizza.getDetails()
                + " = " + pizza.getPrice());

        MetricSpeedometer speed = new SpeedAdapter(new ImperialSpeedometer(60));
        System.out.println("Task 9: " + speed.getSpeedKmh());

        String password = "12345678";
        System.out.println("Task 10: "
                + new LengthValidator().isValid(password) + ", "
                + new DigitValidator().isValid(password) + ", "
                + new NoSpaceValidator().isValid(password));









    }
}
