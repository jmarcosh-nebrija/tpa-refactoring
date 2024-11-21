package nebrija.tpa.refactorings.step04;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieTest {

    @Test
    void testMovieInitialization() {
        Movie movie = new Movie("Inception", Movie.NEW_RELEASE);

        assertEquals("Inception", movie.getTitle(), "El título de la película no coincide.");
        assertEquals(Movie.NEW_RELEASE, movie.getPriceCode(), "El código de precio no coincide.");
    }

    @Test
    void testSetPriceCode() {
        Movie movie = new Movie("Frozen", Movie.CHILDRENS);

        movie.setPriceCode(Movie.REGULAR);
        assertEquals(Movie.REGULAR, movie.getPriceCode(), "El código de precio no se actualizó correctamente.");
    }
}
