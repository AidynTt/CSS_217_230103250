package week12_practice.CoffeeRoasterLab;

public class RoastingDrumMotor {

    private int currentRpm = 0;

    public void startRotation(int targetRpm) {
        if (targetRpm < 30 || targetRpm > 65) {
            throw new IllegalArgumentException(
                "Drum RPM must be between 30 and 65."
            );
        }

        this.currentRpm = targetRpm;

        System.out.println(
            "[DrumMotor] Drum rotating at " + currentRpm + " RPM."
        );
    }

    public void adjustRpm(int newRpm) {
        if (currentRpm == 0) {
            throw new IllegalStateException(
                "Cannot adjust RPM while drum motor is stopped."
            );
        }

        startRotation(newRpm);
    }

    public void stopRotation() {
        this.currentRpm = 0;

        System.out.println(
            "[DrumMotor] Drum rotation STOPPED (0 RPM)."
        );
    }

    public boolean isSpinning() {
        return currentRpm > 0;
    }
}
