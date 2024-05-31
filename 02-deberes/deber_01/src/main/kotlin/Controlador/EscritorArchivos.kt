package Controlador

import Modelos.Cuenta
import java.io.File
import java.io.FileWriter

class EscritorArchivos(private val archivo: String) {

    fun guardarDatosEnArchivo(cuentas: List<Cuenta>) {
        try {
            val archivo = File(archivo)
            FileWriter(archivo).use { fw ->
                cuentas.forEach { cuenta ->
                    with(cuenta) {

                        val estaVerificadaTransformada = if (estaVerificada) "Si" else "No"

                        fw.write("Cuenta de youtube\n")
                        fw.write("ID: $id\n")
                        fw.write("Cuenta: $nombre\n")
                        fw.write("Nombre del canal: $nombre\n")
                        fw.write("Número de suscriptores: $numeroSuscriptores\n")
                        fw.write("Esta verificada: $estaVerificadaTransformada\n")
                        fw.write("Correo electrónico: $correoElectronico\n")

                        if (videos.isNotEmpty()) {
                            videos.forEach { video ->
                                with(video) {

                                    fw.write("  - ID: $id\n")
                                    fw.write("    Titulo: $titulo\n")
                                    fw.write("    Duración: $duracion\n")
                                    fw.write("    Número de vistas: $numeroVistas\n")
                                    fw.write("    Fecha cuando fue subido: $fechaSubido\n")
                                    fw.write("    Número de me gustas: $numeroLikes\n")
                                }
                            }
                        }
                        fw.write("\n")
                    }
                }
            }
            println("Datos Guardados")
        } catch (ex: Exception) {
            println("Error al Guardar los Datos")
        }
    }
}

