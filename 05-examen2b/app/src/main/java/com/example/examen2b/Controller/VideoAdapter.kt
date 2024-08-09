package com.example.examen2b.Controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.examen2b.Model.Video

class VideoAdapter(context: Context, private var videos: MutableList<Video>) :
    ArrayAdapter<Video>(context, 0, videos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listItemView = convertView ?: LayoutInflater.from(context).inflate(
            android.R.layout.simple_list_item_1, parent, false
        )

        val video = getItem(position)
        val textView = listItemView.findViewById<TextView>(android.R.id.text1)
        textView.text = video?.titulo

        return listItemView
    }

    fun actualizarLista(nuevaLista: List<Video>) {
        videos.clear()
        videos.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}