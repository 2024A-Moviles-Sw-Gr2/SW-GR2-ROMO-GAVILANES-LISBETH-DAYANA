package com.example.examen2b.Views

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.examen2b.Controller.VideoCRUD
import com.example.examen2b.Model.Video
import com.example.examen2b.R
import com.google.android.material.snackbar.Snackbar
import java.util.Date

class AgregarVideoActivity : AppCompatActivity() {

    private var cuentaId: Int = -1

    //override fun onCreate(savedInstanceState: Bundle?) {
    //    super.onCreate(savedInstanceState)
    //    setContentView(R.layout.activity_agregar_video)
//
    //    cuentaId = intent.getIntExtra("CUENTA_ID", -1)
//
    //    val inputTitulo = findViewById<EditText>(R.id.input_Nombre_Video)
    //    val inputDuracion = findViewById<EditText>(R.id.input_Duracion_Video)
    //    val inputVistas = findViewById<EditText>(R.id.input_Vistas_Video)
    //    val inputLikes = findViewById<EditText>(R.id.input_Likes_Video)
    //    val botonAgregar = findViewById<Button>(R.id.btn_Crear_Video)
    //    val botonRegresar = findViewById<Button>(R.id.btn_Regresar_Listado_Videos)
//
    //    botonAgregar.setOnClickListener {
    //        val titulo = inputTitulo.text.toString()
    //        val duracion = inputDuracion.text.toString().toDoubleOrNull() ?: 0.0
    //        val vistas = inputVistas.text.toString().toIntOrNull() ?: 0
    //        val likes = inputLikes.text.toString().toIntOrNull() ?: 0
//
    //        if (titulo.isNotEmpty() && duracion > 0) {
    //            val nuevoVideo = Video(
    //                cuentaId = cuentaId,
    //                titulo = titulo,
    //                duracion = duracion,
    //                numeroVistas = vistas,
    //                fechaSubido = Date(),
    //                numeroLikes = likes,
    //                latitud = -0.18,
    //                longitud = 0.15
    //            )
    //            VideoCRUD(this).crearVideo(nuevoVideo)
    //            mostrarSnackbar("Video agregado con éxito")
    //            finish()
    //        } else {
    //            mostrarSnackbar("Por favor, completa todos los campos obligatorios")
    //        }
    //    }
//
    //    botonRegresar.setOnClickListener {
    //        finish()
    //    }
    //}

    private val LOCATION_PERMISSION_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_video)

        cuentaId = intent.getIntExtra("CUENTA_ID", -1)

        val inputTitulo = findViewById<EditText>(R.id.input_Nombre_Video)
        val inputDuracion = findViewById<EditText>(R.id.input_Duracion_Video)
        val inputVistas = findViewById<EditText>(R.id.input_Vistas_Video)
        val inputLikes = findViewById<EditText>(R.id.input_Likes_Video)
        val botonAgregar = findViewById<Button>(R.id.btn_Crear_Video)
        val botonRegresar = findViewById<Button>(R.id.btn_Regresar_Listado_Videos)

        // Request location permission if not already granted
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }

        botonAgregar.setOnClickListener {
            val titulo = inputTitulo.text.toString()
            val duracion = inputDuracion.text.toString().toDoubleOrNull() ?: 0.0
            val vistas = inputVistas.text.toString().toIntOrNull() ?: 0
            val likes = inputLikes.text.toString().toIntOrNull() ?: 0

            if (titulo.isNotEmpty() && duracion > 0) {
                val ubicacion = obtenerUbicacionActual()
                val latitud = ubicacion?.first ?: 0.0
                val longitud = ubicacion?.second ?: 0.0

                val nuevoVideo = Video(
                    cuentaId = cuentaId,
                    titulo = titulo,
                    duracion = duracion,
                    numeroVistas = vistas,
                    fechaSubido = Date(),
                    numeroLikes = likes,
                    latitud = latitud,
                    longitud = longitud
                )
                VideoCRUD(this).crearVideo(nuevoVideo)
                mostrarSnackbar("Video agregado con éxito")
                finish()
            } else {
                mostrarSnackbar("Por favor, completa todos los campos obligatorios")
            }
        }

        botonRegresar.setOnClickListener {
            finish()
        }
    }

    private fun obtenerUbicacionActual(): Pair<Double, Double>? {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (isGPSEnabled) {
                val location: Location? =
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location != null) {
                    return Pair(location.latitude, location.longitude)
                }
            }
        }
        return null
    }


    private fun mostrarSnackbar(mensaje: String) {
        val rootView = findViewById<android.view.View>(android.R.id.content)
        Snackbar.make(rootView, mensaje, Snackbar.LENGTH_SHORT).show()
    }
}