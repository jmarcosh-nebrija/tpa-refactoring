## Refactor 05: Extracción de puntos de fidelización

El siguiente paso es hacer algo similar a lo hecho hasta ahora cambios anteriores pero para los puntos de fidelidad. 
Las reglas varían con la pelicula, aunque hay menos variación que con el cobro. Parece razonable poner la 
responsabilidad en el arrendatario. 
Primero debemos usar el método de extracción en la parte del código de puntos de arrendatario frecuente (en negrita):

### Se pide

Aplicar **Extract Method** al código que calcula los puntos de fidelización y después moverlo a la clase Rental. 
Se requiere hacer ajustes manuales. 

<pre>
    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        Enumeration rentals = _rentals.elements();
        String result = "Rental Record for " + getName() + "\n";
        while (rentals.hasMoreElements()) {
            Rental each = (Rental) rentals.nextElement();

            <b>// add frequent renter points
            frequentRenterPoints++;

            // add bonus for a two day new release rental
            if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE)
                    && each.getDaysRented() > 1) frequentRenterPoints++;
            </b>

            //show figures for this rental
            result += "\t" + each.getMovie().getTitle() + "\t" +
                    String.valueOf(each.getCharge()) + "\n";
            totalAmount += each.getCharge();
        }

        //add footer lines
        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";
        return result;
    }
</pre>

### Resultado

La clase Customer:

<pre>
public class Customer {
...
    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        Enumeration rentals = _rentals.elements();
        String result = "Rental Record for " + getName() + "\n";
        while (rentals.hasMoreElements()) {
            Rental each = (Rental) rentals.nextElement();

            <b>frequentRenterPoints += each.getFrequentRenterPoints();</b>

            //show figures for this rental
            result += "\t" + each.getMovie().getTitle() + "\t" +
                    String.valueOf(each.getCharge()) + "\n";
            totalAmount += each.getCharge();
        }

        //add footer lines
        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";
        return result;
    }
}
</pre>


La clase Rental:

<pre>
public class Rental {
...
    <b>
    int getFrequentRenterPoints() {
        // add bonus for a two day new release rental
        if ((getMovie().getPriceCode() == Movie.NEW_RELEASE) && getDaysRented() > 1)
            return 2;
        else
            return 1;
    }</b>
}
</pre>

### Pruebas

Añadimos las siguientes pruebas a la clase RentalTest:

<pre>
<b>
    @Test
    void testFrequentRenterPointsForRegularMovie() {
        Movie regularMovie = new Movie("The Matrix", Movie.REGULAR);
        Rental rental = new Rental(regularMovie, 3); // 3 días de alquiler

        int points = rental.getFrequentRenterPoints();

        assertEquals(1, points, "Puntos de alquiler incorrectos para película regular.");
    }

    @Test
    void testFrequentRenterPointsForChildrensMovie() {
        Movie childrensMovie = new Movie("Frozen", Movie.CHILDRENS);
        Rental rental = new Rental(childrensMovie, 4); // 4 días de alquiler

        int points = rental.getFrequentRenterPoints();

        assertEquals(1, points, "Puntos de alquiler incorrectos para película infantil.");
    }

    @Test
    void testFrequentRenterPointsForNewReleaseUnderTwoDays() {
        Movie newReleaseMovie = new Movie("Avatar 2", Movie.NEW_RELEASE);
        Rental rental = new Rental(newReleaseMovie, 1); // 1 día de alquiler

        int points = rental.getFrequentRenterPoints();

        assertEquals(1, points, "Puntos de alquiler incorrectos para nuevo lanzamiento con 1 día de alquiler.");
    }

    @Test
    void testFrequentRenterPointsForNewReleaseOverTwoDays() {
        Movie newReleaseMovie = new Movie("Avatar 2", Movie.NEW_RELEASE);
        Rental rental = new Rental(newReleaseMovie, 3); // 3 días de alquiler

        int points = rental.getFrequentRenterPoints();

        assertEquals(2, points, "Puntos de alquiler incorrectos para nuevo lanzamiento con más de 2 días de alquiler.");
    }
</b>
</pre>
