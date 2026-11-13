
package task11;

import java.util.*;


class TicketVerifier {
    private Set<String> bookings = new HashSet<>(
        Arrays.asList("AB123", "CD456", "EF789")
    );

    boolean verifyTicket(String bookingRef) {
        return bookings.contains(bookingRef);
    }
}


class SeatAllocator {
    private String[] seats = {"12A", "12B", "12C"};
    private int nextSeat = 0;

    String assignSeat() {
        if (nextSeat >= seats.length) {
            return null;
        }

        String seat = seats[nextSeat];
        nextSeat++;
        return seat;
    }
}

class BaggageHandler {
    boolean checkWeight(double weight) {
        return weight >= 0 && weight <= 23.0;
    }

    String tagBag(String bookingRef, double weight) {
        if (weight == 0) {
            return "No checked baggage";
        }

        return "BAG-" + bookingRef + " (" + weight + " kg)";
    }
}

class BoardingPassGenerator {
    String generatePass(String bookingRef, String seat, String bagTag) {
        return "=== BOARDING PASS ===\n"
                + "Booking: " + bookingRef + "\n"
                + "Seat: " + seat + "\n"
                + "Baggage: " + bagTag + "\n"
                + "Check-in successful!";
    }
}

class AirportCheckInFacade {
    private TicketVerifier tickets = new TicketVerifier();
    private SeatAllocator seats = new SeatAllocator();
    private BaggageHandler baggage = new BaggageHandler();
    private BoardingPassGenerator passes = new BoardingPassGenerator();
    private Set<String> checkedIn = new HashSet<>();

    // Checks everything before completing check-in
    String completeCheckIn(String bookingRef, double bagWeightKg) {
        if (!tickets.verifyTicket(bookingRef)) {
            return "Check-in failed: Invalid booking";
        }

        if (checkedIn.contains(bookingRef)) {
            return "Check-in failed: Already checked in";
        }

        if (!baggage.checkWeight(bagWeightKg)) {
            return "Check-in failed: Baggage is over 23 kg or invalid";
        }

        String seat = seats.assignSeat();

        if (seat == null) {
            return "Check-in failed: No available seats";
        }

        String bagTag = baggage.tagBag(bookingRef, bagWeightKg);
        checkedIn.add(bookingRef);

        return passes.generatePass(bookingRef, seat, bagTag);
    }
}

public class Main {
    public static void main(String[] args) {
        AirportCheckInFacade airport = new AirportCheckInFacade();

        // Valid passenger
        System.out.println(airport.completeCheckIn("AB123", 15.0));

        System.out.println();

        // Invalid ticket
        System.out.println(airport.completeCheckIn("XX000", 10.0));

        System.out.println();

        // Baggage too heavy
        System.out.println(airport.completeCheckIn("CD456", 25.0));

        System.out.println();

        // Valid passenger
        System.out.println(airport.completeCheckIn("CD456", 20.0));
    }
}
