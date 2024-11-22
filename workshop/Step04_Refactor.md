## Refactor 04: Variable thisAmount es redundante...

La variable thisAmount es el resultado de each.getCharge() y no se modifica en ningún caso.
Se puede aplicar fácilmente el refactor **Replace Temp with Query** (Reemplazar temporal con consulta) que 
permite cambiar la variable por la llamada al código directamente.

### Se pide

Usar Replace Temp with Query (Reemplazar temporal con consulta) con la variable thisAmount.

<pre>
    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        Enumeration rentals = _rentals.elements();
        String result = "Rental Record for " + getName() + "\n";
        while (rentals.hasMoreElements()) {
            double thisAmount = 0;
            Rental each = (Rental) rentals.nextElement();
            <b>thisAmount = each.getCharge();</b>

            // add frequent renter points
            frequentRenterPoints++;

            // add bonus for a two day new release rental
            if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) && each.getDaysRented() > 1) 
                frequentRenterPoints++;

            //show figures for this rental
            result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(<b>thisAmount</b>) + "\n";
            totalAmount += <b>thisAmount</b>;
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
        Rental each = (Rental) rentals.nextElement();

        // add frequent renter points
        frequentRenterPoints++;

        // add bonus for a two day new release rental
        if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) && each.getDaysRented() > 1) 
            frequentRenterPoints++;

        //show figures for this rental
        result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(<b>each.getCharge()</b>) + "\n";
        totalAmount += <b>each.getCharge()</b>;
    }

    //add footer lines
    result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
    result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";
    return result;
}
</pre>

Puede estar bien deshacerse de variables temporales como esta tanto como sea posible. Las variables 
temporales suelen ser un problema porque hacen que se pasen muchos parámetros cuando no es necesario. 
Es fácil perder de vista para qué están ahí. Son particularmente insidiosas en métodos largos. 
Por supuesto, hay un precio de rendimiento que pagar; aquí el cargo ahora se calcula dos veces. 
Pero es fácil optimizar eso en la clase de alquiler, y se puede optimizar de manera mucho más efectiva 
cuando el código está factorizado correctamente.

### Pruebas

No es necesario cambiar las pruebas, pero nos viene genial ejecutarlas!