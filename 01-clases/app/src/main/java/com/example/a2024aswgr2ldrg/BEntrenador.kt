package com.example.a2024aswgr2ldrg

class BEntrenador(
    var int: Int,
    var nombre:String,
    var descripcion:String?
) {
    override fun toString(): String {
        return "$nombre ${descripcion}"
    }
}