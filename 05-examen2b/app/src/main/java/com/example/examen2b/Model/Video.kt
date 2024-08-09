package com.example.examen2b.Model

import java.util.Date

data class Video(
    var id: Int = 0,
    var cuentaId: Int = 0,
    var titulo: String,
    var duracion: Double,
    var numeroVistas: Int,
    var fechaSubido: Date,
    var numeroLikes: Int,
    var latitud: Double = 0.0,
    var longitud: Double = 0.0
) {

    constructor(
        cuentaId: Int,
        titulo: String,
        duracion: Double,
        numeroVistas: Int,
        fechaSubido: Date,
        numeroLikes: Int,
        latitud: Double,
        longitud: Double
    ) : this(0, cuentaId, titulo, duracion, numeroVistas, fechaSubido, numeroLikes, latitud, longitud)
}