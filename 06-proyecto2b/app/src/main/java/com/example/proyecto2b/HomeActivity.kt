package com.example.proyecto2b

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Get the username and password from the Intent
        val username1 = intent.getStringExtra("USERNAME1")
        val password = intent.getStringExtra("PASSWORD")
        val username = intent.getStringExtra("USERNAME")

        // Find the TextView by its ID and set the username
        val textViewName = findViewById<TextView>(R.id.id_nombre)
        textViewName.text = username1

        // Delay for 5 seconds and then redirect to HomeActivity1
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, Home1Activity::class.java)
            intent.putExtra("USERNAME", username)
            intent.putExtra("PASSWORD", password)
            intent.putExtra("USERNAME1", username1)
            startActivity(intent)
            finish()
        }, 1000) // 5000 milliseconds = 5 seconds
    }
}