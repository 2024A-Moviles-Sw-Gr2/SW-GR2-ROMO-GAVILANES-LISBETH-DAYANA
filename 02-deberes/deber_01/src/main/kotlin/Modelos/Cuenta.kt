package Modelos

import java.util.Date

data class Cuenta(
    val id: Int,
    var nombre: String,
    var numeroSuscriptores: Int,
    var estaVerificada: Boolean,
    var fechaCreacion: Date,
    var correoElectronico: String,
    var videos: MutableList<Video> = mutableListOf()
)
