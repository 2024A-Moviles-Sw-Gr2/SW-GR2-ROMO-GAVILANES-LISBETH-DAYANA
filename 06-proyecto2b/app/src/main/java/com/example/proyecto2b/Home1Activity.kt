package com.example.proyecto2b

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home1Activity : AppCompatActivity() {

    private lateinit var pageTitle: TextView
    private lateinit var logoutIcon: ImageView
    private var username1: String? = null
    private var password: String? = null
    private var username: String? = null
    private var userId: Int? = null
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home1)

        // Initialize DatabaseHelper
        dbHelper = DatabaseHelper(this)

        // Get the username and password from the Intent
        username1 = intent.getStringExtra("USERNAME1")
        password = intent.getStringExtra("PASSWORD")
        username = intent.getStringExtra("USERNAME")

        // Get the user ID based on the username and password
        userId = dbHelper.getUserId(username ?: "", password ?: "")

        pageTitle = findViewById(R.id.pageTitle)
        logoutIcon = findViewById(R.id.logoutIcon)

        logoutIcon.setOnClickListener {
            // Handle logout action
            logoutUser()
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set initial content
        updateContent("Inicio")

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    updateContent("Inicio")
                    true
                }

                R.id.search -> {
                    updateContent("Buscar")
                    true
                }

                R.id.calendar -> {
                    updateContent("Reservas")
                    true
                }

                R.id.profile -> {
                    updateContent("Perfil")
                    true
                }

                else -> false
            }
        }

        // Llama a updateUI para configurar la pantalla inicial
        updateUI(R.drawable.cancha1)
    }

    override fun onResume() {
        super.onResume()
        // Llama a updateUI para actualizar la pantalla cuando se regrese a esta actividad
        updateUI(R.drawable.cancha1)
    }

    private fun updateUI(canchaImageResId: Int) {
        // Establece el contenido inicial de la pantalla
        updateContent("Inicio")

        // Muestra la última reservación
        displayLastReservation(canchaImageResId)
    }

    private fun updateContent(section: String) {
        pageTitle.text = section

        // Clear previous content views
        val contentView = findViewById<FrameLayout>(R.id.content)
        contentView.removeAllViews()

        val contentView1 = findViewById<FrameLayout>(R.id.content1)
        contentView1.removeAllViews()

        // Create RecyclerView for Cancha
        val recyclerViewCancha = RecyclerView(this).apply {
            layoutManager = LinearLayoutManager(this@Home1Activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = CanchaAdapter(getCanchaList(), this@Home1Activity, userId ?: -1)
        }

        // Add the RecyclerView to the contentView
        contentView.addView(recyclerViewCancha)

        // Create RecyclerView for Deporte
        val recyclerViewDeporte = RecyclerView(this).apply {
            layoutManager = LinearLayoutManager(this@Home1Activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = DeporteAdapter(getDeporteList())
        }

        // Add the RecyclerView to the contentView1
        contentView1.addView(recyclerViewDeporte)
    }

    private fun getCanchaList(): List<Cancha> {
        return listOf(
            Cancha("Cancha 1", "Parque La Carolina", "100 m", R.drawable.cancha1, R.drawable.f),
            Cancha("Cancha 2", "Parque La Carolina", "110 m", R.drawable.cancha2, R.drawable.b),
            Cancha("Cancha 3", "Parque La Carolina", "120 m", R.drawable.cancha3, R.drawable.t),
            Cancha("Cancha 4", "Parque La Carolina", "130 m", R.drawable.cancha4, R.drawable.v)
            // Add more canchas as needed
        )
    }

    private fun getDeporteList(): List<Deporte> {
        return listOf(
            Deporte("Fútbol", R.drawable.futbol, R.drawable.f),
            Deporte("Básquet", R.drawable.basquet, R.drawable.b),
            Deporte("Tenis", R.drawable.tenis, R.drawable.t),
            Deporte("Vóley", R.drawable.voley, R.drawable.v)
            // Add more sports as needed
        )
    }

    private fun logoutUser() {
        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // Redirect to the login screen (MainActivity in this case)
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish() // Close the current activity
    }

    private fun displayLastReservation(canchaImageResId: Int) {
        val lastReservation = dbHelper.getLastReservation(username ?: "", password ?: "")

        lastReservation?.let { reservation ->
            val lastReservationCard = findViewById<View>(R.id.lastReservationCard)
            val canchaNameTextView = lastReservationCard.findViewById<TextView>(R.id.textViewCanchaName)
            val canchaLocationTextView = lastReservationCard.findViewById<TextView>(R.id.textViewCanchaLocation)
            val canchaDateTextView = lastReservationCard.findViewById<TextView>(R.id.textViewFecha)
            val canchaTimeTextView = lastReservationCard.findViewById<TextView>(R.id.textViewHora)
            val canchaImageView = lastReservationCard.findViewById<ImageView>(R.id.imageViewCancha)

            canchaNameTextView.text = reservation.canchaName
            canchaLocationTextView.text = reservation.canchaLocation
            canchaDateTextView.text = reservation.date
            canchaTimeTextView.text = reservation.time
            canchaImageView.setImageResource(canchaImageResId) // Actualiza la imagen
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_RESERVATION && resultCode == RESULT_OK) {
            val reservationMade = data?.getBooleanExtra("RESERVATION_MADE", false) ?: false
            if (reservationMade) {
                val canchaImageResId = data?.getIntExtra("CANCHA_IMAGE", R.drawable.cancha1) ?: R.drawable.cancha1

                // Llama a los métodos necesarios para actualizar la UI
                updateUI(canchaImageResId) // Pasa la imagen a updateUI para actualizar la vista
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_RESERVATION = 1
    }
}