# Grados de Separación (Degrees of Separation)

## Integrantes
**John Garrido** (20-10293)

Este proyecto es una implementación en Kotlin para calcular los grados de separación entre dos personas en una red social. El algoritmo utiliza un enfoque de Búsqueda en Anchura (BFS) sobre un grafo no dirigido representado mediante Listas de Adyacencia. La estructura principal utiliza un `HashMap<String, ArrayList<String>>` donde cada clave es una persona y el valor es la lista de sus amigos directos.

---

## Instrucciones de Ejecución

# Requisitos
* Kotlin 1.3+ o JDK 8+
* Visual Studio Code, IntelliJ IDEA, o cualquier editor con soporte para Kotlin.

### Compilación y Ejecución: 
a) Clonar el repositorio o descargar los archivos en una carpeta.
b) Archivos necesarios:
    * DegreesOfSeparation.kt: Código fuente principal.
    * input.txt: Archivo de texto con las relaciones de amistad (formato: `Nombre1 Nombre2`).
c) Compilación: Abrir la terminal en la carpeta y ejecutar en bash: kotlinc DegreesOfSeparation.kt -include-runtime -d DegreesOfSeparation.jar
d) Ejecución: Ejecutar el archivo `.jar` generado pasando los dos nombres a consultaR en bash:
    java -jar DegreesOfSeparation.jar <NombreInicio> <NombreDestino>

## Complejidad Computacional (Big O)

Método: Construcción del Grafo (en `main`)
* Complejidad: O(L) donde L es el número de líneas en `input.txt`.
* Justificación: Se recorre el archivo una sola vez. Las inserciones en el `HashMap` y `ArrayList` son operaciones amortizadas de O(1).

Método: `calcularGrados(grafo, inicio, fin)`
* Complejidad: O(V + E)
* Justificación: Al utilizar BFS (Búsqueda en Anchura), en el peor de los casos se visitan todos los vértices (V, personas) y se recorren todas las aristas (E, relaciones de amistad) una vez para encontrar el camino más corto o determinar que no existe conexión.

Operaciones Internas del BFS:
* `cola.poll()` / `cola.add()`: O(1) gracias al uso de `LinkedList`.
* `visitados.contains()` / `visitados.add()`: O(1) promedio gracias al uso de `HashSet`.
* Acceso a amigos (`grafo[nombre]`): O(1) promedio gracias al uso de `HashMap`.

## Decisiones de Implementación

a. Estructura de Datos Principal
Se eligió `HashMap<String, ArrayList<String>>` porque:
1.  Acceso Rápido: Permite obtener la lista de amigos de una persona en tiempo constante O(1).
2.  Flexibilidad: Al usar `String` como clave, no necesitamos mapear nombres a índices enteros manualmente.
3.  Simplicidad: `ArrayList` es eficiente para iterar sobre los amigos vecinos durante la expansión del BFS.

b. Algoritmo de Búsqueda (BFS)
Se utilizó BFS en lugar de DFS porque:
1.  BFS garantiza encontrar el camino más corto.
2.  DFS podría encontrar un camino, pero no necesariamente el de menor grado de separación, y podría entrar en ciclos profundos.

c. Control de Ciclos y Visitados
Se implementó un `HashSet<String>` llamado `visitados`:
1.  Esto evita que el algoritmo procese a la misma persona más de una vez, previniendo bucles infinitos en amistades circulares (ej: A es amigo de B, B es amigo de A).
2.  Garantiza que la primera vez que llegamos a un nodo, es a través del camino más corto posible desde el origen.

d. Manejo del Grafo No Dirigido (Simetría)
Como la amistad es simetrica, el programa inserta ambas direcciones en el grafo al leer el archivo:
`grafo[p1]?.add(p2)` y `grafo[p2]?.add(p1)`.
Esto asegura que el grafo se pueda navegar.

e. Uso de `Pair` en la Cola
Se utilizó la clase `Pair<String, Int>` dentro de la `Queue` para almacenar el estado:
1. Nombre: El nodo actual que se está visitando.
2. Distancia: El grado de separación acumulado hasta ese momento.
