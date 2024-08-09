package com.example.deber_02

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import android.content.DialogInterface
import android.content.Intent
import android.text.InputType
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.LinearLayout
import java.text.SimpleDateFormat

class VistaVideos : AppCompatActivity() {

    private lateinit var arregloVideos: MutableList<Video>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_videos)

        val textNombreCuenta = findViewById<TextView>(R.id.tv_nombre_cuenta)
        val cuenta = intent.getParcelableExtra<Cuenta>("EXTRA_CUENTA")

        if (cuenta != null) {
            arregloVideos = cuenta.videos
            textNombreCuenta.text = cuenta.nombre
        } else {
            arregloVideos = mutableListOf()
        }

        val listViewVideo = findViewById<ListView>(R.id.lv_list_view_videos)
        val adaptador = VideoAdapter(this, arregloVideos)
        listViewVideo.adapter = adaptador

        val botonCrearVideo = findViewById<Button>(R.id.btn_anadir_list_view_videos)
        botonCrearVideo.setOnClickListener {
            abrirDialogoCrear()
        }

        registerForContextMenu(listViewVideo)
    }

    override fun onBackPressed() {
        val cuenta = intent.getParcelableExtra<Cuenta>("EXTRA_CUENTA")
        if (cuenta != null) {
            cuenta.videos = arregloVideos
            val resultIntent = Intent()
            resultIntent.putExtra("EXTRA_CUENTA_ACTUALIZADA", cuenta)
            setResult(RESULT_OK, resultIntent)
        }
        super.onBackPressed()
    }

    fun abrirDialogoCrear() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Crear Video")

        // Crear el layout del diálogo en código
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(16, 16, 16, 16)

        val editTextId = EditText(this)
        editTextId.hint = "ID"
        editTextId.inputType = InputType.TYPE_CLASS_NUMBER
        layout.addView(editTextId)

        val editTextTitulo = EditText(this)
        editTextTitulo.hint = "Título"
        layout.addView(editTextTitulo)

        val editTextDuracion = EditText(this)
        editTextDuracion.hint = "Duración (min)"
        editTextDuracion.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        layout.addView(editTextDuracion)

        val editTextNumeroVistas = EditText(this)
        editTextNumeroVistas.hint = "Número de Vistas"
        editTextNumeroVistas.inputType = InputType.TYPE_CLASS_NUMBER
        layout.addView(editTextNumeroVistas)

        val editTextFechaSubido = EditText(this)
        editTextFechaSubido.hint = "Fecha Subida (dd/MM/yyyy)"
        layout.addView(editTextFechaSubido)

        val editTextNumeroLikes = EditText(this)
        editTextNumeroLikes.hint = "Número de Likes"
        editTextNumeroLikes.inputType = InputType.TYPE_CLASS_NUMBER
        layout.addView(editTextNumeroLikes)

        builder.setView(layout)

        builder.setPositiveButton(
            "Guardar",
            DialogInterface.OnClickListener { dialog, which ->
                val id = editTextId.text.toString().toIntOrNull() ?: -1
                val titulo = editTextTitulo.text.toString()
                val duracion = editTextDuracion.text.toString().toDoubleOrNull() ?: 0.0
                val numeroVistas = editTextNumeroVistas.text.toString().toIntOrNull() ?: 0
                val fechaSubido =
                    SimpleDateFormat("dd/MM/yyyy").parse(editTextFechaSubido.text.toString())
                val numeroLikes = editTextNumeroLikes.text.toString().toIntOrNull() ?: 0

                if (id == -1 || fechaSubido == null) {
                    mostrarSnackbar("Datos no válidos")
                    return@OnClickListener
                }

                val nuevoVideo = Video(id, titulo, duracion, numeroVistas, fechaSubido, numeroLikes)
                arregloVideos?.add(nuevoVideo)

                // Actualizar el ListView
                val listViewVideos = findViewById<ListView>(R.id.lv_list_view_videos)
                val adaptador = VideoAdapter(this, arregloVideos)
                listViewVideos.adapter = adaptador
                adaptador.notifyDataSetChanged()
                mostrarSnackbar("Video creado: ${nuevoVideo.titulo}")
            }
        )
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
    }

    var posicionItemSeleccionado = -1
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_video, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(
        item: MenuItem
    ): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_video -> {
                abrirDialogoEditar()
                return true
            }

            R.id.mi_eliminar_video -> {
                abrirDialogoEliminar()
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogoEditar() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Video")

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(16, 16, 16, 16)

        val editTextTitulo = EditText(this)
        editTextTitulo.hint = "Título"
        layout.addView(editTextTitulo)

        val editTextDuracion = EditText(this)
        editTextDuracion.hint = "Duración (min)"
        editTextDuracion.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        layout.addView(editTextDuracion)

        val editTextNumeroVistas = EditText(this)
        editTextNumeroVistas.hint = "Número de Vistas"
        editTextNumeroVistas.inputType = InputType.TYPE_CLASS_NUMBER
        layout.addView(editTextNumeroVistas)

        val editTextNumeroLikes = EditText(this)
        editTextNumeroLikes.hint = "Número de Likes"
        editTextNumeroLikes.inputType = InputType.TYPE_CLASS_NUMBER
        layout.addView(editTextNumeroLikes)

        builder.setView(layout)

        val video = arregloVideos?.get(posicionItemSeleccionado)
        editTextTitulo.setText(video?.titulo)
        editTextDuracion.setText(video?.duracion.toString())
        editTextNumeroVistas.setText(video?.numeroVistas.toString())
        editTextNumeroLikes.setText(video?.numeroLikes.toString())

        builder.setPositiveButton(
            "Guardar",
            DialogInterface.OnClickListener { dialog, which ->
                video?.titulo = editTextTitulo.text.toString()
                video?.duracion =
                    editTextDuracion.text.toString().toDoubleOrNull() ?: video?.duracion ?: 0.0
                video?.numeroVistas =
                    editTextNumeroVistas.text.toString().toIntOrNull() ?: video?.numeroVistas ?: 0
                video?.numeroLikes =
                    editTextNumeroLikes.text.toString().toIntOrNull() ?: video?.numeroLikes ?: 0

                // Actualizar el ListView
                val listViewVideos = findViewById<ListView>(R.id.lv_list_view_videos)
                val adaptador = VideoAdapter(this, arregloVideos)
                listViewVideos.adapter = adaptador
                adaptador.notifyDataSetChanged()
                mostrarSnackbar("Video editado: ${video?.titulo}")
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
                arregloVideos?.removeAt(posicionItemSeleccionado)
                val listViewCuenta = findViewById<ListView>(R.id.lv_list_view_videos)
                val adaptador = VideoAdapter(this, arregloVideos)
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