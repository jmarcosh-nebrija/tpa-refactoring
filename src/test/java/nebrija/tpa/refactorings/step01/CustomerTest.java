package nebrija.tpa.refactorings.step01;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void testStatementForRegularMovie() {
        Movie regularMovie = new Movie("The Godfather", Movie.REGULAR);
        Rental rental = new Rental(regularMovie, 3);
        Customer customer = new Customer("John Doe");
        customer.addRental(rental);

        String expectedStatement = "Rental Record for John Doe\n" +
                "\tThe Godfather\t3.5\n" +
                "Amount owed is 3.5\n" +
                "You earned 1 frequent renter points";

        assertEquals(expectedStatement, customer.statement());
    }

    @Test
    void testStatementForNewReleaseMovie() {
        Movie newReleaseMovie = new Movie("Avengers: Endgame", Movie.NEW_RELEASE);
        Rental rental = new Rental(newReleaseMovie, 2);
        Customer customer = new Customer("Jane Smith");
        customer.addRental(rental);

        String expectedStatement = "Rental Record for Jane Smith\n" +
                "\tAvengers: Endgame\t6.0\n" +
                "Amount owed is 6.0\n" +
                "You earned 2 frequent renter points";

        assertEquals(expectedStatement, customer.statement());
    }

    @Test
    void testStatementForChildrenMovie() {
        Movie childrenMovie = new Movie("Frozen", Movie.CHILDRENS);
        Rental rental = new Rental(childrenMovie, 4);
        Customer customer = new Customer("Alice");
        customer.addRental(rental);

        String expectedStatement = "Rental Record for Alice\n" +
                "\tFrozen\t3.0\n" +
                "Amount owed is 3.0\n" +
                "You earned 1 frequent renter points";

        assertEquals(expectedStatement, customer.statement());
    }

    @Test
    void testStatementWithMultipleRentals() {
        Movie regularMovie = new Movie("The Godfather", Movie.REGULAR);
        Movie newReleaseMovie = new Movie("Avengers: Endgame", Movie.NEW_RELEASE);
        Movie childrenMovie = new Movie("Frozen", Movie.CHILDRENS);

        Rental rental1 = new Rental(regularMovie, 3);
        Rental rental2 = new Rental(newReleaseMovie, 1);
        Rental rental3 = new Rental(childrenMovie, 5);

        Customer customer = new Customer("Chris");
        customer.addRental(rental1);
        customer.addRental(rental2);
        customer.addRental(rental3);

        String expectedStatement = "Rental Record for Chris\n" +
                "\tThe Godfather\t3.5\n" +
                "\tAvengers: Endgame\t3.0\n" +
                "\tFrozen\t4.5\n" +
                "Amount owed is 11.0\n" +
                "You earned 3 frequent renter points";

        assertEquals(expectedStatement, customer.statement());
    }

    @Test
    void testAmountForRegularMovie() {
        Movie regularMovie = new Movie("Regular Movie", Movie.REGULAR);
        Rental rental = new Rental(regularMovie, 3); // 3 días de alquiler

        Customer customer = new Customer("Test Customer");
        double amount = customer.amountFor(rental);

        assertEquals(3.5, amount, 0.01, "Monto incorrecto para película regular con más de 2 días de alquiler");
    }

    @Test
    void testAmountForNewReleaseMovie() {
        Movie newRelease = new Movie("New Release", Movie.NEW_RELEASE);
        Rental rental = new Rental(newRelease, 2); // 2 días de alquiler

        Customer customer = new Customer("Test Customer");
        double amount = customer.amountFor(rental);

        assertEquals(6.0, amount, 0.01, "Monto incorrecto para un nuevo lanzamiento");
    }

    @Test
    void testAmountForChildrensMovieUnderThreeDays() {
        Movie childrensMovie = new Movie("Children's Movie", Movie.CHILDRENS);
        Rental rental = new Rental(childrensMovie, 2); // 2 días de alquiler

        Customer customer = new Customer("Test Customer");
        double amount = customer.amountFor(rental);

        assertEquals(1.5, amount, 0.01, "Monto incorrecto para película infantil con menos de 3 días de alquiler");
    }

    @Test
    void testAmountForChildrensMovieOverThreeDays() {
        Movie childrensMovie = new Movie("Children's Movie", Movie.CHILDRENS);
        Rental rental = new Rental(childrensMovie, 5); // 5 días de alquiler

        Customer customer = new Customer("Test Customer");
        double amount = customer.amountFor(rental);

        assertEquals(4.5, amount, 0.01, "Monto incorrecto para película infantil con más de 3 días de alquiler");
    }

    @Test
    void testAmountForRegularMovieUnderTwoDays() {
        Movie regularMovie = new Movie("Regular Movie", Movie.REGULAR);
        Rental rental = new Rental(regularMovie, 1); // 1 día de alquiler

        Customer customer = new Customer("Test Customer");
        double amount = customer.amountFor(rental);

        assertEquals(2.0, amount, 0.01, "Monto incorrecto para película regular con menos de 2 días de alquiler");
    }
}
