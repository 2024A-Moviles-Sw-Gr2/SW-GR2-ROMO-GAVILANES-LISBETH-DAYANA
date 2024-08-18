package com.example.proyecto2b

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TimeSlotAdapter(
    private val timeSlots: List<TimeSlot>,
    private val onTimeSlotClicked: (TimeSlot) -> Unit
) : RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder>() {

    // This variable tracks the selected time slot
    var selectedTimeSlot: String? = null
        private set

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSlotViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_time_slot, parent, false)
        return TimeSlotViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeSlotViewHolder, position: Int) {
        val timeSlot = timeSlots[position]
        holder.bind(timeSlot, position == selectedPosition)

        holder.itemView.setOnClickListener {
            val currentPosition = holder.adapterPosition

            if (currentPosition != RecyclerView.NO_POSITION) {
                // Update selected position and notify adapter to refresh items
                val previousPosition = selectedPosition
                selectedPosition = currentPosition
                selectedTimeSlot = timeSlot.time // Update selected time slot
                notifyItemChanged(previousPosition)
                notifyItemChanged(currentPosition)

                // Invoke the callback with the clicked time slot
                onTimeSlotClicked(timeSlot)
            }
        }
    }

    override fun getItemCount() = timeSlots.size

    inner class TimeSlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTimeSlot: TextView = itemView.findViewById(R.id.textViewTimeSlot)

        fun bind(timeSlot: TimeSlot, isSelected: Boolean) {
            textViewTimeSlot.text = timeSlot.time
            itemView.isEnabled = !timeSlot.isReserved
            itemView.isSelected = isSelected // Highlight the selected item

            // Change the background based on whether the item is selected or not
            if (isSelected) {
                itemView.setBackgroundResource(R.drawable.bg_time_slot_reserved) // Custom background for selected item
            } else {
                itemView.setBackgroundResource(R.drawable.bg_time_slot_default) // Default background
            }
        }
    }
}