package com.example.proyecto2b

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHelper = DatabaseHelper(this)

        val editTextNombreApellido = findViewById<EditText>(R.id.editTextNombreApellido)
        val editTextTelefono = findViewById<EditText>(R.id.editTextTelefono)
        val editTextCedula = findViewById<EditText>(R.id.editTextCedula)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)  // New email input
        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val editTextRepeatPassword = findViewById<EditText>(R.id.editTextRepeatPassword)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        buttonRegister.setOnClickListener {
            val nombreApellido = editTextNombreApellido.text.toString().trim()
            val telefono = editTextTelefono.text.toString().trim()
            val cedula = editTextCedula.text.toString().trim()
            val email = editTextEmail.text.toString().trim()  // Get email input
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val repeatPassword = editTextRepeatPassword.text.toString().trim()

            if (nombreApellido.isNotEmpty() && telefono.isNotEmpty() && cedula.isNotEmpty() &&
                email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()) {
                if (password == repeatPassword) {
                    val user = User(0, nombreApellido, telefono, cedula, email, username, password)
                    val result = dbHelper.addUser(user)

                    if (result > 0) {
                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        finish() // Go back to login screen
                    } else {
                        Toast.makeText(this, "Registro fallido", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}