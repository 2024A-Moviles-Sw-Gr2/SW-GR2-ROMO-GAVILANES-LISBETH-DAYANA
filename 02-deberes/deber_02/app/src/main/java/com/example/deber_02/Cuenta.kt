package com.example.deber_02

import android.os.Parcel
import android.os.Parcelable

data class Cuenta(
    var id: Int,
    var nombre: String,
    var descripcion: String,
    var numeroSuscriptores: Int,
    var estaVerificada: Boolean,
    var correoElectronico: String,
    var videos: MutableList<Video> = mutableListOf()
) : Parcelable {
    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Cuenta> {
            override fun createFromParcel(parcel: Parcel): Cuenta {
                return Cuenta(parcel)
            }

            override fun newArray(size: Int): Array<Cuenta?> {
                return arrayOfNulls(size)
            }
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
        parcel.createTypedArrayList(Video.CREATOR) ?: mutableListOf()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(descripcion)
        parcel.writeInt(numeroSuscriptores)
        parcel.writeByte(if (estaVerificada) 1 else 0)
        parcel.writeString(correoElectronico)
        parcel.writeTypedList(videos)
    }

    override fun describeContents(): Int {
        return 0
    }
}