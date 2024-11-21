package nebrija.tpa.video_store;


import java.util.ArrayList;
import java.util.List;

/**
 * Ejemplo de prueba manual con inspección visual: justo lo que no se debe de hacer.
 */
public class MainApp {

    public static void main(String[] args) {
        List<Movie> movies = MainApp.findAllMovies();
        Movie theMatrix = MainApp.findMovieByTitle(movies, "The Matrix");
        Rental rental = new Rental(theMatrix, 2);

        Customer customer = new Customer("John Wayme");
        customer.addRental(rental);
        String statement = customer.statement();
        System.out.println(statement);
    }

    private static List<Movie> findAllMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", Movie.REGULAR));
        movies.add(new Movie("Frozen", Movie.CHILDRENS));
        movies.add(new Movie("Avengers: Endgame", Movie.NEW_RELEASE));

        return movies;
    }

    private static Movie findMovieByTitle(List<Movie> movies, String title) {
        for (Movie movie : movies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return movie;
            }
        }
        return null; // Si no se encuentra, devuelve null
    }
}
