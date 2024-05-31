package Modelos

import java.util.Date

data class Video(
    val id: Int = generarId(),
    var titulo: String,
    var duracion: Double,
    var numeroVistas: Int,
    var fechaSubido: Date,
    var numeroLikes: Int
){
    companion object{
        private var contadorId: Int = 1

        private fun generarId(): Int{
            return contadorId++
        }
    }
}