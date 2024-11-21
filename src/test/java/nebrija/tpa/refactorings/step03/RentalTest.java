package nebrija.tpa.refactorings.step03;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RentalTest {

    @Test
    void testRentalInitialization() {
        Movie movie = new Movie("The Matrix", Movie.REGULAR);
        Rental rental = new Rental(movie, 5);

        assertEquals(movie, rental.getMovie(), "La película asociada al alquiler no coincide.");
        assertEquals(5, rental.getDaysRented(), "El número de días de alquiler no coincide.");
    }

    @Test
    void testAmountForRegularMovie() {
        Movie regularMovie = new Movie("Regular Movie", Movie.REGULAR);
        Rental rental = new Rental(regularMovie, 3); // 3 días de alquiler

        double amount = rental.getCharge();

        assertEquals(3.5, amount, 0.01, "Monto incorrecto para película regular con más de 2 días de alquiler");
    }

    @Test
    void testAmountForNewReleaseMovie() {
        Movie newRelease = new Movie("New Release", Movie.NEW_RELEASE);
        Rental rental = new Rental(newRelease, 2); // 2 días de alquiler

        double amount = rental.getCharge();

        assertEquals(6.0, amount, 0.01, "Monto incorrecto para un nuevo lanzamiento");
    }

    @Test
    void testAmountForChildrensMovieUnderThreeDays() {
        Movie childrensMovie = new Movie("Children's Movie", Movie.CHILDRENS);
        Rental rental = new Rental(childrensMovie, 2); // 2 días de alquiler

        double amount = rental.getCharge();

        assertEquals(1.5, amount, 0.01, "Monto incorrecto para película infantil con menos de 3 días de alquiler");
    }

    @Test
    void testAmountForChildrensMovieOverThreeDays() {
        Movie childrensMovie = new Movie("Children's Movie", Movie.CHILDRENS);
        Rental rental = new Rental(childrensMovie, 5); // 5 días de alquiler

        double amount = rental.getCharge();

        assertEquals(4.5, amount, 0.01, "Monto incorrecto para película infantil con más de 3 días de alquiler");
    }

    @Test
    void testAmountForRegularMovieUnderTwoDays() {
        Movie regularMovie = new Movie("Regular Movie", Movie.REGULAR);
        Rental rental = new Rental(regularMovie, 1); // 1 día de alquiler

        double amount = rental.getCharge();

        assertEquals(2.0, amount, 0.01, "Monto incorrecto para película regular con menos de 2 días de alquiler");
    }
}
