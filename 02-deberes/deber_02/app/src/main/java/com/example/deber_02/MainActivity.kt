package com.example.deber_02

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    val arreglo = BaseDatosMemoria.arregloCuentas
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_videos)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val listViewCuenta = findViewById<ListView>(R.id.lv_list_view_videos)
        val adaptador = CuentaAdapter(this, arreglo)
        listViewCuenta.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonCrearCuenta = findViewById<Button>(R.id.btn_anadir_list_view_videos)
        botonCrearCuenta.setOnClickListener {
            abrirDialogoCrear()
        }
        registerForContextMenu(listViewCuenta)
    }

    private fun abrirDialogoCrear() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Crear Cuenta")

        // Crear el layout del diálogo en código
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(16, 16, 16, 16)

        val editTextId = EditText(this)
        editTextId.hint = "ID"
        editTextId.inputType = InputType.TYPE_CLASS_NUMBER
        layout.addView(editTextId)

        val editTextNombre = EditText(this)
        editTextNombre.hint = "Nombre"
        layout.addView(editTextNombre)

        val editTextDescripcion = EditText(this)
        editTextDescripcion.hint = "Descripción"
        layout.addView(editTextDescripcion)

        val editTextNumeroSuscriptores = EditText(this)
        editTextNumeroSuscriptores.hint = "Número de Suscriptores"
        editTextNumeroSuscriptores.inputType = InputType.TYPE_CLASS_NUMBER
        layout.addView(editTextNumeroSuscriptores)

        val editTextCorreo = EditText(this)
        editTextCorreo.hint = "Correo Electrónico"
        layout.addView(editTextCorreo)

        val checkBoxVerificada = CheckBox(this)
        checkBoxVerificada.text = "Cuenta Verificada"
        layout.addView(checkBoxVerificada)

        builder.setView(layout)

        builder.setPositiveButton(
            "Guardar",
            DialogInterface.OnClickListener { dialog, which ->
                val id = editTextId.text.toString().toIntOrNull() ?: -1
                val nombre = editTextNombre.text.toString()
                val descripcion = editTextDescripcion.text.toString()
                val numeroSuscriptores = editTextNumeroSuscriptores.text.toString().toIntOrNull() ?: 0
                val correo = editTextCorreo.text.toString()
                val verificada = checkBoxVerificada.isChecked

                val nuevaCuenta = Cuenta(id, nombre, descripcion, numeroSuscriptores, verificada, correo)
                arreglo.add(nuevaCuenta)

                // Actualizar el ListView
                val listViewCuenta = findViewById<ListView>(R.id.lv_list_view_videos)
                val adaptador = CuentaAdapter(this, arreglo)
                listViewCuenta.adapter = adaptador
                adaptador.notifyDataSetChanged()
                mostrarSnackbar("Cuenta creada: ${nuevaCuenta.nombre}")
            }
        )
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
    }

    fun irActividad(clase: Class<*>, cuenta: Cuenta) {
        val intent = Intent(this, clase)
        intent.putExtra("EXTRA_CUENTA", cuenta)
        startActivityForResult(intent, REQUEST_CODE_VISTA_VIDEOS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_VISTA_VIDEOS && resultCode == RESULT_OK) {
            val cuentaActualizada = data?.getParcelableExtra<Cuenta>("EXTRA_CUENTA_ACTUALIZADA")
            if (cuentaActualizada != null) {
                val posicion = arreglo.indexOfFirst { it.id == cuentaActualizada.id }
                if (posicion != -1) {
                    arreglo[posicion] = cuentaActualizada
                    val listViewCuenta = findViewById<ListView>(R.id.lv_list_view_videos)
                    val adaptador = CuentaAdapter(this, arreglo)
                    listViewCuenta.adapter = adaptador
                    adaptador.notifyDataSetChanged()
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE_VISTA_VIDEOS = 1
    }

    var posicionItemSeleccionado = -1
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_cuenta, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(
        item: MenuItem
    ): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                abrirDialogoEditar()
                return true
            }

            R.id.mi_eliminar -> {
                abrirDialogoEliminar()
                return true
            }

            R.id.ver_videos_id -> {
                mostrarSnackbar("Ver videos")
                val cuentaSeleccionada = arreglo[posicionItemSeleccionado]
                irActividad(VistaVideos::class.java, cuentaSeleccionada)
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogoEditar() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Cuenta")

        // Crear el layout del diálogo en código
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(16, 16, 16, 16)

        val editTextNombre = EditText(this)
        editTextNombre.hint = "Nombre"
        layout.addView(editTextNombre)

        val editTextDescripcion = EditText(this)
        editTextDescripcion.hint = "Descripción"
        layout.addView(editTextDescripcion)

        val editTextNumeroSuscriptores = EditText(this)
        editTextNumeroSuscriptores.hint = "Número de Suscriptores"
        editTextNumeroSuscriptores.inputType = InputType.TYPE_CLASS_NUMBER
        layout.addView(editTextNumeroSuscriptores)

        val editTextCorreo = EditText(this)
        editTextCorreo.hint = "Correo Electrónico"
        layout.addView(editTextCorreo)

        val checkBoxVerificada = CheckBox(this)
        checkBoxVerificada.text = "Cuenta Verificada"
        layout.addView(checkBoxVerificada)

        builder.setView(layout)

        val cuenta = BaseDatosMemoria.arregloCuentas[posicionItemSeleccionado]
        editTextNombre.setText(cuenta.nombre)
        editTextDescripcion.setText(cuenta.descripcion)
        editTextNumeroSuscriptores.setText(cuenta.numeroSuscriptores.toString())
        editTextCorreo.setText(cuenta.correoElectronico)
        checkBoxVerificada.isChecked = cuenta.estaVerificada

        builder.setPositiveButton(
            "Guardar",
            DialogInterface.OnClickListener { dialog, which ->
                cuenta.nombre = editTextNombre.text.toString()
                cuenta.descripcion = editTextDescripcion.text.toString()
                cuenta.numeroSuscriptores = editTextNumeroSuscriptores.text.toString().toIntOrNull()
                    ?: cuenta.numeroSuscriptores
                cuenta.correoElectronico = editTextCorreo.text.toString()
                cuenta.estaVerificada = checkBoxVerificada.isChecked

                // Actualizar el ListView
                val listViewCuenta = findViewById<ListView>(R.id.lv_list_view_videos)
                val adaptador = CuentaAdapter(this, BaseDatosMemoria.arregloCuentas)
                listViewCuenta.adapter = adaptador
                adaptador.notifyDataSetChanged()
                mostrarSnackbar("Cuenta editada: ${cuenta.nombre}")
            }
        )
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
    }

    fun abrirDialogoEliminar() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea Eliminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->
                arreglo.removeAt(posicionItemSeleccionado)
                val listViewCuenta = findViewById<ListView>(R.id.lv_list_view_videos)
                val adaptador = CuentaAdapter(this, arreglo)
                listViewCuenta.adapter = adaptador
                adaptador.notifyDataSetChanged()
                mostrarSnackbar("Eliminado $posicionItemSeleccionado")
            }
        )
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_videos),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }
}