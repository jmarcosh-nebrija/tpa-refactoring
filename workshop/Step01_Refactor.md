## Refactor 01: Extraer met3odo amountFor()

### Se pide 

Aplicar Extract Method al código en negrita:

<pre>
public String statement() {
    double totalAmount = 0;
    int frequentRenterPoints = 0;
    Enumeration rentals = _rentals.elements();
    String result = "Rental Record for " + getName() + "\n";
    while (rentals.hasMoreElements()) {
        double thisAmount = 0;
        Rental each = (Rental) rentals.nextElement();
        <b>
        //determine amounts for each line
        switch (each.getMovie().getPriceCode()) {
            case Movie.REGULAR:
                thisAmount += 2;
                if (each.getDaysRented() > 2)
                    thisAmount += (each.getDaysRented() - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                thisAmount += each.getDaysRented() * 3;
                break;
            case Movie.CHILDRENS:
                thisAmount += 1.5;
                if (each.getDaysRented() > 3)
                    thisAmount += (each.getDaysRented() - 3) * 1.5;
                break;
        }
        </b>
        // add frequent renter points
        frequentRenterPoints++;

        // add bonus for a two day new release rental
        if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE)
                && each.getDaysRented() > 1) frequentRenterPoints++;

        //show figures for this rental
        result += "\t" + each.getMovie().getTitle() + "\t" +
                String.valueOf(thisAmount) + "\n";
        totalAmount += thisAmount;
    }

    //add footer lines
    result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
    result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";
    return result;
}
</pre>

### Resultado

<pre>
public String statement() {
    double totalAmount = 0;
    int frequentRenterPoints = 0;
    Enumeration rentals = _rentals.elements();
    String result = "Rental Record for " + getName() + "\n";
    while (rentals.hasMoreElements()) {
        double thisAmount = 0;
        Rental each = (Rental) rentals.nextElement();
        thisAmount = <b>amountFor(each)</b>;

        // add frequent renter points
        frequentRenterPoints++;

        // add bonus for a two day new release rental
        if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE)
                && each.getDaysRented() > 1) frequentRenterPoints++;

        //show figures for this rental
        result += "\t" + each.getMovie().getTitle() + "\t" +
                String.valueOf(thisAmount) + "\n";
        totalAmount += thisAmount;
    }

    //add footer lines
    result += "Amount owed is " + String.valueOf(totalAmount) +
            "\n";
    result += "You earned " + String.valueOf(frequentRenterPoints)
            +
            " frequent renter points";
    return result;
}

double amountFor(Rental each) {
    <b>double thisAmount = 0;</b>

    //determine amounts for each line
    switch (each.getMovie().getPriceCode()) {
        case Movie.REGULAR:
            thisAmount += 2;
            if (each.getDaysRented() > 2)
                thisAmount += (each.getDaysRented() - 2) * 1.5;
            break;
        case Movie.NEW_RELEASE:
            thisAmount += each.getDaysRented() * 3;
            break;
        case Movie.CHILDRENS:
            thisAmount += 1.5;
            if (each.getDaysRented() > 3)
                thisAmount += (each.getDaysRented() - 3) * 1.5;
            break;
    }
    return thisAmount;
}
</pre>

### Pruebas

Se añaden las siguiente pruebas a la clase CustomerTest:

<pre>
<b>
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
</b>
</pre>



