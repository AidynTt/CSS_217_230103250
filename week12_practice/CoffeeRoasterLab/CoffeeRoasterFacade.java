package week12_practice.CoffeeRoasterLab;

public class CoffeeRoasterFacade {

    private final BeanHopperGate hopperGate;
    private final RoastingDrumMotor drumMotor;
    private final HeatingChamber heatingChamber;
    private final CoolingTraySystem coolingTray;
    private final TelemetryLogger logger;

    public CoffeeRoasterFacade() {
        this.hopperGate = new BeanHopperGate();
        this.drumMotor = new RoastingDrumMotor();
        this.heatingChamber = new HeatingChamber();
        this.coolingTray = new CoolingTraySystem();
        this.logger = new TelemetryLogger();
    }

    public boolean runRoastBatch(String beanOrigin,
                                 int batchGrams,
                                 int drumRpm,
                                 int targetTempCelsius) {

        try {
            // Step 1: Start batch
            logger.resetBatchLog();
            logger.logState("BATCH", "Starting roast: " + beanOrigin);

            // Step 2: Load beans
            hopperGate.loadGreenBeans(batchGrams);

            // Step 3: Start drum
            drumMotor.startRotation(drumRpm);

            // Step 4: Transfer beans into drum
            hopperGate.openGate();
            hopperGate.closeGate();

            // Step 5: Ignite burner
            heatingChamber.igniteBurner(drumMotor.isSpinning());
            logger.logState("HEATING", "Burner ignited safely.");

            // Step 6: Heat to target temperature
            heatingChamber.heatToTarget(targetTempCelsius);
            logger.logState("ROASTING", "Roasting " + beanOrigin
                    + " at " + targetTempCelsius + "C.");

            // Step 7: Turn off burner
            heatingChamber.extinguishBurner();

            // Step 8: Start cooling
            coolingTray.startCoolingFan();
            coolingTray.openDischargeDoor(
                    heatingChamber.isBurnerIgnited()
            );

            // Step 9: Finish roasting
            drumMotor.stopRotation();
            coolingTray.finishCoolingCycle();

            logger.logState("COMPLETE", "Batch completed: " + beanOrigin);

            return true;

        } catch (RuntimeException ex) {
            emergencyAbort(ex.getMessage());
            return false;
        }
    }

    public boolean roastLightProfile(String beanOrigin, int batchGrams) {
        return runRoastBatch(beanOrigin, batchGrams, 55, 196);
    }

    public boolean roastDarkProfile(String beanOrigin, int batchGrams) {
        return runRoastBatch(beanOrigin, batchGrams, 42, 230);
    }

    public void emergencyAbort(String reason) {

        logger.logState(
                "ABORT",
                "Emergency shutdown triggered: " + reason
        );

        heatingChamber.extinguishBurner();
        hopperGate.closeGate();
        drumMotor.stopRotation();
        coolingTray.finishCoolingCycle();
    }
}