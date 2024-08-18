package com.example.proyecto2b

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DeporteAdapter(private val deporteList: List<Deporte>) : RecyclerView.Adapter<DeporteAdapter.DeporteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeporteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_deporte, parent, false)
        return DeporteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DeporteViewHolder, position: Int) {
        val deporte = deporteList[position]
        holder.bind(deporte)
    }

    override fun getItemCount(): Int = deporteList.size

    class DeporteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val deporteImage: ImageView = itemView.findViewById(R.id.deporteImage)
        private val deporteName: TextView = itemView.findViewById(R.id.deporteName)
        private val deporteIcon: ImageView = itemView.findViewById(R.id.deporteIcon)

        fun bind(deporte: Deporte) {
            deporteImage.setImageResource(deporte.imageResId)
            deporteName.text = deporte.name
            deporteIcon.setImageResource(deporte.iconResId)
        }
    }
}