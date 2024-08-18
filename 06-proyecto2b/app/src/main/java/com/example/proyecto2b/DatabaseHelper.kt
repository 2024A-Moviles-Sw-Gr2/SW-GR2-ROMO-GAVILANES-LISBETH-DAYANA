package com.example.proyecto2b

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 5
        private const val DATABASE_NAME = "user.db"

        private const val TABLE_USER = "user"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE_APELLIDO = "nombreApellido"
        private const val COLUMN_TELEFONO = "telefono"
        private const val COLUMN_CEDULA = "cedula"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"

        private const val TABLE_RESERVATION = "reservation"
        private const val COLUMN_RESERVATION_ID = "reservationId"
        private const val COLUMN_CANCHA_NAME = "canchaName"
        private const val COLUMN_LOCATION = "location"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_TIME = "time"
        private const val COLUMN_EQUIPMENT = "equipment"
        private const val COLUMN_NUM_PLAYERS = "numPlayers"
        private const val COLUMN_USER_ID = "userId"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUserTable = ("CREATE TABLE " + TABLE_USER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOMBRE_APELLIDO + " TEXT,"
                + COLUMN_TELEFONO + " TEXT,"
                + COLUMN_CEDULA + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")")

        val createReservationTable = ("CREATE TABLE " + TABLE_RESERVATION + "("
                + COLUMN_RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CANCHA_NAME + " TEXT,"
                + COLUMN_LOCATION + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_TIME + " TEXT,"
                + COLUMN_EQUIPMENT + " TEXT,"
                + COLUMN_NUM_PLAYERS + " INTEGER,"
                + COLUMN_USER_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID + "))")

        db.execSQL(createUserTable)
        db.execSQL(createReservationTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_RESERVATION")
        onCreate(db)
    }

    fun addUser(user: User): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NOMBRE_APELLIDO, user.nombreApellido)
            put(COLUMN_TELEFONO, user.telefono)
            put(COLUMN_CEDULA, user.cedula)
            put(COLUMN_EMAIL, user.email)
            put(COLUMN_USERNAME, user.username)
            put(COLUMN_PASSWORD, user.password)
        }
        return db.insert(TABLE_USER, null, contentValues)
    }

    fun addReservation(reservation: Reservation): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_CANCHA_NAME, reservation.canchaName)
            put(COLUMN_LOCATION, reservation.canchaLocation)
            put(COLUMN_DATE, reservation.date)
            put(COLUMN_TIME, reservation.time)
            put(COLUMN_EQUIPMENT, serializeEquipment(reservation.equipment))
            put(COLUMN_NUM_PLAYERS, reservation.numberOfPlayers)
            put(COLUMN_USER_ID, reservation.userId)
        }
        return db.insert(TABLE_RESERVATION, null, contentValues)
    }

    private fun serializeEquipment(equipment: Equipment): String {
        val selectedEquipment = mutableListOf<String>()
        if (equipment.balon) selectedEquipment.add("balon")
        if (equipment.zapatillas) selectedEquipment.add("zapatillas")
        if (equipment.chalecos) selectedEquipment.add("chalecos")
        if (equipment.arbitraje) selectedEquipment.add("arbitraje")
        return selectedEquipment.joinToString(",")
    }

    private fun deserializeEquipment(equipmentStr: String): Equipment {
        val equipmentSet = equipmentStr.split(",").toSet()
        return Equipment(
            balon = "balon" in equipmentSet,
            zapatillas = "zapatillas" in equipmentSet,
            chalecos = "chalecos" in equipmentSet,
            arbitraje = "arbitraje" in equipmentSet
        )
    }

    fun checkUser(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(TABLE_USER, arrayOf(COLUMN_ID),
            "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(email, password), null, null, null)
        val count = cursor.count
        cursor.close()
        return count > 0
    }

    fun getUserId(email: String, password: String): Int? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USER, arrayOf(COLUMN_ID),
            "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(email, password), null, null, null
        )

        return if (cursor.moveToFirst()) {
            val userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            cursor.close()
            userId
        } else {
            cursor.close()
            null
        }
    }

    fun getUserName(email: String, password: String): String? {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(TABLE_USER, arrayOf(COLUMN_USERNAME),
            "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(email, password), null, null, null)

        return if (cursor.moveToFirst()) {
            val userName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME))
            cursor.close()
            userName
        } else {
            cursor.close()
            null
        }
    }

    fun getLastReservation(email: String, password: String): Reservation? {
        val db = this.readableDatabase
        var cursorUser: Cursor? = null
        var cursorReservation: Cursor? = null

        return try {
            // Primero, obtener el ID del usuario basado en email y password
            cursorUser = db.query(
                TABLE_USER, arrayOf(COLUMN_ID),
                "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?",
                arrayOf(email, password), null, null, null
            )

            if (cursorUser.moveToFirst()) {
                val userId = cursorUser.getInt(cursorUser.getColumnIndexOrThrow(COLUMN_ID))

                // Ahora, obtener la última reservación para este usuario
                cursorReservation = db.rawQuery(
                    "SELECT * FROM $TABLE_RESERVATION WHERE $COLUMN_USER_ID = ? ORDER BY $COLUMN_RESERVATION_ID DESC LIMIT 1",
                    arrayOf(userId.toString())
                )

                if (cursorReservation.moveToFirst()) {
                    val reservation = Reservation(
                        canchaName = cursorReservation.getString(cursorReservation.getColumnIndexOrThrow(COLUMN_CANCHA_NAME)),
                        canchaLocation = cursorReservation.getString(cursorReservation.getColumnIndexOrThrow(COLUMN_LOCATION)),
                        date = cursorReservation.getString(cursorReservation.getColumnIndexOrThrow(COLUMN_DATE)),
                        time = cursorReservation.getString(cursorReservation.getColumnIndexOrThrow(COLUMN_TIME)),
                        numberOfPlayers = cursorReservation.getInt(cursorReservation.getColumnIndexOrThrow(COLUMN_NUM_PLAYERS)),
                        equipment = deserializeEquipment(cursorReservation.getString(cursorReservation.getColumnIndexOrThrow(COLUMN_EQUIPMENT))),
                        userId = userId
                    )

                    // Imprimir la reservación en el logcat
                    Log.d("Reservation", "Cancha: ${reservation.canchaName}, Location: ${reservation.canchaLocation}, Date: ${reservation.date}, Time: ${reservation.time}, Players: ${reservation.numberOfPlayers}")

                    return reservation
                }
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            cursorUser?.close()
            cursorReservation?.close()
        }
    }
}