package com.example.examen2b.Controller

import android.content.ContentValues
import android.content.Context
import com.example.examen2b.Database.BaseDeDatosHelper
import com.example.examen2b.Model.Cuenta

class CuentaCRUD(context: Context) {
    private val dbHelper: BaseDeDatosHelper = BaseDeDatosHelper(context)

    fun crearCuenta(cuenta: Cuenta) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", cuenta.nombre)
            put("descripcion", cuenta.descripcion)
            put("numeroSuscriptores", cuenta.numeroSuscriptores)
            put("estaVerificada", if (cuenta.estaVerificada) 1 else 0)
            put("correoElectronico", cuenta.correoElectronico)
        }
        val nuevoId = db.insert(BaseDeDatosHelper.TABLA_CUENTA, null, values)
        cuenta.id = nuevoId.toInt()
        db.close()
    }

    fun obtenerTodas(): List<Cuenta> {
        val listaCuentas = mutableListOf<Cuenta>()
        dbHelper.readableDatabase.use { db ->
            val query = "SELECT * FROM ${BaseDeDatosHelper.TABLA_CUENTA}"
            val cursor = db.rawQuery(query, null)
            cursor.use {
                if (it.moveToFirst()) {
                    do {
                        val id = it.getInt(it.getColumnIndexOrThrow("id"))
                        val nombre = it.getString(it.getColumnIndexOrThrow("nombre"))
                        val descripcion = it.getString(it.getColumnIndexOrThrow("descripcion"))
                        val numeroSuscriptores = it.getInt(it.getColumnIndexOrThrow("numeroSuscriptores"))
                        val estaVerificada = it.getInt(it.getColumnIndexOrThrow("estaVerificada")) == 1
                        val correoElectronico = it.getString(it.getColumnIndexOrThrow("correoElectronico"))
                        val cuenta = Cuenta(id, nombre, descripcion, numeroSuscriptores, estaVerificada, correoElectronico)
                        listaCuentas.add(cuenta)
                    } while (it.moveToNext())
                }
            }
        }
        return listaCuentas
    }

    fun obtenerCuentaPorId(cuentaId: Int): Cuenta? {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM ${BaseDeDatosHelper.TABLA_CUENTA} WHERE id = ?"
        val cursor = db.rawQuery(query, arrayOf(cuentaId.toString()))
        var cuenta: Cuenta? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
            val numeroSuscriptores = cursor.getInt(cursor.getColumnIndexOrThrow("numeroSuscriptores"))
            val estaVerificada = cursor.getInt(cursor.getColumnIndexOrThrow("estaVerificada")) == 1
            val correoElectronico = cursor.getString(cursor.getColumnIndexOrThrow("correoElectronico"))
            cuenta = Cuenta(id, nombre, descripcion, numeroSuscriptores, estaVerificada, correoElectronico)
        }
        cursor.close()
        db.close()
        return cuenta
    }

    fun updateCuenta(cuenta: Cuenta) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", cuenta.nombre)
            put("descripcion", cuenta.descripcion)
            put("numeroSuscriptores", cuenta.numeroSuscriptores)
            put("estaVerificada", if (cuenta.estaVerificada) 1 else 0)
            put("correoElectronico", cuenta.correoElectronico)
        }
        val whereClause = "id = ?"
        val whereArgs = arrayOf(cuenta.id.toString())
        db.update(BaseDeDatosHelper.TABLA_CUENTA, values, whereClause, whereArgs)
        db.close()
    }

    fun borrarCuentaPorId(cuentaId: Int) {
        val db = dbHelper.writableDatabase
        val whereClause = "id = ?"
        val whereArgs = arrayOf(cuentaId.toString())
        db.delete(BaseDeDatosHelper.TABLA_CUENTA, whereClause, whereArgs)
        db.close()
    }
}