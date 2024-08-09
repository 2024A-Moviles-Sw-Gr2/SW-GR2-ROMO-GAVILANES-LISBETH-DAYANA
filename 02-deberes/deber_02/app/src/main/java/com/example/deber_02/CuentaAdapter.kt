package com.example.deber_02

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CuentaAdapter(context: Context, private val cuentas: List<Cuenta>) :
    ArrayAdapter<Cuenta>(context, 0, cuentas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val cuenta = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = cuenta?.nombre
        return view
    }
}