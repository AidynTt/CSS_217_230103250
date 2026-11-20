package week12_practice.FacadeCodingQuestions.task04;

class CPU {
    public void freeze() {
        System.out.println("CPU: Freezing processor...");
    }

    public void jump(long position) {
        System.out.println("CPU: Jumping to position " + position);
    }

    public void execute() {
        System.out.println("CPU: Executing instructions...");
    }
}

class Memory {
    public void load(long position, String data) {
        System.out.println("Memory: Loading " + data
                + " at position " + position);
    }
}

class HardDrive {
    public String read(long sector, int size) {
        System.out.println("HardDrive: Reading " + size
                + " bytes from sector " + sector);
        return "Boot Data";
    }
}

class ComputerFacade {
    private CPU cpu = new CPU();
    private Memory memory = new Memory();
    private HardDrive hardDrive = new HardDrive();

    public void start() {
        System.out.println("Starting computer...");

        cpu.freeze();

        String data = hardDrive.read(0, 1024);
        memory.load(0, data);

        cpu.jump(0);
        cpu.execute();

        System.out.println("Computer started successfully!");
    }
}

public class Main {
    public static void main(String[] args) {
        ComputerFacade computer = new ComputerFacade();
        computer.start();
    }
}