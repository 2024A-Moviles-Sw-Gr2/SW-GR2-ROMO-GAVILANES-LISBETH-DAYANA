package com.example.deber_03

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deber_03.Adaptadores.AdaptadorCategoria
import com.example.deber_03.Adaptadores.AdaptadorVideosMusicalesParaTi
import com.example.deber_03.Adaptadores.AdaptadorCanciones


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv_horizontal_top_categoria = findViewById<RecyclerView>(R.id.rv_categoria_top_home)

        val dataSourceCategoria = ArrayList<String>()
        dataSourceCategoria.add("Entrenamiento")
        dataSourceCategoria.add("Energía")
        dataSourceCategoria.add("Relajación")
        dataSourceCategoria.add("Para sentirte bien")
        dataSourceCategoria.add("Viaje diario")
        dataSourceCategoria.add("Fiesta")
        dataSourceCategoria.add("Romance")
        dataSourceCategoria.add("Triste")
        dataSourceCategoria.add("Concentración")
        dataSourceCategoria.add("Sueño")

        val linearLayoutManagerCategoria = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapterCategoriaTopHome = AdaptadorCategoria(this, dataSourceCategoria, rv_horizontal_top_categoria)
        rv_horizontal_top_categoria.setLayoutManager(linearLayoutManagerCategoria)
        rv_horizontal_top_categoria.setAdapter(adapterCategoriaTopHome)

        val rv_horizontal_grid_vovler_escuchar = findViewById<RecyclerView>(R.id.rv_volver_escuchar_home)

        val cancionesPop = arrayListOf(
            Song("Chk Chk Boom", R.drawable.imagen1),
            Song("Nobody", R.drawable.imagen2),
            Song("Come as you are", R.drawable.imagen3),
            Song("kissing Strangers", R.drawable.imagen4),
            Song("Into You", R.drawable.imagen5),
            Song("good 4 u", R.drawable.imagen6),
            Song("Positions", R.drawable.imagen7),
            Song("Happier", R.drawable.imagen8),
            Song("All of Me", R.drawable.imagen9),
            Song("No Time To Die", R.drawable.imagen10)
        )

        val linearLayoutManagerVolverEscuchar = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapterVolverEscucharHome = AdaptadorCanciones(this, cancionesPop, rv_horizontal_grid_vovler_escuchar)
        rv_horizontal_grid_vovler_escuchar.layoutManager = linearLayoutManagerVolverEscuchar
        rv_horizontal_grid_vovler_escuchar.adapter = adapterVolverEscucharHome

        val rv_horizontal_videos_recomendados = findViewById<RecyclerView>(R.id.rv_videos_recomendados_home)

        val videosParaTi = arrayListOf(
            Video("Amárrame (con Juanes)", R.drawable.v1),
            Video("Rosa Pastel", R.drawable.v2),
            Video("Acuérdate de mí", R.drawable.v3),
            Video("Bailando Bachata (Premio Lo Nuestro 2024)", R.drawable.v4),
            Video("Sofia", R.drawable.v5),
            Video("¿Cómo Pasó?", R.drawable.v6),
            Video("Sofia Reyes- Lo Siento (Live from YouTube Space LA)", R.drawable.v7),
            Video("Si No Estás", R.drawable.v8),
            Video("La llorona", R.drawable.v9)
        )

        val linearLayoutManagerVideosRecomendados = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        val adapterVideosRecomendados = AdaptadorVideosMusicalesParaTi(this, videosParaTi,rv_horizontal_videos_recomendados)
        rv_horizontal_videos_recomendados.setLayoutManager(linearLayoutManagerVideosRecomendados)
        rv_horizontal_videos_recomendados.setAdapter(adapterVideosRecomendados)
    }
}