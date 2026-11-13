package task03;

class PowerSupply {
    void turnOn() {
        System.out.println("Power supply ON");
    }

    void turnOff() {
        System.out.println("Power supply OFF");
    }
}

class BIOS {
    void runPOST() {
        System.out.println("BIOS: Running POST diagnostics");
        System.out.println("BIOS: All hardware OK");
    }
}

class HardDrive {
    void spinUp() {
        System.out.println("Hard drive spinning up");
    }

    void readBootSector() {
        System.out.println("Reading boot sector");
    }

    void stop() {
        System.out.println("Hard drive stopped");
    }
}

class OperatingSystem {
    void loadKernel() {
        System.out.println("Loading OS kernel");
        System.out.println("Operating system ready");
    }

    void shutdown() {
        System.out.println("Operating system shutting down");
    }
}

class ComputerFacade {
    private final PowerSupply power;
    private final BIOS bios;
    private final HardDrive hardDrive;
    private final OperatingSystem os;

    ComputerFacade(PowerSupply power, BIOS bios,
                   HardDrive hardDrive, OperatingSystem os) {
        this.power = power;
        this.bios = bios;
        this.hardDrive = hardDrive;
        this.os = os;
    }

    void startComputer() {
        System.out.println("Starting Computer");
        power.turnOn();
        bios.runPOST();
        hardDrive.spinUp();
        hardDrive.readBootSector();
        os.loadKernel();
        System.out.println("Computer started successfully!");
    }

    void shutDownComputer() {
        System.out.println("hutting Down Computer");
        os.shutdown();
        hardDrive.stop();
        power.turnOff();
        System.out.println("Computer is OFF");
    }
}

public class Main {
    public static void main(String[] args) {
        ComputerFacade computer = new ComputerFacade(
            new PowerSupply(),
            new BIOS(),
            new HardDrive(),
            new OperatingSystem()
        );

        computer.startComputer();

        System.out.println();

        computer.shutDownComputer();
    }
}
