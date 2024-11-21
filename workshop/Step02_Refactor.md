## Refactor 02: Renombrar variables

### Se pide 

Se pide hacer un Rename del nombre del argumento en amountFor():

<pre>
double amountFor(Rental <b>each</b>) {
    double thisAmount = 0;

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

### Resultado

<pre>
double amountFor(Rental <b>aRental</b>) {
    double <b>result</b> = 0;

    //determine amounts for each line
    switch (<b>aRental</b>.getMovie().getPriceCode()) {
        case Movie.REGULAR:
            <b>result</b> += 2;
            if (<b>aRental</b>.getDaysRented() > 2)
                <b>result</b> += (<b>aRental</b>.getDaysRented() - 2) * 1.5;
            break;
            break;
        case Movie.NEW_RELEASE:
            <b>result</b> += <b>aRental</b>.getDaysRented() * 3;
            break;
        case Movie.CHILDRENS:
            <b>result</b> += 1.5;
            if (<b>aRental</b>.getDaysRented() > 3)
                <b>result</b> += (<b>aRental</b>.getDaysRented() - 3) * 1.5;
            break;
    }
    return <b>result</b>;
}
</pre>

### Pruebas

No hay necesidad de hacer cambios en las pruebas, pero si hay que ejecutarlas!

### Tip

> Any fool can write code that a computer can understand. Good programmers write code that humans can understand.