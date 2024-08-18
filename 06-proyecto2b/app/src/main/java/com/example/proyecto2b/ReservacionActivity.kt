package com.example.proyecto2b

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class ReservacionActivity : AppCompatActivity() {
    private lateinit var pageTitle: TextView
    private lateinit var backIcon: ImageView
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: TimeSlotAdapter
    private lateinit var dateAdapter: CalendarAdapter
    private lateinit var canchaNameTextView: TextView
    private var userID: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reservacion)
        dbHelper = DatabaseHelper(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize toolbar components
        pageTitle = findViewById(R.id.pageTitle)
        backIcon = findViewById(R.id.backIcon)
        canchaNameTextView = findViewById(R.id.textViewCanchaName)

        // Get intent extras
        val canchaName = intent.getStringExtra("CANCHA_NAME") ?: ""
        val canchaLocation = intent.getStringExtra("CANCHA_LOCATION") ?: ""
        val canchaImageResId = intent.getIntExtra("CANCHA_IMAGE", R.drawable.cancha1)
        userID = intent.getIntExtra("USER_ID", -1)  // Get the user ID from the intent

        // Set the cancha name in the TextView
        canchaNameTextView.text = canchaName

        // Set the title for the toolbar
        pageTitle.text = "Realizar Reservación"

        // Handle the back button click
        backIcon.setOnClickListener {
            onBackPressed()
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set initial content
        updateContent("Reservas")

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

        // Initialize RecyclerView for time slots
        val recyclerViewTimeSlots = findViewById<RecyclerView>(R.id.recyclerViewTimeSlots)
        recyclerViewTimeSlots.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val timeSlots = listOf(
            TimeSlot("08:00", false),
            TimeSlot("09:00", false),
            TimeSlot("10:00", false),
            TimeSlot("11:00", false),
            TimeSlot("12:00", false),
            TimeSlot("13:00", false),
            TimeSlot("14:00", false),
            TimeSlot("15:00", false),
            TimeSlot("16:00", false),
            TimeSlot("17:00", false),
            TimeSlot("18:00", false)
        )

        adapter = TimeSlotAdapter(timeSlots) { timeSlot ->
            // Handle time slot click
        }
        recyclerViewTimeSlots.adapter = adapter

        // Add date selection to the FrameLayout
        val contentFrame = findViewById<FrameLayout>(R.id.content)
        val recyclerViewDates = RecyclerView(this).apply {
            layoutManager = LinearLayoutManager(this@ReservacionActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = CalendarAdapter(getDiasList()) { selectedDate ->
                // Handle date click (e.g., update selectedDate)
            }.also { dateAdapter = it }
        }
        contentFrame.addView(recyclerViewDates)

        // Equipment and players UI elements
        val checkboxBalon: CheckBox = findViewById(R.id.checkboxBalon)
        val checkboxZapatillas: CheckBox = findViewById(R.id.checkboxZapatillas)
        val checkboxChalecos: CheckBox = findViewById(R.id.checkboxChalecos)
        val checkboxArbitraje: CheckBox = findViewById(R.id.checkboxArbitraje)

        val textViewNumber: TextView = findViewById(R.id.textViewNumber)
        val buttonIncrease: ImageButton = findViewById(R.id.buttonIncrease)
        val buttonDecrease: ImageButton = findViewById(R.id.buttonDecrease)

        buttonIncrease.setOnClickListener {
            val currentNumber = textViewNumber.text.toString().toInt()
            textViewNumber.text = (currentNumber + 1).toString()
        }

        buttonDecrease.setOnClickListener {
            val currentNumber = textViewNumber.text.toString().toInt()
            if (currentNumber > 1) {
                textViewNumber.text = (currentNumber - 1).toString()
            }
        }

        // Confirm reservation
        val buttonConfirm: Button = findViewById(R.id.buttonConfirm)
        buttonConfirm.setOnClickListener {
            val selectedTime = adapter.selectedTimeSlot ?: ""
            val selectedDate = dateAdapter.selectedDate ?: "Selected Date"

            val equipment = Equipment(
                balon = checkboxBalon.isChecked,
                zapatillas = checkboxZapatillas.isChecked,
                chalecos = checkboxChalecos.isChecked,
                arbitraje = checkboxArbitraje.isChecked
            )

            val numPlayers = textViewNumber.text.toString().toInt()

            val reservation = Reservation(
                canchaName = canchaName,
                canchaLocation = canchaLocation,
                date = selectedDate,
                time = selectedTime,
                numberOfPlayers = numPlayers,
                equipment = equipment,
                userId = userID ?: -1 // Use the dynamic user ID
            )

            // Agregar la reservación en la base de datos
            dbHelper.addReservation(reservation)

            // Notificar a Home1Activity que se ha hecho una reservación y pasar la imagen
            val resultIntent = Intent().apply {
                putExtra("RESERVATION_MADE", true)
                putExtra("CANCHA_IMAGE", canchaImageResId) // Incluye la imagen
            }
            setResult(RESULT_OK, resultIntent)

            // Finalizar la actividad actual para volver a Home1Activity
            finish()
        }

        // Handle the "Cancel" button
        val buttonCancel: Button = findViewById(R.id.buttonCancel)
        buttonCancel.setOnClickListener {
            // Navigate to Home1Activity if the user cancels the reservation
            val intent = Intent(this@ReservacionActivity, Home1Activity::class.java)
            startActivity(intent)

            // Optionally, close the current activity so the user can't return to it
            finish()
        }
    }

    private fun updateContent(section: String) {
        // Update the title in the top bar
        pageTitle.text = section
    }

    private fun getDiasList(): List<CalendarDay> {
        return listOf(
            CalendarDay("LUN", 1, "Feb"),
            CalendarDay("MAR", 2, "Feb"),
            CalendarDay("MIE", 3, "Feb"),
            CalendarDay("JUE", 4, "Feb"),
            CalendarDay("VIE", 5, "Feb"),
            CalendarDay("SAB", 6, "Feb"),
            CalendarDay("DOM", 7, "Feb"),
            CalendarDay("LUN", 8, "Feb"),
            CalendarDay("MAR", 9, "Feb"),
            CalendarDay("MIE", 10, "Feb"),
            CalendarDay("JUE", 11, "Feb"),
            CalendarDay("VIE", 12, "Feb"),
            CalendarDay("SAB", 13, "Feb"),
            CalendarDay("DOM", 14, "Feb"),
            CalendarDay("LUN", 15, "Feb")
        )
    }
}