package com.example.proyecto2b

data class User(
    val id: Int,
    val nombreApellido: String,
    val telefono: String,
    val cedula: String,
    val email: String,  // New email field
    val username: String,
    val password: String
)