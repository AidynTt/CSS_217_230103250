
package week13_practice.task01;

// Strategy interface
interface LightingStrategy {
    int getBrightness();
    int getColorTemperature();
}

// Concrete strategies
class EcoDimStrategy implements LightingStrategy {
    public int getBrightness() {
        return 30;
    }

    public int getColorTemperature() {
        return 2700;
    }
}

class ReadingFocusStrategy implements LightingStrategy {
    public int getBrightness() {
        return 80;
    }

    public int getColorTemperature() {
        return 5000;
    }
}

class AmbientPartyStrategy implements LightingStrategy {
    public int getBrightness() {
        return 60;
    }

    public int getColorTemperature() {
        return 3000;
    }
}

// Subsystems
class PowerGridMonitor {
    public boolean isPowerAvailable() {
        return true;
    }
}

class RoomSensor {
    public void checkRoom(String roomId) {
        System.out.println("Checking room: " + roomId);
    }
}

class DimmerHardware {
    public void setLighting(int brightness, int temperature) {
        System.out.println("Brightness: " + brightness + "%");
        System.out.println("Color temperature: " + temperature + "K");
    }
}

// Facade
class HomeLightingFacade {
    private LightingStrategy strategy;
    private final PowerGridMonitor power = new PowerGridMonitor();
    private final RoomSensor sensor = new RoomSensor();
    private final DimmerHardware dimmer = new DimmerHardware();

    public HomeLightingFacade(LightingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(LightingStrategy strategy) {
        this.strategy = strategy;
    }

    public void applyScene(String roomId) {
        if (!power.isPowerAvailable()) {
            System.out.println("Power is unavailable");
            return;
        }

        sensor.checkRoom(roomId);
        dimmer.setLighting(
            strategy.getBrightness(),
            strategy.getColorTemperature()
        );
    }
}

// Client
public class Main {
    public static void main(String[] args) {
        HomeLightingFacade home =
                new HomeLightingFacade(new EcoDimStrategy());

        System.out.println("Eco Mode:");
        home.applyScene("Living Room");

        System.out.println("\nReading Mode:");
        home.setStrategy(new ReadingFocusStrategy());
        home.applyScene("Living Room");

        System.out.println("\nParty Mode:");
        home.setStrategy(new AmbientPartyStrategy());
        home.applyScene("Living Room");
    }
}
