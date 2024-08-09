package com.example.deber_03.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deber_03.MainActivity
import com.example.deber_03.Video
import com.example.deber_03.R

class AdaptadorVideosMusicalesParaTi(
    private val contexto: MainActivity,
    private val lista: List<Video>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<AdaptadorVideosMusicalesParaTi.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txt_vista_nombre_videos_recomendados)
        val imageView: ImageView = view.findViewById(R.id.imageViewVideo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.rv_vista_videos_musicales_para_ti, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val video = this.lista[position]
        holder.txtNombre.text = video.name
        holder.imageView.setImageResource(video.imageResId)
    }
}