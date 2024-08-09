package com.example.examen2b.Model

data class Cuenta(
    var id: Int = 0,
    var nombre: String,
    var descripcion: String,
    var numeroSuscriptores: Int,
    var estaVerificada: Boolean,
    var correoElectronico: String,
) {

    constructor() : this(
        id = 0,
        nombre = "",
        descripcion = "",
        numeroSuscriptores = 0,
        estaVerificada = false,
        correoElectronico = "",
    )

    constructor(
        nombre: String,
        descripcion: String,
        numeroSuscriptores: Int,
        estaVerificada: Boolean,
        correoElectronico: String
    ) : this(
        id = 0,
        nombre = nombre,
        descripcion = descripcion,
        numeroSuscriptores = numeroSuscriptores,
        estaVerificada = estaVerificada,
        correoElectronico = correoElectronico
    )
}