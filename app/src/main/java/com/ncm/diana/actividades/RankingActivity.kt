package com.ncm.diana.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.ncm.diana.Conector
import com.ncm.diana.modelos.adaptadores.EntradaAdaptador
import com.ncm.diana.databinding.ActivityRankingBinding
import com.ncm.diana.modelos.Entrada

class RankingActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityRankingBinding
    private lateinit var ranking: MutableList<Entrada>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        // Vinculación de vistas
        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Guardamos información por defecto sobre el ranking
//        val db = Room.databaseBuilder(this@RankingActivity, Conector::class.java, "ranking")
//            .allowMainThreadQueries()
//            .build()
        val dao = Conector.getDatabase(this@RankingActivity)

        with(dao)
        {
        //with(db.dao()) {
            // insertamos valores
//            this?.insert(Entrada("AAA", 500),
//                Entrada("BBB", 500),
//                Entrada("CCC", 500),
//                Entrada("DDD", 400),
//                Entrada("EEE", 250),
//                Entrada("FFF", 100))

            // leemos los datos
            //ranking = getAll()
            ranking = this?.getAll() ?: mutableListOf()
        }

        with(binding) {
            /**
             * Creamos el adaptador y le pasamos la lista de datos que tiene que gestionar
             */

            val adaptador = EntradaAdaptador(ranking)

            /*adaptador.setOnBorrarListener {
                ranking.removeAt(it)
                adaptador.notifyItemRemoved(it)
                Log.i("XXXX", "Se ha pulsado el botón BORRAR del elemento ${it}")
            }*/

            /**
             * Le decimos al RecyclerView quién es el adaptador que
             * se va a encargar de gestionar la lista en pantalla
             */
            rvRanking.adapter = adaptador

            /**
             * Definimos el gestor de layouts desde aquí
             * y luego indicar al Recicles que tipo de gestor de layouts vamos a usar
             */
            rvRanking.layoutManager = LinearLayoutManager(this@RankingActivity)

            /**
             * Si prevemos que el tamaño del omponenete REcyclerView no va a variar indicamos
             * que su tamaño va a ser siempre el mismo con el siguiente método (MEJORA RENDIMIENTO)
             */
            rvRanking.setHasFixedSize(true)

            /**
             * Botón para volver al home
             */
            btnVolver.setOnClickListener {
                finish()
                return@setOnClickListener
            }
        }
    }
}

