import java.io.File
import java.util.LinkedList
import java.util.Queue

fun main(args: Array<String>) {
    // Verificamos que pasan los argumentos correctos
    if (args.size < 2) {
        println("Faltan argumentos. Uso: java -jar DegreesOfSeparation.jar Nombre1 Nombre2")
        return
    }

    val personaInicio = args[0]
    val personaDestino = args[1]

    // Estructura para el grafo
    val grafo = HashMap<String, ArrayList<String>>()

    // Leemos el archivo input.txt 
    val archivo = File("input.txt")
    if (!archivo.exists()) {
        println("Error: No se encuentra el archivo input.txt")
        return
    }

    val lineas = archivo.readLines()
    for (linea in lineas) {
        // Separamos por espacio
        val partes = linea.trim().split(" ")
        if (partes.size == 2) {
            val p1 = partes[0]
            val p2 = partes[1]

            // Agregamos la relación en ambos sentidos porque es simetrico
            if (!grafo.containsKey(p1)) {
                grafo[p1] = ArrayList()
            }
            grafo[p1]?.add(p2)

            if (!grafo.containsKey(p2)) {
                grafo[p2] = ArrayList()
            }
            grafo[p2]?.add(p1)
        }
    }

    // Llamamos a la función para saber la distancia
    val resultado = calcularGrados(grafo, personaInicio, personaDestino)
    println(resultado)
}

fun calcularGrados(grafo: HashMap<String, ArrayList<String>>, inicio: String, fin: String): Int {
    // Si es la misma persona, la distancia es 0
    if (inicio == fin) {
        return 0
    }

    // Si alguno de los dos no existe en el archivo, retornamos -1
    if (!grafo.containsKey(inicio) || !grafo.containsKey(fin)) {
        return -1
    }

    // Usamos BFS para encontrar el camino más corto
    val cola: Queue<Pair<String, Int>> = LinkedList()
    
    // Un Set para no repetir personas y evitar ciclos infinitos
    val visitados = HashSet<String>()

    // Inicializamos
    cola.add(Pair(inicio, 0))
    visitados.add(inicio)

    while (!cola.isEmpty()) {
        val actual = cola.poll() // Sacamos el primero de la cola
        val nombreActual = actual.first
        val distanciaActual = actual.second

        // Obtenemos los amigos de la persona actual
        val amigos = grafo[nombreActual]

        if (amigos != null) {
            for (amigo in amigos) {
                if (amigo == fin) {
                    // Llegamos al destino
                    return distanciaActual + 1
                }

                // Si no hemos visitado a este amigo, lo agregamos a la cola
                if (!visitados.contains(amigo)) {
                    visitados.add(amigo)
                    cola.add(Pair(amigo, distanciaActual + 1))
                }
            }
        }
    }

    // Si salimos del ciclo y no encontramos nada
    return -1
}