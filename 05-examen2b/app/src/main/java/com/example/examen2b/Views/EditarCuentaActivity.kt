package com.example.examen2b.Views

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.examen2b.Controller.CuentaCRUD
import com.example.examen2b.Model.Cuenta
import com.example.examen2b.R
import com.google.android.material.snackbar.Snackbar

class EditarCuentaActivity : AppCompatActivity() {
    private var cuentaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_cuenta)

        val inputNombre = findViewById<EditText>(R.id.input_Editar_Nombre_Cuenta)
        val inputDescripcion = findViewById<EditText>(R.id.input_Editar_Descripcion_Cuenta)
        val inputSuscriptores = findViewById<EditText>(R.id.input_Editar_Suscriptores_Cuenta)
        val inputCorreo = findViewById<EditText>(R.id.input_Editar_Correo_Cuenta)
        val switchVerificada = findViewById<Switch>(R.id.sw_Verificada_Cuenta2)
        val botonActualizar = findViewById<Button>(R.id.btn_Actualizar_Cuenta)
        val botonRegresar = findViewById<Button>(R.id.btn_Regresar_Editar_Cuenta)

        cuentaId = intent.getIntExtra("CUENTA_ID", -1)
        if (cuentaId != -1) {
            val cuenta = CuentaCRUD(this).obtenerCuentaPorId(cuentaId)
            cuenta?.let {
                inputNombre.setText(it.nombre)
                inputDescripcion.setText(it.descripcion)
                inputSuscriptores.setText(it.numeroSuscriptores.toString())
                inputCorreo.setText(it.correoElectronico)
                switchVerificada.isChecked = it.estaVerificada
            }
        }

        botonActualizar.setOnClickListener {
            val nombre = inputNombre.text.toString()
            val descripcion = inputDescripcion.text.toString()
            val suscriptores = inputSuscriptores.text.toString().toIntOrNull() ?: 0
            val correo = inputCorreo.text.toString()
            val verificada = switchVerificada.isChecked

            if (nombre.isNotEmpty() && correo.isNotEmpty()) {
                val cuentaActualizada = Cuenta(
                    id = cuentaId,
                    nombre = nombre,
                    descripcion = descripcion,
                    numeroSuscriptores = suscriptores,
                    estaVerificada = verificada,
                    correoElectronico = correo
                )
                CuentaCRUD(this).updateCuenta(cuentaActualizada)
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                mostrarSnackbar("Por favor, completa todos los campos")
            }
        }

        botonRegresar.setOnClickListener {
            finish()
        }
    }

    private fun mostrarSnackbar(mensaje: String) {
        val rootView = findViewById<android.view.View>(android.R.id.content)
        Snackbar.make(rootView, mensaje, Snackbar.LENGTH_SHORT).show()
    }
}