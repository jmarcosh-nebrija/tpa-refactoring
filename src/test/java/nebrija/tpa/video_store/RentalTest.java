package nebrija.tpa.video_store;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RentalTest {

    @Test
    void testRentalInitialization() {
        Movie movie = new Movie("The Matrix", Movie.REGULAR);
        Rental rental = new Rental(movie, 5);

        assertEquals(movie, rental.getMovie(), "La película asociada al alquiler no coincide.");
        assertEquals(5, rental.getDaysRented(), "El número de días de alquiler no coincide.");
    }
}
