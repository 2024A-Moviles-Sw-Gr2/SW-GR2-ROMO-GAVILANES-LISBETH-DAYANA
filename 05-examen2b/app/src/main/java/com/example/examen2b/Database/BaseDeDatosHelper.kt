package com.example.examen2b.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDeDatosHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NOMBRE, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NOMBRE = "biblioteca_digital"
        const val DATABASE_VERSION = 1
        const val TABLA_CUENTA = "cuenta"
        const val TABLA_VIDEO = "video"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaCuenta = """
            CREATE TABLE $TABLA_CUENTA (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                descripcion TEXT,
                numeroSuscriptores INTEGER,
                estaVerificada BIT,
                correoElectronico TEXT
            )
        """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaCuenta)

        val scriptSQLCrearTablaVideo = """
            CREATE TABLE $TABLA_VIDEO (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                cuentaId INTEGER,
                titulo TEXT,
                duracion REAL,
                numeroVistas INTEGER,
                fechaSubido INTEGER,
                numeroLikes INTEGER,
                latitud REAL,     
                longitud REAL,
                FOREIGN KEY(cuentaId) REFERENCES $TABLA_CUENTA(id)
            )
        """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaVideo)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Implementación para manejar cambios de versión
    }
}
