package week12_practice.FacadeCodingQuestions.task01;

public class StreamingPlayer {

    public void turnOn() {
        System.out.println("Streaming Player is ON");
    }

    public void play(String movie) {
        System.out.println("Now playing: " + movie);
    }

    public void stop() {
        System.out.println("Movie stopped");
    }

    public void turnOff() {
        System.out.println("Streaming Player is OFF");
    }
}
