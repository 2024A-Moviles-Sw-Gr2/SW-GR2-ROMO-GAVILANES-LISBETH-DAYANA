package com.example.deber_03.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.deber_03.MainActivity
import com.example.deber_03.R

class AdaptadorCategoria(
    private val contexto: MainActivity,
    private val lista: ArrayList<String>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<AdaptadorCategoria.MyViewHolder>(){
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val btnCategoria: Button

        init {
            btnCategoria = view.findViewById<Button>(R.id.btn_rv_vista_categoria_top_home)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.rv_vista_categorias,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val categoriaActual = this.lista[position]
        holder.btnCategoria.text = categoriaActual.toString()
    }
}