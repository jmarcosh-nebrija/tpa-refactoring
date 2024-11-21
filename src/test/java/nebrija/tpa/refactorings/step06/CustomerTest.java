package nebrija.tpa.refactorings.step06;

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

    @Test
    void testGetTotalChargeWithMultipleRentals() {
        // Preparación
        Customer customer = new Customer("John Doe");
        Movie regularMovie = new Movie("The Matrix", Movie.REGULAR);
        Movie newReleaseMovie = new Movie("Avatar 2", Movie.NEW_RELEASE);
        Movie childrensMovie = new Movie("Frozen", Movie.CHILDRENS);

        customer.addRental(new Rental(regularMovie, 3));  // Regular: 3 días -> 3.5
        customer.addRental(new Rental(newReleaseMovie, 2));  // New Release: 2 días -> 6.0
        customer.addRental(new Rental(childrensMovie, 4));  // Children's: 4 días -> 3.0

        // Total esperado
        double expectedTotalCharge = 3.5 + 6.0 + 3.0;

        // Acción
        String statement = customer.statement();
        double totalChargeExtracted = Double.parseDouble(statement.split("Amount owed is ")[1].split("\n")[0]);

        // Verificación
        assertEquals(expectedTotalCharge, totalChargeExtracted, 0.01, "El total de cargos calculado es incorrecto.");
    }

    @Test
    void testGetTotalFrequentRenterPointsWithMultipleRentals() {
        // Preparación
        Customer customer = new Customer("Jane Doe");
        Movie regularMovie = new Movie("The Matrix", Movie.REGULAR);
        Movie newReleaseMovie = new Movie("Avatar 2", Movie.NEW_RELEASE);
        Movie childrensMovie = new Movie("Frozen", Movie.CHILDRENS);

        customer.addRental(new Rental(regularMovie, 3));  // Regular: 1 punto
        customer.addRental(new Rental(newReleaseMovie, 2));  // New Release: 2 días -> 2 puntos
        customer.addRental(new Rental(childrensMovie, 4));  // Children's: 1 punto

        // Total esperado
        int expectedTotalPoints = 1 + 2 + 1;

        // Acción
        String statement = customer.statement();
        int totalPointsExtracted = Integer.parseInt(statement.split("You earned ")[1].split(" frequent renter points")[0]);

        // Verificación
        assertEquals(expectedTotalPoints, totalPointsExtracted, "El total de puntos calculado es incorrecto.");
    }
}
