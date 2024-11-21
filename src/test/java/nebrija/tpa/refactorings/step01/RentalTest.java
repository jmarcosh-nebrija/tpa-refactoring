package nebrija.tpa.refactorings.step01;


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
}
