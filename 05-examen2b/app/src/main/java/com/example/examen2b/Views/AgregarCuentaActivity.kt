package com.example.examen2b.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.examen2b.Controller.CuentaCRUD
import com.example.examen2b.Model.Cuenta
import com.example.examen2b.R
import com.google.android.material.snackbar.Snackbar

class AgregarCuentaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_cuenta)

        val inputNombreCuenta = findViewById<EditText>(R.id.input_Nombre_Cuenta)
        val inputDescripcionCuenta = findViewById<EditText>(R.id.input_Descripcion_Cuenta)
        val inputCorreoCuenta = findViewById<EditText>(R.id.input_Correo_Cuenta)
        val inputSuscriptoresCuenta = findViewById<EditText>(R.id.input_Suscriptores_Cuenta)
        val switchVerificadaCuenta = findViewById<Switch>(R.id.sw_Verificada_Cuenta)

        val botonRegresarHome = findViewById<Button>(R.id.btn_Regresar_Home)
        botonRegresarHome.setOnClickListener {
            irActividad(MainActivity::class.java)
        }

        val botonCrearCuenta = findViewById<Button>(R.id.btn_Crear_Cuenta)
        botonCrearCuenta.setOnClickListener {
            val nombreCuenta = inputNombreCuenta.text.toString()
            val descripcionCuenta = inputDescripcionCuenta.text.toString()
            val correoCuenta = inputCorreoCuenta.text.toString()
            val numeroSuscriptores = inputSuscriptoresCuenta.text.toString().toIntOrNull() ?: 0
            val estaVerificada = switchVerificadaCuenta.isChecked

            if (nombreCuenta.isBlank() || correoCuenta.isBlank()) {
                mostrarSnackbarError("Completa todos los campos obligatorios.")
            } else {
                val nuevaCuenta = Cuenta(
                    id = 0,
                    nombre = nombreCuenta,
                    descripcion = descripcionCuenta,
                    numeroSuscriptores = numeroSuscriptores,
                    estaVerificada = estaVerificada,
                    correoElectronico = correoCuenta
                )
                CuentaCRUD(this).crearCuenta(nuevaCuenta)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("NOMBRE_CUENTA_AGREGADA", nombreCuenta)
                startActivity(intent)
            }
        }
    }

    private fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    private fun mostrarSnackbarError(mensaje: String) {
        val rootView: View = findViewById(android.R.id.content)
        Snackbar.make(rootView, mensaje, Snackbar.LENGTH_SHORT).show()
    }
}