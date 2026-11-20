package week12_practice.CoffeeRoasterLab;

public class HeatingChamber {

    private boolean burnerIgnited = false;
    private int currentTempCelsius = 25;

    public void igniteBurner(boolean drumIsSpinning) {
        if (!drumIsSpinning) {
            throw new IllegalStateException(
                "SAFETY FAULT: Cannot ignite burner while drum is stationary!"
            );
        }

        this.burnerIgnited = true;
        System.out.println("[HeatingChamber] Gas burner IGNITED.");
    }

    public void heatToTarget(int targetTempCelsius) {
        if (!burnerIgnited) {
            throw new IllegalStateException(
                "Cannot heat: burner is not ignited."
            );
        }

        if (targetTempCelsius < 160 || targetTempCelsius > 245) {
            throw new IllegalArgumentException(
                "Target roast temperature must be between 160C and 245C."
            );
        }

        this.currentTempCelsius = targetTempCelsius;

        System.out.println(
            "[HeatingChamber] Chamber stabilized at "
            + currentTempCelsius + "°C."
        );
    }

    public void extinguishBurner() {
        this.burnerIgnited = false;
        this.currentTempCelsius = 40;

        System.out.println(
            "[HeatingChamber] Burner EXTINGUISHED. Venting residual heat."
        );
    }

    public int getCurrentTempCelsius() {
        return currentTempCelsius;
    }

    public boolean isBurnerIgnited() {
        return burnerIgnited;
    }
}