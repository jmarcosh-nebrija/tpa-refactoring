## Nueva funcionalidad 07:

El resultado de todos estos cambios se puede ver claramente a la hora de añadir el método htmlStatement(). Ahora 
estoy en el punto en el que me quito el sombrero de refactorizador y me pongo el sombrero de agregar funciones. 
Puedo escribir htmlStatement() de la siguiente manera y agregar las pruebas apropiadas:


### Resultado

Se añade el método htmlStatement() a la clase Customer:

**Nota**: No se indican los cambios en **negrita** ya que la mezcla al incluir codigo HTML la página 
muestra de forma incorrecta el texto.

```
public class Customer {
    ...

    public String htmlStatement() {
        Enumeration rentals = _rentals.elements();
        String result = "<H1>Rentals for <EM>" + getName() + "</EM></H1><P>\n";
        while (rentals.hasMoreElements()) {
            Rental each = (Rental) rentals.nextElement();
            //show figures for each rental
            result += each.getMovie().getTitle() + ": " + String.valueOf(each.getCharge()) + "<BR>\n";
        }
        //add footer lines
        result += "<P>You owe <EM>" + String.valueOf(getTotalCharge()) + "</EM><P>\n";

        result += "On this rental you earned <EM>" +
                String.valueOf(getTotalFrequentRenterPoints()) +
                "</EM> frequent renter points<P>";
        return result;
    }
    ...
}
```

### Pruebas

Se añaden a la clase Customer tests las siguientes pruebas:


```
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
```
