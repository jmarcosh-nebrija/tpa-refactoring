package nebrija.tpa.refactorings.step03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
