package com.example.examen2b.Views

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.examen2b.Controller.CuentaAdapter
import com.example.examen2b.Controller.CuentaCRUD
import com.example.examen2b.Model.Cuenta
import com.example.examen2b.R
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var listaDeCuentas: MutableList<Cuenta>
    private lateinit var adaptadorCuentas: CuentaAdapter
    private lateinit var listViewCuentas: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewCuentas = findViewById(R.id.lv_Listado_Cuentas)
        val botonAgregarCuenta = findViewById<Button>(R.id.btn_Agregar_Cuenta)

        botonAgregarCuenta.setOnClickListener {
            irActividad(AgregarCuentaActivity::class.java)
        }

        cargarListadoDeCuentas()

        adaptadorCuentas = CuentaAdapter(this, listaDeCuentas)
        listViewCuentas.adapter = adaptadorCuentas

        registerForContextMenu(listViewCuentas)

        listViewCuentas.setOnItemClickListener { _, _, position, _ ->
            val cuentaSeleccionada = listaDeCuentas[position]
            irActividadConID(VerVideosActivity::class.java, cuentaSeleccionada.id)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_cuenta, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        val cuentaSeleccionada = listaDeCuentas[position]

        return when (item.itemId) {
            R.id.mi_VerVideos -> {
                irActividadConID(VerVideosActivity::class.java, cuentaSeleccionada.id)
                true
            }
            R.id.mi_EditarCuenta -> {
                val intent = Intent(this, EditarCuentaActivity::class.java)
                intent.putExtra("CUENTA_ID", cuentaSeleccionada.id)
                startActivityForResult(intent, REQUEST_CODE_EDITAR_CUENTA)
                true
            }
            R.id.mi_EliminarCuenta -> {
                CuentaCRUD(this).borrarCuentaPorId(cuentaSeleccionada.id)
                cargarListadoDeCuentas()
                adaptadorCuentas.actualizarLista(listaDeCuentas)
                mostrarSnackbar("Cuenta Eliminada")
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_EDITAR_CUENTA && resultCode == RESULT_OK) {
            cargarListadoDeCuentas()
            adaptadorCuentas.actualizarLista(listaDeCuentas)
        }
    }

    private fun cargarListadoDeCuentas() {
        listaDeCuentas = CuentaCRUD(this).obtenerTodas().toMutableList()
    }

    private fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    private fun irActividadConID(clase: Class<*>, cuentaId: Int) {
        val intent = Intent(this, clase)
        intent.putExtra("CUENTA_ID", cuentaId)
        startActivity(intent)
    }

    private fun mostrarSnackbar(mensaje: String) {
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, mensaje, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        const val REQUEST_CODE_EDITAR_CUENTA = 1
    }
}