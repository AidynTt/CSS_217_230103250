package week12_practice.CoffeeRoasterLab;

public class TelemetryLogger {

    private int sequenceNumber = 0;

    public void logState(String subsystemTag, String eventMessage) {
        sequenceNumber++;

        System.out.printf(
            "[LOG #%02d] [%-14s] %s%n",
            sequenceNumber,
            subsystemTag,
            eventMessage
        );
    }

    public void resetBatchLog() {
        this.sequenceNumber = 0;

        System.out.println(
            "[TelemetryLogger] Batch log sequence reset."
        );
    }
}