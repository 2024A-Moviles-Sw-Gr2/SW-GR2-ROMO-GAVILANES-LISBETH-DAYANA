package com.example.deber_02

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class VideoAdapter(context: Context, private val videos: MutableList<Video>?) :
    ArrayAdapter<Video>(context, 0, videos!!) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val video = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = video?.titulo
        return view
    }
}