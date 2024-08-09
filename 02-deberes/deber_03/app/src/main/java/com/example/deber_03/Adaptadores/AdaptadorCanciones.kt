package com.example.deber_03.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deber_03.MainActivity
import com.example.deber_03.Song
import com.example.deber_03.R

class AdaptadorCanciones(
    private val contexto: MainActivity,
    private val lista: List<Song>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<AdaptadorCanciones.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txt_rv_vista_nombre_volver_escuchar)
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.rv_vista_canciones, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val song = this.lista[position]
        holder.txtNombre.text = song.name
        holder.imageView.setImageResource(song.imageResId)
    }
}