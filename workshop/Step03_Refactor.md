## Refactor 03: Mover el cálculo de la cantidad (método amountFor())

Cuando se observa el método amountFor(), se puede ver que utiliza información del alquiler, 
pero no utiliza información del cliente. 

Esto inmediatamente hace que se sospeche que el método está en el objeto equivocado. 
En la mayoría de los casos, un método debería estar en el objeto cuyos datos utiliza, 
por lo que el método debería trasladarse al alquiler (Rental). Para ello, utilizo el refactor 
**Move Method** (en Intellij IDEA se llama "Move Instance Method"). Con esto, primero se copia el código 
en el alquiler y se ajusta para que encaje en su nuevo hogar.

Además, aplicamos otro refactor **Rename** para **cambiar el nombre del método de amountFor() a getCharge()**.

### Resultado

El método desaparece de la clase Customer: 

<pre>
public class Customer {
    private String _name;
    private Vector _rentals = new Vector();

    public Customer(String name) {
        _name = name;
    }

    public void addRental(Rental arg) {
        _rentals.addElement(arg);
    }

    public String getName() {
        return _name;
    }

    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        Enumeration rentals = _rentals.elements();
        String result = "Rental Record for " + getName() + "\n";
        while (rentals.hasMoreElements()) {
            double thisAmount = 0;
            Rental each = (Rental) rentals.nextElement();
            thisAmount = <b>each.getCharge();</b>

            // add frequent renter points
            frequentRenterPoints++;

            // add bonus for a two day new release rental
            if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) && each.getDaysRented() > 1) 
                frequentRenterPoints++;

            //show figures for this rental
            result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(thisAmount) + "\n";
            totalAmount += thisAmount;
        }

        //add footer lines
        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";
        return result;
    }
}
</pre>

La clase Rental es dónde aparece el método getCharge():
<pre>
public class Rental {
    private Movie _movie;
    private int _daysRented;

    public Rental(Movie movie, int daysRented) {
        _movie = movie;
        _daysRented = daysRented;
    }

    public int getDaysRented() {
        return _daysRented;
    }

    public Movie getMovie() {
        return _movie;
    }

    <b>public double getCharge() {
        double result = 0;

        //determine amounts for each line
        switch (getMovie().getPriceCode()) {
            case Movie.REGULAR:
                result += 2;
                if (getDaysRented() > 2)
                    result += (getDaysRented() - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                result += getDaysRented() * 3;
                break;
            case Movie.CHILDRENS:
                result += 1.5;
                if (getDaysRented() > 3)
                    result += (getDaysRented() - 3) * 1.5;
                break;
        }
        return result;
    }</b>
}
</pre>

### Pruebas

Se eliminan las pruebas de CustomerTestCase testAmountFor*.

Se añaden las siguientes pruebas a la clase RentalTest:

<pre>
<b>
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
</b>
</pre>