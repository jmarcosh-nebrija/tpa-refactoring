## Refactor 06: Eliminamos más variables temporales

Como se sugirió antes, las variables temporales pueden ser un problema. Son útiles solo dentro de su propia rutina y, 
por lo tanto, fomentan rutinas largas y complejas. En este caso, tenemos dos variables temporales, **totalAmount** y 
**frequencyRentalPoints**, y ambas se utilizan para obtener un total de los alquileres asociados al cliente. 
Hay que aplicar **Replace Temp with Query** para las dos variables, pero en estos casos no será tan sencillo como el 
anterior ya que las variables se modifican en el bucle.

Tener en cuenta que estos cambios nos vendrán bien a la hora de implementar htmlStatement() ya que tanto la 
versión ASCII como la HTML requieren estos totales.

En resumen, vamos a aplicar **Replace Temp with Query** (Reemplazar temporal con consulta) para reemplazar 
**totalAmount** y **frequencyRentalPoints** con métodos de consulta.


### Se pide

Eliminar las variables temporales totalAmount y frequencyRentalPoints. Como estas variable se modifican dentro del 
bucle, creamos métodos para calcular las variables.

<pre>
public String statement() {
    double <b>totalAmount</b> = 0;
    int <b>frequentRenterPoints</b> = 0;
    Enumeration rentals = _rentals.elements();
    String result = "Rental Record for " + getName() + "\n";
    while (rentals.hasMoreElements()) {
        Rental each = (Rental) rentals.nextElement();

        <b>frequentRenterPoints</b> += each.getFrequentRenterPoints();

        //show figures for this rental
        result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(each.getCharge()) + "\n";
        <b>totalAmount</b> += each.getCharge();
    }

    //add footer lines
    result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
    result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";
    return result;
}
</pre>

### Resultado


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
        int frequentRenterPoints = 0;
        Enumeration rentals = _rentals.elements();
        String result = "Rental Record for " + getName() + "\n";
        while (rentals.hasMoreElements()) {
            Rental each = (Rental) rentals.nextElement();

            //show figures for this rental
            result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(each.getCharge()) + "\n";
        }

        //add footer lines
        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";
        return result;
    }

    <b>private double getTotalCharge() {
        double result = 0;
        Enumeration rentals = _rentals.elements();
        while (rentals.hasMoreElements()) {
            Rental each = (Rental) rentals.nextElement();
            result += each.getCharge();
        }
        return result;
    }

    private int getTotalFrequentRenterPoints() {
        int result = 0;
        Enumeration rentals = _rentals.elements();
        while (rentals.hasMoreElements()) {
            Rental each = (Rental) rentals.nextElement();
            result += each.getFrequentRenterPoints();
        }
        return result;
    } </b>
}
</pre>

### Pruebas



### Toca pensar... 

Vale la pena detenerse a pensar un poco en la última refactorización. **La mayoría de las refactorizaciones reducen 
la cantidad de código, pero esta la aumenta.** Esto se debe a que Java requiere muchas sentencias para configurar un 
bucle sumador. Incluso un bucle sumador simple con una línea de código por elemento necesita seis líneas de soporte a 
su alrededor. Posiblemente en las nuevas versiones de Java el numero de líneas se puede reducir, pero que de todas 
formas son más líneas.

La otra preocupación con esta refactorización radica en el rendimiento. **El código antiguo ejecutaba el bucle 
"while" una vez, el nuevo código lo ejecuta tres veces.** Un bucle while que tarda mucho tiempo puede perjudicar 
el rendimiento. Muchos programadores no harían esta refactorización simplemente por esta razón. No se preocupe por 
el rendimiento mientras realiza la refactorización. Cuando optimice, tendrá que preocuparse por 
ello, pero estará en una posición mucho mejor para hacer algo al respecto y tendrá más opciones para optimizar de 
manera efectiva.

**Lo bueno de este cambio es que estas consultas ahora están disponibles para cualquier código escrito en la clase 
de cliente.** Se pueden agregar fácilmente a la interfaz de la clase si otras partes del sistema necesitan esta 
información. **Sin consultas como estas, otros métodos tienen que lidiar con conocer los alquileres y crear ellos 
mismos los bucles.** En un sistema complejo, eso generará mucho más código para escribir y mantener.