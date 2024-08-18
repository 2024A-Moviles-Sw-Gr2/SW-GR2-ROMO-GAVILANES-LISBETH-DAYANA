package com.example.proyecto2b

data class TimeSlot(
    val time: String,
    val isReserved: Boolean,
    var isSelected: Boolean = false
)