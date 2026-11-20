package week12_practice.CoffeeRoasterLab;

public class CoolingTraySystem {

    private boolean coolingFanRunning = false;
    private boolean dischargeDoorOpen = false;

    public void startCoolingFan() {
        this.coolingFanRunning = true;

        System.out.println(
            "[CoolingTray] Cooling exhaust fan and agitator STARTED."
        );
    }

    public void openDischargeDoor(boolean burnerStillOn) {
        if (burnerStillOn) {
            throw new IllegalStateException(
                "SAFETY FAULT: Extinguish burner before opening discharge door!"
            );
        }

        if (!coolingFanRunning) {
            throw new IllegalStateException(
                "Start cooling fan before discharging hot beans."
            );
        }

        this.dischargeDoorOpen = true;

        System.out.println(
            "[CoolingTray] Discharge door OPENED. Beans transferred to cooling tray."
        );
    }

    public void finishCoolingCycle() {
        this.dischargeDoorOpen = false;
        this.coolingFanRunning = false;

        System.out.println(
            "[CoolingTray] Discharge door CLOSED. Cooling fan STOPPED."
        );
    }
}