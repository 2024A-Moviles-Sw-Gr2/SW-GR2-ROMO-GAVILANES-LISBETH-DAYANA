package com.example.deber_02

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

data class Video(
    val id: Int = generarId(),
    var titulo: String,
    var duracion: Double,
    var numeroVistas: Int,
    var fechaSubido: Date,
    var numeroLikes: Int
) : Parcelable {
    companion object {
        private var contadorId: Int = 1

        private fun generarId(): Int {
            return contadorId++
        }

        @JvmField
        val CREATOR = object : Parcelable.Creator<Video> {
            override fun createFromParcel(parcel: Parcel): Video {
                return Video(parcel)
            }

            override fun newArray(size: Int): Array<Video?> {
                return arrayOfNulls(size)
            }
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readInt(),
        Date(parcel.readLong()),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(titulo)
        parcel.writeDouble(duracion)
        parcel.writeInt(numeroVistas)
        parcel.writeLong(fechaSubido.time)
        parcel.writeInt(numeroLikes)
    }

    override fun describeContents(): Int {
        return 0
    }
}