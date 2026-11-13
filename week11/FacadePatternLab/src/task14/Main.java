
package task14;

//I used ChatGPT to help write this code

// Creates new gym member profiles
class ProfileRegistry {
    private int nextId = 1001;

    String createProfile(String name) {
        String memberId = "M" + nextId;
        nextId++;

        System.out.println("Profile created for " + name);
        return memberId;
    }
}

// Checks age and medical clearance
class HealthScreening {
    boolean checkHealth(int age, boolean medicalCleared) {
        if (age < 16) {
            System.out.println("Member must be at least 16 years old");
            return false;
        }

        if (!medicalCleared) {
            System.out.println("Medical clearance required");
            return false;
        }

        return true;
    }
}

// Assigns a locker to each member
class LockerAssigner {
    private int nextLocker = 101;

    int assignLocker() {
        int locker = nextLocker;
        nextLocker++;

        System.out.println("Locker assigned: " + locker);
        return locker;
    }
}

// Issues a gym access card
class AccessCardIssuer {
    String issueCard(String memberId) {
        String card = "CARD-" + memberId;

        System.out.println("Access card issued: " + card);
        return card;
    }
}

class GymOnboardingFacade {
    private ProfileRegistry profiles;
    private HealthScreening health;
    private LockerAssigner lockers;
    private AccessCardIssuer cards;

    GymOnboardingFacade(ProfileRegistry profiles,
                        HealthScreening health,
                        LockerAssigner lockers,
                        AccessCardIssuer cards) {
        this.profiles = profiles;
        this.health = health;
        this.lockers = lockers;
        this.cards = cards;
    }

    // Registers a member only after validation
    String registerNewMember(String name, int age, boolean medicalCleared) {
        System.out.println("Registering: " + name);

        if (name == null || name.isBlank()) {
            return "Registration failed: Invalid name";
        }

        if (!health.checkHealth(age, medicalCleared)) {
            return "Registration failed";
        }

        String memberId = profiles.createProfile(name);
        int locker = lockers.assignLocker();
        String card = cards.issueCard(memberId);

        return "Welcome to the gym, " + name + "!\n"
                + "Member ID: " + memberId + "\n"
                + "Locker: " + locker + "\n"
                + "Access card: " + card;
    }
}

public class Main {
    public static void main(String[] args) {
        GymOnboardingFacade gym = new GymOnboardingFacade(
            new ProfileRegistry(),
            new HealthScreening(),
            new LockerAssigner(),
            new AccessCardIssuer()
        );

        // Successful registration
        System.out.println(gym.registerNewMember("Ali", 20, true));

        System.out.println();

        // Member is too young
        System.out.println(gym.registerNewMember("Dana", 15, true));

        System.out.println();

        // No medical clearance
        System.out.println(gym.registerNewMember("John", 19, false));

        System.out.println();

        // Another successful registration
        System.out.println(gym.registerNewMember("Sara", 22, true));
    }
}
