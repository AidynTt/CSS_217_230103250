package week12_practice.CoffeeRoasterLab;

public class BeanHopperGate {

    private boolean gateOpen = false;
    private int stagedBeansGrams = 0;

    public void loadGreenBeans(int grams) {
        if (gateOpen) {
            throw new IllegalStateException(
                "Cannot load beans while hopper gate is open."
            );
        }

        if (grams < 250 || grams > 5000) {
            throw new IllegalArgumentException(
                "Batch size must be between 250g and 5000g."
            );
        }

        this.stagedBeansGrams = grams;

        System.out.println(
            "[HopperGate] Loaded " + grams + "g of green beans into hopper."
        );
    }

    public void openGate() {
        if (stagedBeansGrams == 0) {
            throw new IllegalStateException(
                "Cannot open gate: hopper is empty."
            );
        }

        this.gateOpen = true;

        System.out.println(
            "[HopperGate] Gate OPENED. Dropping "
            + stagedBeansGrams + "g into drum."
        );

        this.stagedBeansGrams = 0;
    }

    public void closeGate() {
        this.gateOpen = false;

        System.out.println(
            "[HopperGate] Gate CLOSED and sealed."
        );
    }

    public boolean isGateOpen() {
        return gateOpen;
    }
}