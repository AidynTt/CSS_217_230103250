package task08;

class Battery {
    boolean checkVoltage() {
        System.out.println("Battery voltage: 12.6V");
        return true;
    }
}

class FuelPump {
    void prime() {
        System.out.println("Fuel pump started");
    }

    void stop() {
        System.out.println("Fuel pump stopped");
    }
}

class StarterMotor {
    void crank() {
        System.out.println("Starter motor running");
        System.out.println("Engine started");
    }
}

class DashboardDisplay {
    void turnOn() {
        System.out.println("Dashboard lights ON");
    }

    void turnOff() {
        System.out.println("Dashboard lights OFF");
    }
}

class CarIgnitionFacade {
    private final Battery battery;
    private final FuelPump pump;
    private final StarterMotor starter;
    private final DashboardDisplay dashboard;
    private boolean running = false;

    CarIgnitionFacade(Battery battery, FuelPump pump,
                      StarterMotor starter, DashboardDisplay dashboard) {
        this.battery = battery;
        this.pump = pump;
        this.starter = starter;
        this.dashboard = dashboard;
    }

    void startEngine() {
        System.out.println("Starting Car ");

        if (running) {
            System.out.println("Engine is already running");
            return;
        }

        if (!battery.checkVoltage()) {
            System.out.println("Battery problem");
            return;
        }

        pump.prime();
        starter.crank();
        dashboard.turnOn();
        running = true;
        System.out.println("Car is ready to drive");
    }

    void stopEngine() {
        System.out.println("Stopping Car");

        if (!running) {
            System.out.println("Engine is already off");
            return;
        }

        pump.stop();
        dashboard.turnOff();
        running = false;
        System.out.println("Engine stopped");
    }
}

public class Main {
    public static void main(String[] args) {
        CarIgnitionFacade car = new CarIgnitionFacade(
            new Battery(),
            new FuelPump(),
            new StarterMotor(),
            new DashboardDisplay()
        );

        car.startEngine();

        System.out.println();

        car.stopEngine();
    }
}
