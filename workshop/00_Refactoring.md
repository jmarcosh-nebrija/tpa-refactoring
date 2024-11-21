# Taller de refactorings

## Punto de partida

Las clases dentro del paquete start.store tienen el diseño que tienen. Esto no es un problema
hasta que es necesario modificar el código.

### El código cambia

Con el estado actual del código, se piden una serie de modificaciones: 
* Modificar el código para que el estado de los alquileres de un cliente (método statement()) 
se replique de tal forma que podamos tener dicho estado en formato HTML para poder incrustarlo en una pagina web.
* El cliente quiere cambiar la clasificación de las películas, pero no tiene claro cómo.
* El cliente quiere cambiar como se calculan los cargos y los puntos de fidelidad

### Problemas que nos encontramos 

En estas modificaciones nos enfrentamos a:

1. Problemas con duplicar código:
  * Cuando duplicas lógica, como copiar el método statement en htmlStatement, cualquier cambio 
en las reglas de negocio (como el cálculo de cargos o puntos de cliente frecuente) requiere modificar ambos lugares. 
Esto incrementa el riesgo de inconsistencias y errores.
2. Cambios en las reglas de negocio:
  * El texto menciona que los usuarios quieren cambiar cómo clasifican las películas y cómo se calculan 
los cargos y puntos. Estos cambios son inciertos y es probable que cambien de nuevo en el futuro. 
Esto pone de relieve la necesidad de un código flexible y fácil de modificar.
3. Impacto de código rígido:
  * Aunque el programa "funcione bien", puede ser difícil de mantener. La complejidad de localizar y 
modificar todas las áreas afectadas por los cambios aumenta conforme las reglas se vuelven más complejas.

### Refactoring 

La refactorización se presenta como la herramienta clave para abordar estos problemas. 
Consiste en reestructurar el código para hacerlo más fácil de entender y modificar, 
sin cambiar su funcionalidad. Esto elimina duplicación, mejora la consistencia y facilita la 
adaptación a futuros cambios.

Tened en cuenta que para hacer refactorings de forma adecuada hay que hacer pruebas unitarias. 
Para el código riginal se muestra una clase MainApp con pruebas que se ejecutarían de forma manual y con inspección
visual de los resultados, basicamente lo que no se debe hacer y tambien la clases *Test con las pruebas unitarias de
las clases que tenemos en store.

Los pasos de refactoring de este taller son:

* Paso 1: [Refactor #01](Step01_Refactor)
* Paso 2: [Refactor #02](Step02_Refactor)
* Paso 3: [Refactor #03](Step03_Refactor)
* Paso 4: [Refactor #04](Step04_Refactor)
* Paso 5: [Refactor #05](Step05_Refactor)
* Paso 6: [Refactor #06](Step06_Refactor)
* Paso 7: [Nueva Funcionalidad #07](Step07_NewFunctionality)




