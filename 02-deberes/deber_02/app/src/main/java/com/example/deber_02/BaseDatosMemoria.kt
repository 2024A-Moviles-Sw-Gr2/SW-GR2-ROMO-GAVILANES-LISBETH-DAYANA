package com.example.deber_02

import java.util.Date

class BaseDatosMemoria {
    companion object {
        val arregloCuentas = arrayListOf<Cuenta>()
        init {
            arregloCuentas.add(
                Cuenta(
                    id = 1,
                    nombre = "Cuenta 1",
                    descripcion = "Descripción de la cuenta 1",
                    numeroSuscriptores = 1000,
                    estaVerificada = true,
                    correoElectronico = "cuenta1@example.com",
                    videos = mutableListOf(
                        Video(titulo = "Video C1", duracion = 10.0, numeroVistas = 500, fechaSubido = Date(), numeroLikes = 100),
                        Video(titulo = "Video C2", duracion = 15.5, numeroVistas = 800, fechaSubido = Date(), numeroLikes = 200)
                    )
                )
            )

            arregloCuentas.add(
                Cuenta(
                    id = 2,
                    nombre = "Cuenta 2",
                    descripcion = "Descripción de la cuenta 2",
                    numeroSuscriptores = 2000,
                    estaVerificada = false,
                    correoElectronico = "cuenta2@example.com",
                    videos = mutableListOf(
                        Video(titulo = "Video P1", duracion = 5.0, numeroVistas = 200, fechaSubido = Date(), numeroLikes = 50),
                        Video(titulo = "Video P2", duracion = 7.5, numeroVistas = 300, fechaSubido = Date(), numeroLikes = 75)
                    )
                )
            )

            arregloCuentas.add(
                Cuenta(
                    id = 3,
                    nombre = "Cuenta 3",
                    descripcion = "Descripción de la cuenta 3",
                    numeroSuscriptores = 3000,
                    estaVerificada = true,
                    correoElectronico = "cuenta3@example.com",
                    videos = mutableListOf(
                        Video(titulo = "Video R1", duracion = 20.0, numeroVistas = 1000, fechaSubido = Date(), numeroLikes = 300),
                        Video(titulo = "Video R2", duracion = 25.0, numeroVistas = 1500, fechaSubido = Date(), numeroLikes = 400)
                    )
                )
            )
        }
    }
}