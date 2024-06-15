package Modelos

import java.util.Date

data class Cuenta(
    var id: Int,
    var nombre: String,
    var descripcion: String,
    var numeroSuscriptores: Int,
    var estaVerificada: Boolean,
    var correoElectronico: String,
    var videos: MutableList<Video> = mutableListOf()
)
