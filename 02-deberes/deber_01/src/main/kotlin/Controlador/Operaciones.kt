package Controlador

import Modelos.Cuenta
import Modelos.Video
import java.util.Date

class Operaciones(private val escritorArchivos: EscritorArchivos) {
    private val cuentas = mutableListOf<Cuenta>()

    fun crearCuenta(
        nombre: String,
        numeroSuscriptores: Int,
        estaVerificada: Boolean,
        fechaCreacion: Date,
        correo: String,
    ) {
        val nuevaCuenta = Cuenta(
            generarId(),
            nombre,
            numeroSuscriptores,
            estaVerificada,
            fechaCreacion,
            correo
        )
        cuentas.add(nuevaCuenta)
        println("Cuenta creada: $nuevaCuenta")
        escritorArchivos.guardarDatosEnArchivo(cuentas)
    }

    fun mostrarCuenta() {
        cuentas.forEach { cuenta ->
            println("***********Cuenta***********")
            println("ID: ${cuenta.id}")
            println("Nombre del canal: ${cuenta.nombre}")
            println("Numero de suscriptores: ${cuenta.numeroSuscriptores}")
            println("Esta verificada: ${if (cuenta.estaVerificada) "Si" else "No"}")
            println("Fecha de creación: ${cuenta.fechaCreacion}")
            println("Correo electrónico: ${cuenta.correoElectronico}")
            println("**** Videos ****")
            cuenta.videos.forEach { video ->
                println("   - ID: ${video.id}")
                println("     Título: ${video.titulo}")
                println("     Duración: ${video.duracion}")
                println("     Número de vistas: ${video.numeroVistas}")
                println("     Fecha cuando se subio: ${video.fechaSubido}")
                println("     Número de me gusta: ${video.numeroLikes}")
            }
            println()
        }
    }

    fun mostrarCuentaID() {
        cuentas.forEach { cuenta ->
            println("ID: ${cuenta.id}, Nombre del canal: ${cuenta.nombre}, Correo electrónico: ${cuenta.correoElectronico}")
        }
    }

    fun actualizarCuenta(id: Int) {
        val cuenta = cuentas.find { it.id == id }
        if (cuenta != null) {
            print("Nuevo nombre del canal (${cuenta.nombre}): ")
            val nuevoNombre = readLine() ?: cuenta.nombre
            print("Nuevo número de suscriptores (${cuenta.numeroSuscriptores}): ")
            val nuevoNumeroSuscriptores = readLine()?.toIntOrNull() ?: cuenta.numeroSuscriptores
            print("¿Esta verificada? (${cuenta.estaVerificada}): ")
            val nuevoEstaVerificada = readLine()?.toBoolean() ?: cuenta.estaVerificada
            print("Nuevo correo electrónico (${cuenta.correoElectronico}):")
            val nuevoCorreo = readLine() ?: cuenta.correoElectronico
            cuenta.nombre = nuevoNombre
            cuenta.numeroSuscriptores = nuevoNumeroSuscriptores
            cuenta.estaVerificada = nuevoEstaVerificada
            cuenta.fechaCreacion = cuenta.fechaCreacion
            cuenta.correoElectronico = nuevoCorreo

            println("Cuenta Actualizada: $cuenta")
            escritorArchivos.guardarDatosEnArchivo(cuentas)
        } else {
            println("La cuenta con id $id no ha sido encontrada")
        }
    }

    fun eliminarCuenta(id: Int) {
        val cuenta = cuentas.find { it.id == id }
        if (cuenta != null) {
            cuentas.remove(cuenta)
            println("La cuenta se a eliminado: $cuenta")
            escritorArchivos.guardarDatosEnArchivo(cuentas)
        } else {
            println("La cuenta con id $id no ha sido encontrada")
        }
    }

    fun subirVideoACuenta(idCuenta: Int) {
        val cuenta = cuentas.find { it.id == idCuenta }

        if (cuenta != null) {
            println("\n***********Subir video a la cuenta***********")

            print("Titulo del video: ")
            val titulo = readLine() ?: ""
            print("Duración del video: ")
            val duracion = readLine()?.toDoubleOrNull() ?: 0.0
            print("Número de vistas del video: ")
            val numeroVistas = readLine()?.toIntOrNull() ?: 0
            print("Número de me gustas del video: ")
            val numeroLikes = readLine()?.toIntOrNull() ?: 0

            val nuevoVideo = Video(
                titulo = titulo,
                duracion = duracion,
                numeroVistas = numeroVistas,
                fechaSubido = Date(),
                numeroLikes = numeroLikes
            )
            cuenta.videos.add(nuevoVideo)
            println("Video subido $idCuenta: $nuevoVideo")
            escritorArchivos.guardarDatosEnArchivo(cuentas)
        } else {
            println("El id $idCuenta no ha sido encontrado")
        }
    }

    private fun generarId(): Int {
        return if (cuentas.isEmpty()) 1 else cuentas.maxByOrNull { it.id }!!.id + 1
    }
}