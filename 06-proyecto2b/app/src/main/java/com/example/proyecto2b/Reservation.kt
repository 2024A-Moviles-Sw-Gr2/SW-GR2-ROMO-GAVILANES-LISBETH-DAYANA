package com.example.proyecto2b

data class Reservation(
    val canchaName: String,
    val canchaLocation: String,
    val date: String,
    val time: String,
    val numberOfPlayers: Int,
    val equipment: Equipment,
    val userId: Int
)