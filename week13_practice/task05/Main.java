package week13_practice.task05;

interface BrewingStrategy {
    int getTemperature();
    int getExtractionTime();
}

class EspressoPullStrategy implements BrewingStrategy {
    public int getTemperature() {
        return 93;
    }

    public int getExtractionTime() {
        return 30;
    }
}

class PourOverStrategy implements BrewingStrategy {
    public int getTemperature() {
        return 95;
    }

    public int getExtractionTime() {
        return 180;
    }
}

class FrenchPressStrategy implements BrewingStrategy {
    public int getTemperature() {
        return 92;
    }

    public int getExtractionTime() {
        return 240;
    }
}

class BeanGrinder {
    public void grind(String beanType) {
        System.out.println("Grinding " + beanType + " beans");
    }
}

class WaterBoiler {
    public void heat(int temperature) {
        System.out.println("Heating water to " + temperature + " C");
    }
}

class BrewChamber {
    public void brew(int seconds) {
        System.out.println("Brewing for " + seconds + " seconds");
    }
}

class BaristaFacade {
    private BrewingStrategy strategy;

    private final BeanGrinder grinder = new BeanGrinder();
    private final WaterBoiler boiler = new WaterBoiler();
    private final BrewChamber chamber = new BrewChamber();

    public BaristaFacade(BrewingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(BrewingStrategy strategy) {
        this.strategy = strategy;
    }

    public void prepareBeverage(String beanType) {
        grinder.grind(beanType);
        boiler.heat(strategy.getTemperature());
        chamber.brew(strategy.getExtractionTime());

        System.out.println("Coffee is ready!");
    }
}

public class Main {
    public static void main(String[] args) {
        BaristaFacade barista =
                new BaristaFacade(new EspressoPullStrategy());

        System.out.println("Espresso:");
        barista.prepareBeverage("Arabica");

        System.out.println("\nPour Over:");
        barista.setStrategy(new PourOverStrategy());
        barista.prepareBeverage("Arabica");

        System.out.println("\nFrench Press:");
        barista.setStrategy(new FrenchPressStrategy());
        barista.prepareBeverage("Robusta");
    }
}
