package task04;

class CeilingLight {
    void on() {
        System.out.println("Lights ON");
    }

    void off() {
        System.out.println("Lights OFF");
    }
}

class Thermostat {
    void setTemperature(int temperature) {
        System.out.println("Temperature set to " + temperature + "°C");
    }
}

class WindowBlinds {
    void open() {
        System.out.println("Blinds OPEN");
    }

    void close() {
        System.out.println("Blinds CLOSED");
    }
}

class DoorLock {
    void lock() {
        System.out.println("Door LOCKED");
    }

    void unlock() {
        System.out.println("Door UNLOCKED");
    }
}

class SmartRoomFacade {
    private final CeilingLight light;
    private final Thermostat thermostat;
    private final WindowBlinds blinds;
    private final DoorLock door;

    SmartRoomFacade(CeilingLight light, Thermostat thermostat,
                    WindowBlinds blinds, DoorLock door) {
        this.light = light;
        this.thermostat = thermostat;
        this.blinds = blinds;
        this.door = door;
    }

    void activateNightMode() {
        System.out.println("Night Mode");
        light.off();
        blinds.close();
        door.lock();
        thermostat.setTemperature(19);
        System.out.println("Night mode activated!");
    }

    void activateMorningMode() {
        System.out.println("Morning Mode");
        blinds.open();
        light.on();
        thermostat.setTemperature(22);
        System.out.println("Morning mode activated");
    }
}

public class Main {
    public static void main(String[] args) {
        SmartRoomFacade room = new SmartRoomFacade(
            new CeilingLight(),
            new Thermostat(),
            new WindowBlinds(),
            new DoorLock()
        );

        room.activateNightMode();

        System.out.println();

        room.activateMorningMode();
    }
}
