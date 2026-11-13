
package task10;

import java.util.*;

//I used ChatGPT to help writ this code

class RoomAvailability {
    private Map<String, List<Integer>> rooms = new HashMap<>();
    private Map<String, Double> prices = new HashMap<>();
    private Set<Integer> occupied = new HashSet<>();

    RoomAvailability() {
        rooms.put("SINGLE", Arrays.asList(101, 102));
        rooms.put("DOUBLE", Arrays.asList(201, 202));
        rooms.put("SUITE", Arrays.asList(301));

        prices.put("SINGLE", 100.0);
        prices.put("DOUBLE", 150.0);
        prices.put("SUITE", 250.0);
    }

    int findRoom(String type) {
        List<Integer> list = rooms.get(type);

        if (list == null) {
            return -1;
        }

        for (int room : list) {
            if (!occupied.contains(room)) {
                return room;
            }
        }

        return -1;
    }

    double getPrice(String type) {
        return prices.getOrDefault(type, 0.0);
    }

    void bookRoom(int room) {
        occupied.add(room);
    }

    boolean releaseRoom(int room) {
        return occupied.remove(room);
    }
}

// Calculates the guests bill
class BillingService {
    void charge(String name, double amount) {
        System.out.println(name + " paid $" + amount);
    }
}

// Create room key card
class KeyCardEncoder {
    void createCard(int room) {
        System.out.println("Key card created for room " + room);
    }
}

// Notifies housekeeping after check-out
class HousekeepingNotifier {
    void notifyCleaning(int room) {
        System.out.println("Room " + room + " needs cleaning");
    }
}

class HotelReceptionFacade {
    private RoomAvailability rooms;
    private BillingService billing;
    private KeyCardEncoder cards;
    private HousekeepingNotifier cleaning;

    HotelReceptionFacade(RoomAvailability rooms,
                         BillingService billing,
                         KeyCardEncoder cards,
                         HousekeepingNotifier cleaning) {
        this.rooms = rooms;
        this.billing = billing;
        this.cards = cards;
        this.cleaning = cleaning;
    }

    // Checks availability and completes guest check-in
    int checkInGuest(String name, String type, int nights) {
        System.out.println("Checking in: " + name);

        if (nights <= 0) {
            System.out.println("Invalid number of nights");
            return -1;
        }

        int room = rooms.findRoom(type);

        if (room == -1) {
            System.out.println("No available room");
            return -1;
        }

        double total = rooms.getPrice(type) * nights;

        rooms.bookRoom(room);
        billing.charge(name, total);
        cards.createCard(room);

        System.out.println("Room assigned: " + room);
        return room;
    }

    // Releases the room and informs housekeeping
    void checkOutGuest(int room) {
        if (rooms.releaseRoom(room)) {
            System.out.println("Checked out from room " + room);
            cleaning.notifyCleaning(room);
        } else {
            System.out.println("Room is not occupied");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        HotelReceptionFacade hotel = new HotelReceptionFacade(
            new RoomAvailability(),
            new BillingService(),
            new KeyCardEncoder(),
            new HousekeepingNotifier()
        );

        // First guest
        int room1 = hotel.checkInGuest("Ali", "SINGLE", 2);

        System.out.println();

        // Second guest
        hotel.checkInGuest("Dana", "DOUBLE", 3);

        System.out.println();

        // Guest checks out
        hotel.checkOutGuest(room1);

        System.out.println();

        // New guest gets the available room
        hotel.checkInGuest("John", "SINGLE", 1);
    }
}
