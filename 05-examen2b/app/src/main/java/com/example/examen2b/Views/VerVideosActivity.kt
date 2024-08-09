package com.example.examen2b.Views

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.examen2b.Controller.CuentaCRUD
import com.example.examen2b.Controller.VideoAdapter
import com.example.examen2b.Controller.VideoCRUD
import com.example.examen2b.Model.Cuenta
import com.example.examen2b.Model.Video
import com.example.examen2b.R
import com.google.android.material.snackbar.Snackbar

class VerVideosActivity : AppCompatActivity() {
    private lateinit var cuenta: Cuenta
    private lateinit var listaDeVideos: MutableList<Video>
    private lateinit var adaptadorVideos: VideoAdapter
    private var cuentaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_videos)

        cuentaId = intent.getIntExtra("CUENTA_ID", -1)
        val nombreCuentaTextView = findViewById<TextView>(R.id.txt_Inserte_Nombre)
        val listViewVideos = findViewById<ListView>(R.id.lv_Listado_Videos)
        val botonAgregarVideo = findViewById<Button>(R.id.btn_Agregar_Videos)
        val botonRegresar = findViewById<Button>(R.id.btn_Regresar_Videos)

        botonAgregarVideo.setOnClickListener {
            val intent = Intent(this, AgregarVideoActivity::class.java)
            intent.putExtra("CUENTA_ID", cuentaId)
            startActivityForResult(intent, REQUEST_CODE_AGREGAR_VIDEO)
        }

        botonRegresar.setOnClickListener {
            finish()
        }

        cargarDatosDeCuenta()

        nombreCuentaTextView.text = cuenta.nombre
        adaptadorVideos = VideoAdapter(this, listaDeVideos)
        listViewVideos.adapter = adaptadorVideos

        registerForContextMenu(listViewVideos)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_AGREGAR_VIDEO,
                REQUEST_CODE_EDITAR_VIDEO -> {
                    cargarDatosDeCuenta()
                    adaptadorVideos.actualizarLista(listaDeVideos)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        cargarDatosDeCuenta()
        adaptadorVideos.actualizarLista(listaDeVideos)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_video, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        val videoSeleccionado = listaDeVideos[position]

        return when (item.itemId) {
            R.id.mi_EditarVideo -> {
                val intent = Intent(this, EditarVideoActivity::class.java)
                intent.putExtra("VIDEO_ID", videoSeleccionado.id)
                intent.putExtra("CUENTA_ID", cuentaId)
                startActivityForResult(intent, REQUEST_CODE_EDITAR_VIDEO)
                true
            }
            R.id.mi_EliminarVideo -> {
                VideoCRUD(this).eliminarVideoPorId(videoSeleccionado.id)
                cargarDatosDeCuenta()
                adaptadorVideos.actualizarLista(listaDeVideos)
                mostrarSnackbar("Video Eliminado")
                true
            }
            R.id.mi_VerMapa -> {
                // Aquí seleccionas la latitud y longitud del video
                val latitud = videoSeleccionado.latitud
                val longitud = videoSeleccionado.longitud

                // Crear el Intent para lanzar VerUbicacionActivity
                val intent = Intent(this, VerUbicacionActivity::class.java)
                intent.putExtra("LATITUD", latitud)
                intent.putExtra("LONGITUD", longitud)
                startActivity(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun cargarDatosDeCuenta() {
        cuenta = CuentaCRUD(this).obtenerCuentaPorId(cuentaId) ?: Cuenta()
        listaDeVideos = VideoCRUD(this).obtenerVideosPorCuentaId(cuentaId).toMutableList()
    }

    private fun mostrarSnackbar(mensaje: String) {
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, mensaje, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        const val REQUEST_CODE_AGREGAR_VIDEO = 1
        const val REQUEST_CODE_EDITAR_VIDEO = 2
    }
}