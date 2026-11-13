package task06;

class WaterFilter {
    void cycleFilter() {
        System.out.println("Water filter is cleaning the water");
    }
}

class AquariumHeater {
    void setTemperature(int temperature) {
        System.out.println("Water temperature set to " + temperature + "°C");
    }
}

class AutoFeeder {
    void dispenseFood(int pinches) {
        System.out.println("Feeding fish: " + pinches + " pinch(es)");
    }
}

class LedHoodLight {
    void daylightMode() {
        System.out.println("Daylight mode ON");
    }

    void moonlightMode() {
        System.out.println("Moonlight mode ON");
    }
}

class AquariumFacade {
    private final WaterFilter filter;
    private final AquariumHeater heater;
    private final AutoFeeder feeder;
    private final LedHoodLight light;

    AquariumFacade(WaterFilter filter, AquariumHeater heater,
                   AutoFeeder feeder, LedHoodLight light) {
        this.filter = filter;
        this.heater = heater;
        this.feeder = feeder;
        this.light = light;
    }

    void runMorningRoutine() {
        System.out.println(" Morning Routine");
        filter.cycleFilter();
        heater.setTemperature(26);
        feeder.dispenseFood(2);
        light.daylightMode();
        System.out.println("Morning routine completed!");
    }

    void prepareForNight() {
        filter.cycleFilter();
        heater.setTemperature(25);
        light.moonlightMode();
        System.out.println("Aquarium is ready for night");
    }
}

public class Main {
    public static void main(String[] args) {
        AquariumFacade aquarium = new AquariumFacade(
            new WaterFilter(),
            new AquariumHeater(),
            new AutoFeeder(),
            new LedHoodLight()
        );

        aquarium.runMorningRoutine();

        System.out.println();

        aquarium.prepareForNight();
    }
}

