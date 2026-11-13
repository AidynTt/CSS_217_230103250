package task12;

//ChatGPT helped me write this code

class DoorSensor {
    private boolean closed = false;

    void closeDoor() {
        closed = true;
        System.out.println("Door closed");
    }

    void openDoor() {
        closed = false;
        System.out.println("Door opened");
    }

    boolean isClosed() {
        return closed;
    }
}

class WaterValve {
    void fillWater(String temperature) {
        System.out.println("Filling with " + temperature + " water");
    }

    void stopWater() {
        System.out.println("Water supply stopped");
    }
}

// Adds detergent
class DetergentDispenser {
    void addDetergent(String amount) {
        System.out.println("Adding " + amount + " detergent");
    }
}

// Controls the washing drum
class DrumMotor {
    void spin(int minutes, String speed) {
        System.out.println("Washing for " + minutes + " minutes");
        System.out.println("Spin speed: " + speed);
    }
}

// Drains water after washing
class DrainPump {
    void drain() {
        System.out.println("Draining water");
    }
}

class WashingMachineFacade {
    private DoorSensor door;
    private WaterValve water;
    private DetergentDispenser detergent;
    private DrumMotor drum;
    private DrainPump drainPump;

    WashingMachineFacade(DoorSensor door, WaterValve water,
                         DetergentDispenser detergent,
                         DrumMotor drum, DrainPump drainPump) {
        this.door = door;
        this.water = water;
        this.detergent = detergent;
        this.drum = drum;
        this.drainPump = drainPump;
    }

    void closeDoor() {
        door.closeDoor();
    }

    void openDoor() {
        door.openDoor();
    }

    // Quick wash use cold water and gentle spinning
    void runQuickWash() {
        System.out.println("Quick Wash");

        if (!door.isClosed()) {
            System.out.println("Cannot start. Door is open!");
            return;
        }

        water.fillWater("cold");
        water.stopWater();
        detergent.addDetergent("normal");
        drum.spin(15, "gentle");
        drainPump.drain();

        System.out.println("Quick wash finished");
    }

    // Heavy wash uses warm water and extra detergent
    void runHeavyWash() {
        System.out.println("Heavy Wash");

        if (!door.isClosed()) {
            System.out.println("Cannot start. Door is open!");
            return;
        }

        water.fillWater("warm");
        water.stopWater();
        detergent.addDetergent("extra");
        drum.spin(45, "high");
        drainPump.drain();

        System.out.println("Heavy wash finished");
    }
}

public class Main {
    public static void main(String[] args) {
        WashingMachineFacade machine = new WashingMachineFacade(
            new DoorSensor(),
            new WaterValve(),
            new DetergentDispenser(),
            new DrumMotor(),
            new DrainPump()
        );

        // Test with open door
        machine.runQuickWash();

        System.out.println();

        // Close door and start quick wash
        machine.closeDoor();
        machine.runQuickWash();

        System.out.println();

        // Start heavy wash
        machine.runHeavyWash();

        System.out.println();

        machine.openDoor();
    }
}
