
package task01;

class Amplifier {
    void on() {
        System.out.println("Amplifier ON");
    }

    void setSurroundSound() {
        System.out.println("Surround sound ON");
    }

    void setVolume(int volume) {
        System.out.println("Volume: " + volume);
    }

    void off() {
        System.out.println("Amplifier OFF");
    }
}


class Projector {
    void on() {
        System.out.println("Projector ON");
    }

    void wideScreenMode() {
        System.out.println("Widescreen mode ON");
    }

    void off() {
        System.out.println("Projector OFF");
    }
}

// Subsystem 3
class StreamingPlayer {
    void on() {
        System.out.println("Player ON");
    }

    void play(String movie) {
        System.out.println("Playing: " + movie);
    }

    void stop() {
        System.out.println("Movie stopped");
    }

    void off() {
        System.out.println("Player OFF");
    }
}

// Subsystem 4
class TheaterLights {
    void dim(int level) {
        System.out.println("Lights dimmed to " + level + "%");
    }

    void on() {
        System.out.println("Lights ON");
    }
}

// Facade controls all subsystems
class HomeTheaterFacade {
    private final Amplifier amplifier;
    private final Projector projector;
    private final StreamingPlayer player;
    private final TheaterLights lights;

    HomeTheaterFacade(Amplifier amplifier, Projector projector,
                      StreamingPlayer player, TheaterLights lights) {
        this.amplifier = amplifier;
        this.projector = projector;
        this.player = player;
        this.lights = lights;
    }

    void watchMovie(String movie) {
        System.out.println("Starting Movie");
        lights.dim(15);
        projector.on();
        projector.wideScreenMode();
        amplifier.on();
        amplifier.setSurroundSound();
        amplifier.setVolume(65);
        player.on();
        player.play(movie);
    }

    void endMovie() {
        System.out.println("Ending Movie");
        player.stop();
        player.off();
        amplifier.off();
        projector.off();
        lights.on();
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        HomeTheaterFacade theater = new HomeTheaterFacade(
            new Amplifier(),
            new Projector(),
            new StreamingPlayer(),
            new TheaterLights()
        );

        theater.watchMovie("Avatar");
        theater.endMovie();

        System.out.println();

        theater.watchMovie("Titanic");
        theater.endMovie();
    }
}

