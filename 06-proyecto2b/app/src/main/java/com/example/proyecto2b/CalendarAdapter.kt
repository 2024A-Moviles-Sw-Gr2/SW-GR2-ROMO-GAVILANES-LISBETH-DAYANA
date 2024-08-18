package com.example.proyecto2b

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CalendarAdapter(
    private val daysList: List<CalendarDay>,
    private val onDateClicked: (CalendarDay) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    // Variable to keep track of the selected position
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    // Exposing the selected date to other components
    val selectedDate: String?
        get() = if (selectedPosition != RecyclerView.NO_POSITION) {
            val selectedDay = daysList[selectedPosition]
            "${selectedDay.dayOfMonth}-${selectedDay.month}-${selectedDay.dayOfWeek}"
        } else {
            null
        }

    class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayOfWeek: TextView = itemView.findViewById(R.id.dayOfWeek)
        val dayOfMonth: TextView = itemView.findViewById(R.id.dayOfMonth)
        val month: TextView = itemView.findViewById(R.id.month)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)
        return CalendarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val currentItem = daysList[position]
        holder.dayOfWeek.text = currentItem.dayOfWeek
        holder.dayOfMonth.text = currentItem.dayOfMonth.toString()
        holder.month.text = currentItem.month

        // Update the background or style based on whether the item is selected
        holder.itemView.isSelected = position == selectedPosition
        holder.itemView.setBackgroundResource(
            if (position == selectedPosition) R.drawable.bg_time_slot_selected else android.R.color.transparent
        )

        // Handle item click
        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = holder.adapterPosition

            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)

            onDateClicked(currentItem) // Call the passed-in click listener
        }
    }

    override fun getItemCount() = daysList.size
}