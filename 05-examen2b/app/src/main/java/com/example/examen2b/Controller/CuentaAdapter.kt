package com.example.examen2b.Controller
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.examen2b.Model.Cuenta

class CuentaAdapter(context: Context, val cuentas: MutableList<Cuenta>) : ArrayAdapter<Cuenta>(context, 0, cuentas) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        }
        val currentCuenta = getItem(position)
        val textView = listItemView?.findViewById<TextView>(android.R.id.text1)
        textView?.text = currentCuenta?.nombre
        return listItemView!!
    }

    fun actualizarLista(nuevaLista: List<Cuenta>) {
        cuentas.clear()
        cuentas.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}