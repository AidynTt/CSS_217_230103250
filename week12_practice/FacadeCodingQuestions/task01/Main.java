package week12_practice.FacadeCodingQuestions.task01;

public class Main {

    public static void main(String[] args) {

        HomeTheaterFacade homeTheater = new HomeTheaterFacade();

        homeTheater.watchMovie("Interstellar");

        System.out.println();

        homeTheater.endMovie();
    }
}