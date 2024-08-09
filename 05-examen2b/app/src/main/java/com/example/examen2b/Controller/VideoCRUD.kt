package com.example.examen2b.Controller

import android.content.ContentValues
import android.content.Context
import com.example.examen2b.Database.BaseDeDatosHelper
import com.example.examen2b.Model.Video
import java.util.Date

class VideoCRUD(context: Context) {
    private val dbHelper: BaseDeDatosHelper = BaseDeDatosHelper(context)

    fun crearVideo(video: Video) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("cuentaId", video.cuentaId)
            put("titulo", video.titulo)
            put("duracion", video.duracion)
            put("numeroVistas", video.numeroVistas)
            put("fechaSubido", video.fechaSubido.time)
            put("numeroLikes", video.numeroLikes)
            put("latitud", video.latitud)
            put("longitud", video.longitud)
        }
        db.insert(BaseDeDatosHelper.TABLA_VIDEO, null, values)
        db.close()
    }

    fun obtenerVideosPorCuentaId(cuentaId: Int): List<Video> {
        val listaVideos = mutableListOf<Video>()
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM ${BaseDeDatosHelper.TABLA_VIDEO} WHERE cuentaId = ?"
        val cursor = db.rawQuery(query, arrayOf(cuentaId.toString()))
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"))
                val duracion = cursor.getDouble(cursor.getColumnIndexOrThrow("duracion"))
                val numeroVistas = cursor.getInt(cursor.getColumnIndexOrThrow("numeroVistas"))
                val fechaSubido = Date(cursor.getLong(cursor.getColumnIndexOrThrow("fechaSubido")))
                val numeroLikes = cursor.getInt(cursor.getColumnIndexOrThrow("numeroLikes"))
                val latitud = cursor.getDouble(cursor.getColumnIndexOrThrow("latitud"))
                val longitud = cursor.getDouble(cursor.getColumnIndexOrThrow("longitud"))
                val video = Video(id, cuentaId, titulo, duracion, numeroVistas, fechaSubido, numeroLikes, latitud, longitud)
                listaVideos.add(video)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaVideos
    }

    fun obtenerVideoPorId(videoId: Int): Video? {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM ${BaseDeDatosHelper.TABLA_VIDEO} WHERE id = ?"
        val cursor = db.rawQuery(query, arrayOf(videoId.toString()))
        var video: Video? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val cuentaId = cursor.getInt(cursor.getColumnIndexOrThrow("cuentaId"))
            val titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"))
            val duracion = cursor.getDouble(cursor.getColumnIndexOrThrow("duracion"))
            val numeroVistas = cursor.getInt(cursor.getColumnIndexOrThrow("numeroVistas"))
            val fechaSubido = Date(cursor.getLong(cursor.getColumnIndexOrThrow("fechaSubido")))
            val numeroLikes = cursor.getInt(cursor.getColumnIndexOrThrow("numeroLikes"))
            val latitud = cursor.getDouble(cursor.getColumnIndexOrThrow("latitud"))
            val longitud = cursor.getDouble(cursor.getColumnIndexOrThrow("longitud"))
            video = Video(id, cuentaId, titulo, duracion, numeroVistas, fechaSubido, numeroLikes, latitud, longitud)
        }
        cursor.close()
        db.close()
        return video
    }

    fun actualizarVideo(video: Video) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("titulo", video.titulo)
            put("duracion", video.duracion)
            put("numeroVistas", video.numeroVistas)
            put("fechaSubido", video.fechaSubido.time)
            put("numeroLikes", video.numeroLikes)
            put("latitud", video.latitud)
            put("longitud", video.longitud)
        }
        val whereClause = "id = ?"
        val whereArgs = arrayOf(video.id.toString())
        db.update(BaseDeDatosHelper.TABLA_VIDEO, values, whereClause, whereArgs)
        db.close()
    }

    fun eliminarVideoPorId(videoId: Int) {
        val db = dbHelper.writableDatabase
        val whereClause = "id = ?"
        val whereArgs = arrayOf(videoId.toString())
        db.delete(BaseDeDatosHelper.TABLA_VIDEO, whereClause, whereArgs)
        db.close()
    }
}