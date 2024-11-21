package nebrija.tpa.refactorings.step07;


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
    void testHtmlStatement_NoRentals() {
        Customer customer = new Customer("John Doe");

        String expected = "<H1>Rentals for <EM>John Doe</EM></H1><P>\n" +
                "<P>You owe <EM>0.0</EM><P>\n" +
                "On this rental you earned <EM>0</EM> frequent renter points<P>";

        assertEquals(expected, customer.htmlStatement());
    }

    @Test
    void testHtmlStatement_OneRental() {
        Customer customer = new Customer("Jane Doe");
        Movie movie = new Movie("The Matrix", Movie.REGULAR);
        Rental rental = new Rental(movie, 3);
        customer.addRental(rental);

        String expected = "<H1>Rentals for <EM>Jane Doe</EM></H1><P>\n" +
                "The Matrix: 3.5<BR>\n" +
                "<P>You owe <EM>3.5</EM><P>\n" +
                "On this rental you earned <EM>1</EM> frequent renter points<P>";

        assertEquals(expected, customer.htmlStatement());
    }

    @Test
    void testHtmlStatement_MultipleRentals() {
        Customer customer = new Customer("Alice");
        Movie movie1 = new Movie("Frozen", Movie.CHILDRENS);
        Rental rental1 = new Rental(movie1, 4);

        Movie movie2 = new Movie("The Godfather", Movie.REGULAR);
        Rental rental2 = new Rental(movie2, 2);

        Movie movie3 = new Movie("Avengers: Endgame", Movie.NEW_RELEASE);
        Rental rental3 = new Rental(movie3, 1);

        customer.addRental(rental1);
        customer.addRental(rental2);
        customer.addRental(rental3);

        String expected = "<H1>Rentals for <EM>Alice</EM></H1><P>\n" +
                "Frozen: 3.0<BR>\n" +
                "The Godfather: 2.0<BR>\n" +
                "Avengers: Endgame: 3.0<BR>\n" +
                "<P>You owe <EM>8.0</EM><P>\n" +
                "On this rental you earned <EM>3</EM> frequent renter points<P>";

        assertEquals(expected, customer.htmlStatement());
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
