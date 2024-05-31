// Deber 01 - Lisbeth Romo
package Vistas

import Controlador.Operaciones
import Controlador.EscritorArchivos
import java.lang.NumberFormatException
import java.util.Date

fun main(){

    val escritorArchivos = EscritorArchivos("cuentas.txt")
    val operaciones = Operaciones(escritorArchivos)

    var opcion: Int

    do{
        println("\n***********Menu***********")
        println("1. Crear una cuenta de youtube")
        println("2. Mostrar cuentas")
        println("3. Actualizar una cuenta")
        println("4. Eliminar una cuenta")
        println("5. Subir video en la cuenta")
        println("6. Salir")
        print("Ingrese una opción: ")

        try{
            opcion = readLine()?.toInt() ?:0

            when (opcion){
                1 -> {
                    println("\n***********Crear una cuenta***********")
                    print("Nombre del canal: ")
                    val nombre = readLine() ?: ""
                    print("Número de suscriptores: ")
                    val numeroSuscriptores = readLine()?.toIntOrNull() ?: 0
                    print("¿Esta verificado? (Si/No): ")
                    val estaVerificadoEntrada = readLine()?.toLowerCase()
                    val estaVerificado = when (estaVerificadoEntrada){
                        "si" -> true
                        "no" -> false
                        else -> false
                    }
                    val fechaCreacion = Date()
                    print("Correo electrónico: ")
                    val correo = readLine() ?: ""
                    operaciones.crearCuenta(nombre, numeroSuscriptores, estaVerificado, fechaCreacion, correo)
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
                    println("\nSaliendo...")
                }
                else -> {
                    println("\nOpcion inválida. Ingrese otro número")
                }
            }
        }catch (ex: NumberFormatException){
            println("Error: Ingresa un Número Valido")
            opcion = 0
        }

    }while(opcion != 6)

}
