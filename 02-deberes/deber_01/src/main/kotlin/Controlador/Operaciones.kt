package Controlador

import Modelos.Cuenta
import Modelos.Video
import java.util.Date

class Operaciones(private val escritorArchivos: EscritorYLectorArchivos) {
    private val cuentas = escritorArchivos.leerArchivo().toMutableList()

    fun crearCuenta(
        nombre: String,
        descripcion: String,
        numeroSuscriptores: Int,
        estaVerificada: Boolean,
        correo: String,
    ) {
        val nuevaCuenta = Cuenta(
            generarId(),
            nombre,
            descripcion,
            numeroSuscriptores,
            estaVerificada,
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
            println("Descripción del canal: ${cuenta.descripcion}")
            println("Numero de suscriptores: ${cuenta.numeroSuscriptores}")
            println("Esta verificada: ${if (cuenta.estaVerificada) "Si" else "No"}")
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
            println("ID: ${cuenta.id}, Nombre del canal: ${cuenta.nombre}, Descripción: ${cuenta.descripcion}, Correo electrónico: ${cuenta.correoElectronico}")
        }
    }

    fun mostrarVideosDeCuenta(idCuenta: Int) {
        val cuenta = cuentas.find { it.id == idCuenta }
        if (cuenta != null) {
            println("Videos de la cuenta con ID: ${cuenta.id}, Nombre del canal: ${cuenta.nombre}")
            cuenta.videos.forEach { video ->
                println("   - ID: ${video.id}")
                println("     Título: ${video.titulo}")
                println("     Duración: ${video.duracion}")
                println("     Número de vistas: ${video.numeroVistas}")
                println("     Fecha cuando se subio: ${video.fechaSubido}")
                println("     Número de me gusta: ${video.numeroLikes}")
            }
        } else {
            println("La cuenta con id $idCuenta no ha sido encontrada")
        }
    }

    fun actualizarCuenta(id: Int) {
        val cuenta = cuentas.find { it.id == id }
        println("Si no se desea actualizar ese campo, se tiene que dejar en blanco")
        if (cuenta != null) {
            print("Nuevo nombre del canal (${cuenta.nombre}): ")
            val nuevoNombre = readLine()?.takeIf { it.isNotBlank() } ?: cuenta.nombre

            print("Nueva descripción del canal (${cuenta.descripcion}): ")
            val nuevoDescripcion = readLine()?.takeIf { it.isNotBlank() } ?: cuenta.descripcion

            print("Nuevo número de suscriptores (${cuenta.numeroSuscriptores}): ")
            val nuevoNumeroSuscriptores = readLine()?.takeIf { it.isNotBlank() }?.toIntOrNull() ?: cuenta.numeroSuscriptores

            print("¿Esta verificada? (${if (cuenta.estaVerificada) "Si" else "No"}): ")
            val nuevaVerificacionInput = readLine()?.takeIf { it.isNotBlank() }
            val nuevoEstaVerificada = when (nuevaVerificacionInput?.toLowerCase()) {
                "si", "Si", "SI" -> true
                "no", "No", "NO" -> false
                else -> cuenta.estaVerificada
            }

            print("Nuevo correo electrónico (${cuenta.correoElectronico}): ")
            val nuevoCorreo = readLine()?.takeIf { it.isNotBlank() } ?: cuenta.correoElectronico

            cuenta.nombre = nuevoNombre
            cuenta.descripcion = nuevoDescripcion
            cuenta.numeroSuscriptores = nuevoNumeroSuscriptores
            cuenta.estaVerificada = nuevoEstaVerificada
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
            println("La cuenta se ha eliminado: $cuenta")
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
            print("Duración del video (Por ejemplo: 34.5): ")
            val duracion = readLine()?.toDoubleOrNull() ?: 0.0
            print("Número de vistas del video: ")
            val numeroVistas = readLine()?.toIntOrNull() ?: 0
            print("Número de me gustas del video: ")
            val numeroLikes = readLine()?.toIntOrNull() ?: 0

            val nuevoVideo = Video(
                id = generarIdVideo(cuenta.videos),
                titulo = titulo,
                duracion = duracion,
                numeroVistas = numeroVistas,
                fechaSubido = Date(),
                numeroLikes = numeroLikes
            )
            cuenta.videos.add(nuevoVideo)
            println("Video subido a la cuenta con ID $idCuenta: $nuevoVideo")
            escritorArchivos.guardarDatosEnArchivo(cuentas)
        } else {
            println("El id $idCuenta no ha sido encontrado")
        }
    }

    fun actualizarVideoEnCuenta(idCuenta: Int, idVideo: Int) {
        val cuenta = cuentas.find { it.id == idCuenta }
        println("Si no se desea actualizar ese campo, se tiene que dejar en blanco")
        if (cuenta != null) {
            val video = cuenta.videos.find { it.id == idVideo }
            if (video != null) {
                print("Nuevo título del video (${video.titulo}): ")
                val nuevoTitulo = readLine()?.takeIf { it.isNotBlank() } ?: video.titulo

                print("Nueva duración del video (${video.duracion}): ")
                val nuevaDuracion = readLine()?.takeIf { it.isNotBlank() }?.toDoubleOrNull() ?: video.duracion

                print("Nuevo número de vistas del video (${video.numeroVistas}): ")
                val nuevoNumeroVistas = readLine()?.takeIf { it.isNotBlank() }?.toIntOrNull() ?: video.numeroVistas

                print("Nuevo número de me gustas del video (${video.numeroLikes}): ")
                val nuevoNumeroLikes = readLine()?.takeIf { it.isNotBlank() }?.toIntOrNull() ?: video.numeroLikes

                video.titulo = nuevoTitulo
                video.duracion = nuevaDuracion
                video.numeroVistas = nuevoNumeroVistas
                video.numeroLikes = nuevoNumeroLikes

                println("Video Actualizado: $video")
                escritorArchivos.guardarDatosEnArchivo(cuentas)
            } else {
                println("El video con id $idVideo no ha sido encontrado en la cuenta con id $idCuenta")
            }
        } else {
            println("La cuenta con id $idCuenta no ha sido encontrada")
        }
    }

    fun eliminarVideoEnCuenta(idCuenta: Int, idVideo: Int) {
        val cuenta = cuentas.find { it.id == idCuenta }
        if (cuenta != null) {
            val video = cuenta.videos.find { it.id == idVideo }
            if (video != null) {
                cuenta.videos.remove(video)
                println("El video se ha eliminado: $video")
                escritorArchivos.guardarDatosEnArchivo(cuentas)
            } else {
                println("El video con id $idVideo no ha sido encontrado en la cuenta con id $idCuenta")
            }
        } else {
            println("La cuenta con id $idCuenta no ha sido encontrada")
        }
    }

    private fun generarId(): Int {
        return if (cuentas.isEmpty()) 1 else cuentas.maxByOrNull { it.id }!!.id + 1
    }

    private fun generarIdVideo(videos: List<Video>): Int {
        return if (videos.isEmpty()) 1 else videos.maxByOrNull { it.id }!!.id + 1
    }
}
