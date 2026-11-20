package week12_practice.FacadeCodingQuestions.task01;

public class HomeTheaterFacade {

    private Amplifier amplifier;
    private Projector projector;
    private StreamingPlayer player;
    private TheaterLights lights;

    public HomeTheaterFacade() {
        amplifier = new Amplifier();
        projector = new Projector();
        player = new StreamingPlayer();
        lights = new TheaterLights();
    }

    public void watchMovie(String movie) {
        System.out.println("Starting home theater...");

        lights.dimLights();

        projector.turnOn();
        projector.setWideScreenMode();

        amplifier.turnOn();
        amplifier.setVolume(20);

        player.turnOn();
        player.play(movie);
    }

    public void endMovie() {
        System.out.println("Shutting down home theater...");

        player.stop();
        player.turnOff();

        amplifier.turnOff();
        projector.turnOff();

        lights.turnOn();
    }
}