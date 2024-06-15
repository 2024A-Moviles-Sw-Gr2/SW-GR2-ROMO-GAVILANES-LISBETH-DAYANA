package Vistas

import Controlador.Operaciones
import Controlador.EscritorYLectorArchivos
import java.lang.NumberFormatException

fun main() {
    val escritorArchivos = EscritorYLectorArchivos("cuentas.txt")
    val operaciones = Operaciones(escritorArchivos)

    var opcion: Int

    do {
        println("\n***********Menu***********")
        println("1. Crear una cuenta de youtube")
        println("2. Mostrar cuentas")
        println("3. Actualizar una cuenta")
        println("4. Eliminar una cuenta")
        println("5. Subir video en la cuenta")
        println("6. Actualizar un video")
        println("7. Eliminar un video")
        println("8. Salir")
        print("Ingrese una opción: ")

        try {
            opcion = readLine()?.toInt() ?: 0

            when (opcion) {
                1 -> {
                    println("\n***********Crear una cuenta***********")
                    print("Nombre del canal: ")
                    val nombre = readLine() ?: ""
                    print("Descripción del canal: ")
                    val descripcion = readLine() ?: ""
                    print("Número de suscriptores: ")
                    val numeroSuscriptores = readLine()?.toIntOrNull() ?: 0
                    print("¿Esta verificado? (Si/No): ")
                    val estaVerificadoEntrada = readLine()?.toLowerCase()
                    val estaVerificado = when (estaVerificadoEntrada) {
                        "si", "Si", "SI" -> true
                        "no", "No", "NO" -> false
                        else -> false
                    }
                    print("Correo electrónico: ")
                    val correo = readLine() ?: ""
                    operaciones.crearCuenta(nombre, descripcion, numeroSuscriptores, estaVerificado, correo)
                }
                2 -> {
                    println("\n***********Mostrar cuentas***********")
                    println("Lista de cuentas")
                    operaciones.mostrarCuenta()
                }
                3 -> {
                    println("\n***********Actualizar una cuenta***********")
                    println("Cuentas para actualizar:")
                    operaciones.mostrarCuentaID()

                    print("ID de la cuenta a actualizar: ")
                    val idActualizar = readLine()?.toIntOrNull() ?: 0
                    operaciones.actualizarCuenta(idActualizar)
                }
                4 -> {
                    println("\n***********Eliminar una cuenta***********")
                    println("Cuentas para eliminar:")
                    operaciones.mostrarCuentaID()

                    print("ID de la cuenta a Eliminar: ")
                    val idEliminar = readLine()?.toIntOrNull() ?: 0
                    operaciones.eliminarCuenta(idEliminar)
                }
                5 -> {
                    println("\n***********Agregar video a la cuenta***********")
                    println("Lista de cuentas")
                    operaciones.mostrarCuentaID()

                    print("ID de la cuenta al que desea subir un video: ")
                    val idCuenta = readLine()?.toIntOrNull() ?: 0
                    operaciones.subirVideoACuenta(idCuenta)
                }
                6 -> {
                    println("\n***********Actualizar un video de la cuenta***********")
                    println("Lista de cuentas")
                    operaciones.mostrarCuentaID()

                    print("ID de la cuenta del video a actualizar: ")
                    val idCuenta = readLine()?.toIntOrNull() ?: 0
                    operaciones.mostrarVideosDeCuenta(idCuenta)
                    print("ID del video a actualizar: ")
                    val idVideo = readLine()?.toIntOrNull() ?: 0
                    operaciones.actualizarVideoEnCuenta(idCuenta, idVideo)
                }
                7 -> {
                    println("\n***********Eliminar un video de la cuenta***********")
                    println("Lista de cuentas")
                    operaciones.mostrarCuentaID()

                    print("ID de la cuenta del video a eliminar: ")
                    val idCuenta = readLine()?.toIntOrNull() ?: 0
                    operaciones.mostrarVideosDeCuenta(idCuenta)
                    print("ID del video a eliminar: ")
                    val idVideo = readLine()?.toIntOrNull() ?: 0
                    operaciones.eliminarVideoEnCuenta(idCuenta, idVideo)
                }
                8 -> {
                    println("\nSaliendo")
                }
                else -> {
                    println("\nOpcion inválida. Ingrese otro número")
                }
            }
        } catch (ex: NumberFormatException) {
            println("Error: Ingresa un Número Valido")
            opcion = 0
        }
    } while (opcion != 8)
}
