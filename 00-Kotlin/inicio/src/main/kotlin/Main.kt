import java.util.Date

fun main() {
    print("Hola mundo")
    // Unmutable (No se pueden RE ASIGNAR "=")
    val inmutable: String = "Lisbeth"
    // inmutable = "Dayana" // ERROR!
    // Mutables
    var mutable: String = "Dayana"
    mutable = "Lisbeth" // OK
    // VAL > VAR

    // Duck Typing
    var ejemploVariable = " Lisbeth Romo "
    val edadEjemplo: Int = 12
    ejemploVariable.trim() // Borrar espacios en blanco
    // ejemploVariable = edadEjemplo // ERROR! tipo incorrecto
    // Variables primitivas
    val nombre: String = "Lisbeth"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    // Clase en java
    val fechaNacimiento: Date = Date()

    println()

    // When (Switch)
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }

        "S" -> {
            println("Soltero")
        }

        else -> {
            println("No sabemos")
        }
    }
    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No" // if else comprimido


    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00, 20.00)
    // Named parameters
    // calcular sueldo, tasa, conoEspecial)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    // Uso de clases
    val sumaUno = Suma(1, 1) // new Suma(1,1) en Kotlin no hay "new"
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    // Arreglos
    // Estaticos
    val arregloEstatico: Array<Int> = arrayOf(1, 2, 3)
    println(arregloEstatico)
    // Dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    )

    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    // Operadores

    // FOR EACH = > Unit
    // Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int -> // -> Funcion flecha / => Funcion flecha gorda
            println("Valor actual: ${valorActual}");
        }
    // "it" (en ingles "eso") significa el elemento iterado
    arregloDinamico.forEach { println("Valor actual (it): ${it}") }

    // MAP -> Muta (Modifica cambia) el arreglo
    // 1) Enviamos el nuevo valor de la iteracion
    // 2) Nos devuelve un NUEVO ARREGLO con valores
    // de las iteraciones
    val repuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.0
        }
    println(repuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }
    println(respuestaMapDos)

    // FILTER -> Filtra el arreglo
    // 1) Devolver una expresion  (TRUE o FALSE)
    // 2) Nuevo arreglo FILTRADO
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            // Espresion o CONDICION
            val mayorsACinco: Boolean = valorActual > 5
            return@filter mayorsACinco
        }

    val respuestaFilterDos = arregloDinamico.filter { it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    // OR AND
    // OR -> ANY (Todos cumplen?)
    // AND -> ALL (Todos cumplen?)
    val respuestaAny: Boolean = arregloDinamico
        .any { valorActual: Int ->
            return@any (valorActual > 5)
        }
    println(respuestaAny) // True
    val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll) // False

    // REDUCE -> valor acumulado
    // Valor acumulado = 0 (Siempre empieza en 0 en kotlin)
    // [1,2,3,4,5] -> Acumular "SUMAR" estos valores del arreglo
    // valorIteracion1 = valorEmpieza + 1 = 0 + 1 = 1 -> Iteracion 1
    // valorIteracion2 = valorAcumuladoIteracion1 + 2 = 1 + 2 = 3 -> Iteracion 2
    // valorIteracion3 = valorAcumuladoIteracion2 + 3 = 3 + 3 = 6 -> Iteracion 3
    // valorIteracion4 = valorAcumuladoIteracion3 + 4 = 6 + 4 = 10 -> Iteracion 4
    // valorIteracion5 = valorAcumuladoIteracion4 + 5 = 10 + 5 = 15 -> Iteracion 5
    val respuestaReduce: Int = arregloDinamico
        .reduce{ acumulado: Int, valorActual: Int ->
            return@reduce (acumulado + valorActual) // -> Cambiar o usar la logica de negocio
        }
    println(respuestaReduce)
    // return@reduce acumulado + (itemCarrito.cantidad * itemCarrito.precio)
}

// void -> Unit
fun imprimirNombre(
    nombre: String
): Unit {
    println("Nombre:  ${nombre}") // Template Strings
}

fun calcularSueldo(
    sueldo: Double, // Requerido
    tasa: Double = 12.00, // Opcional (defecto)
    bonoEspecial: Double? = null // Opcional (nullable)
    // Variable? -> "?" Es Nullable (osea que puede en algun momento ser nulo)
): Double {
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if (bonoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        return sueldo * (100 / tasa) * bonoEspecial
    }
}

abstract class NumerosJava {
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno: Int,
        dos: Int
    ) {
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros(
    // Constructor Primario
    // Caso 1) Parametros normal
    // uno: Int , (parametro (sin modificador de acceso))

    // Caso 2) Parametros y propiedad (atributo) (private)
    // private var uno: Int (propiedad "instancia.uno")

    protected val numeroUno: Int, // Instancia.numeroUno
    protected val numeroDos: Int, // Instancia.numeroDos
    // parametroInutil: String, // Parametro
) {
    init { // Bloque constructor primario
        this.numeroUno
        this.numeroDos
        println("Inicializando")
    }
}

class Suma(
    // Constructor primario
    unoParametro: Int, // Parametro
    dosParametro: Int, // Parametro
) : Numeros(
    //Clase papa, Numeros (estendiendo)
    unoParametro,
    dosParametro,
) {
    public val soyPublicoExplicito: String = "Explicito" // Publicos
    val soyPublicoImplicito: String = "Implicito" // Publicos (propiedades, metodos)

    init { // Bloque Codigo Constructor primario
        // this.unoParametro // ERROR no existe
        this.numeroUno // this. OPCIONAL (propiedades, metodos)
        this.numeroDos // this. OPCIONAL (propiedades, metodos)
        numeroUno
        numeroDos
        this.soyPublicoExplicito
        soyPublicoImplicito // this. OPCIONAL (propiedades, metodos)
    }

    constructor( // Constructor secundario
        uno: Int?,
        dos: Int
    ) : this(
        if (uno == null) 0 else uno,
        dos
    )

    constructor( // Constructor tercero
        uno: Int,
        dos: Int?
    ) : this(
        uno,
        if (dos == null) 0 else dos,
    )

    constructor( // Constructor cuarto
        uno: Int?,
        dos: Int?
    ) : this(
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos,
    )

    // public fun sumar()Int{ (Modificar "public" es OPCIONAL)
    fun sumar(): Int {
        val total = numeroUno + numeroDos
        // Suma.agregarHistorial(total) ("Suma." o "NombreClase." es OPCIONAL)
        agregarHistorial(total)
        return total
    }

    companion object { // Comparte entre todas las instancias, similar al Static
        // funciones y variables
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }

        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma: Int) {
            historialSumas.add(valorTotalSuma)
        }
    }
}
