package com.example.proyecto2b

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CanchaAdapter(private val canchaList: List<Cancha>, private val context: Context, private val userId: Int) : RecyclerView.Adapter<CanchaAdapter.CanchaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CanchaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_cancha, parent, false)
        return CanchaViewHolder(itemView, context, userId)
    }

    override fun onBindViewHolder(holder: CanchaViewHolder, position: Int) {
        val cancha = canchaList[position]
        holder.bind(cancha)
    }

    override fun getItemCount(): Int = canchaList.size

    class CanchaViewHolder(itemView: View, private val context: Context, private val userId: Int) : RecyclerView.ViewHolder(itemView) {
        private val canchaName: TextView = itemView.findViewById(R.id.textViewCanchaName)
        private val canchaLocation: TextView = itemView.findViewById(R.id.textViewLocation)
        private val canchaDistance: TextView = itemView.findViewById(R.id.textViewDistance)
        private val canchaImage: ImageView = itemView.findViewById(R.id.imageViewCancha)
        private val iconImage: ImageView = itemView.findViewById(R.id.imageViewIcon)
        private val reserveButton: Button = itemView.findViewById(R.id.buttonReserve)

        fun bind(cancha: Cancha) {
            canchaName.text = cancha.name
            canchaLocation.text = cancha.location
            canchaDistance.text = cancha.distance

            // Set the main image from the model
            canchaImage.setImageResource(cancha.imageResId)

            // Set the image for the "icon" based on the iconResId from the model
            iconImage.setImageResource(cancha.iconResId)

            reserveButton.setOnClickListener {
                val intent = Intent(context, ReservacionActivity::class.java).apply {
                    putExtra("CANCHA_NAME", cancha.name)
                    putExtra("CANCHA_LOCATION", cancha.location)
                    putExtra("CANCHA_IMAGE", cancha.imageResId) // Pass the image resource ID to the reservation activity
                    putExtra("USER_ID", userId)  // Pass the user ID to the reservation activity
                }
                context.startActivity(intent)
            }
        }
    }
}