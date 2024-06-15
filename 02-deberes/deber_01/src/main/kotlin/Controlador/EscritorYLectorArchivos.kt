package Controlador

import Modelos.Cuenta
import Modelos.Video
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Locale

class EscritorYLectorArchivos(private val archivo: String) {

    fun guardarDatosEnArchivo(cuentas: List<Cuenta>) {
        try {
            val archivo = File(archivo)
            FileWriter(archivo).use { fw ->
                cuentas.forEach { cuenta ->
                    with(cuenta) {

                        val estaVerificadaTransformada = if (estaVerificada) "Si" else "No"

                        fw.write("Cuenta de youtube\n")
                        fw.write("ID Cuenta: $id\n")
                        fw.write("Nombre del canal: $nombre\n")
                        fw.write("Descripción: $descripcion\n")
                        fw.write("Número de suscriptores: $numeroSuscriptores\n")
                        fw.write("Esta verificada: $estaVerificadaTransformada\n")
                        fw.write("Correo electrónico: $correoElectronico\n")

                        if (videos.isNotEmpty()) {
                            videos.forEach { video ->
                                with(video) {
                                    val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
                                    val fechaSubidaStr = dateFormat.format(fechaSubido)
                                    fw.write("  - ID Video: $id\n")
                                    fw.write("    Titulo: $titulo\n")
                                    fw.write("    Duración: $duracion\n")
                                    fw.write("    Número de vistas: $numeroVistas\n")
                                    fw.write("    Fecha cuando fue subido: $fechaSubidaStr\n")
                                    fw.write("    Número de me gustas: $numeroLikes\n")
                                }
                            }
                        }
                    }
                }
            }
            println("Datos Guardados")
        } catch (ex: Exception) {
            println("Error al Guardar los Datos")
        }
    }

    fun leerArchivo(): List<Cuenta> {
        val cuentas = mutableListOf<Cuenta>()
        val lines = File(archivo).readLines()

        var cuenta: Cuenta? = null
        var videos = mutableListOf<Video>()
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)

        var i = 0
        while (i < lines.size) {
            val line = lines[i]
            when {
                line.startsWith("Cuenta de youtube") -> {
                    if (cuenta != null) {
                        cuenta.videos.addAll(videos)
                        cuentas.add(cuenta)
                        videos = mutableListOf()
                    }
                    cuenta = Cuenta(0, "", "", 0, false, "")
                }

                line.startsWith("ID Cuenta:") -> {
                    cuenta?.id = line.substringAfter(":").trim().toInt()
                }

                line.startsWith("Nombre del canal:") -> {
                    cuenta?.nombre = line.substringAfter(":").trim()
                }

                line.startsWith("Descripción:") -> {
                    cuenta?.descripcion = line.substringAfter(":").trim()
                }

                line.startsWith("Número de suscriptores:") -> {
                    cuenta?.numeroSuscriptores = line.substringAfter(":").trim().toInt()
                }

                line.startsWith("Esta verificada:") -> {
                    cuenta?.estaVerificada = line.substringAfter(":").trim() == "Si"
                }

                line.startsWith("Correo electrónico:") -> {
                    cuenta?.correoElectronico = line.substringAfter(":").trim()
                }

                line.startsWith("  - ID Video:") -> {
                    val idVideo = line.substringAfter(":").trim().toInt()
                    val titulo = lines[++i].substringAfter(":").trim()
                    val duracion = lines[++i].substringAfter(":").trim().toDouble()
                    val numeroVistas = lines[++i].substringAfter(":").trim().toInt()
                    val fechaSubidaStr = lines[++i].substringAfter(":").trim()
                    val fechaSubida = dateFormat.parse(fechaSubidaStr)
                    val numeroMeGustas = lines[++i].substringAfter(":").trim().toInt()
                    val video = Video(idVideo, titulo, duracion, numeroVistas, fechaSubida, numeroMeGustas)
                    videos.add(video)
                }
            }
            i++
        }
        cuenta?.videos?.addAll(videos)
        if (cuenta != null) cuentas.add(cuenta)

        return cuentas
    }
}
