package task02;

class WaterHeater {
    void heatWater(int celsius) {
        System.out.println("Heating water to " + celsius + "°C");
    }

    void addHotWater() {
        System.out.println("Adding extra hot water");
    }
}

class CoffeeGrinder {
    void grindBeans(String grindSize) {
        System.out.println("Grinding beans: " + grindSize);
    }
}

class BrewUnit {
    void brew(int seconds) {
        System.out.println("Brewing coffee for " + seconds + " seconds");
    }
}

class CupDispenser {
    void dispenseCup() {
        System.out.println("Dispensing a cup");
    }
}

class CoffeeMachineFacade {
    private final WaterHeater heater;
    private final CoffeeGrinder grinder;
    private final BrewUnit brewer;
    private final CupDispenser cups;

    CoffeeMachineFacade(WaterHeater heater,
                        CoffeeGrinder grinder,
                        BrewUnit brewer,
                        CupDispenser cups) {
        this.heater = heater;
        this.grinder = grinder;
        this.brewer = brewer;
        this.cups = cups;
    }

    void brewEspresso() {
        System.out.println("Making Espresso");
        cups.dispenseCup();
        grinder.grindBeans("Fine");
        heater.heatWater(92);
        brewer.brew(25);
        System.out.println("Espresso is ready!");
    }

    void brewAmericano() {
        System.out.println("Making Americano");
        cups.dispenseCup();
        grinder.grindBeans("Medium");
        heater.heatWater(95);
        brewer.brew(40);
        heater.addHotWater();
        System.out.println("Americano is ready!");
    }
}


public class Main {
    public static void main(String[] args) {
        CoffeeMachineFacade coffee = new CoffeeMachineFacade(
            new WaterHeater(),
            new CoffeeGrinder(),
            new BrewUnit(),
            new CupDispenser()
        );

        coffee.brewEspresso();

        System.out.println();

        coffee.brewAmericano();
    }
}
