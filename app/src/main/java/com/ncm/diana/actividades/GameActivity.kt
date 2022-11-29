package com.ncm.diana.actividades
/**
 * Natalia Castillo Muñoz
 */
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.ncm.diana.Conector
import com.ncm.diana.R
import com.ncm.diana.databinding.ActivityGameBinding
import com.ncm.diana.modelos.Entrada
import kotlin.math.abs
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private val NIVELES: List<Int> = listOf(50, 100, 250, 500)

    // variables de juego
    private var umbral: Int = 5
    private var dif: Int = 0

    private var puntuacion: Int = 0
    private var tiradas: Int = 5
    private var numero: Int = 0
    private var iniciales: String = "NCM"
    private lateinit var nivel: String


    /**
     * @param savedInstanceState: Bundle?
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // eliminamos la APPBAR
        supportActionBar?.hide()

        // leemos los valores: umbral, dificultad e iniciales de la configuración
        val sp: SharedPreferences = getSharedPreferences("com.ncm.diana", MODE_PRIVATE)
        umbral = sp.getInt("umbral",5)
        dif = sp.getInt("dificultad",0)
        iniciales = sp.getString("iniciales", "NCM").toString()
        nivel = sp.getString("txtnivel", "Min").toString()

        // 1. Definir el valor máximo del slider en función del nivel
        binding.barBusqueda.valueTo = NIVELES[dif].toFloat()
        Log.i("XXX -DIFICULTAD", NIVELES[dif].toString())
        //binding.barBusqueda.valueTo = nivelDificultad().toFloat()
        //Log.i("XXX -DIFICULTAD", nivelDificultad().toString())

        // elegimos un valor al azar
        //numero = Random.nextInt(0, nivelDificultad())
        numero = Random.nextInt(0, NIVELES[dif])
        // actualizamos el panel
        panel()
        // reaccionamos a la pulsación de botón de disparo
        // - Decrementar el número de dardos
        // - Comprobar si he acertado:
        //      - SI - cambiamos número y rellenamos dardos
        //      - NO - comprobar si quedan dardos
        //           - SI - continuar
        //           - NO - finalizar juego
        //
        //
        binding.btnDispara.setOnClickListener {
            // recuperamos valor del slider
            val valor = binding.barBusqueda.value.toInt()
            // calculamos los puntos
            val dif = abs(valor - numero)
            if (dif <= umbral) puntuacion += (dif*10)
            // decrementamos el número de dardos
            tiradas--
            // comprobamos si hemos acertado
            if (valor == numero)  {
                val madb = MaterialAlertDialogBuilder(this@GameActivity)
                    .setTitle(R.string.app_name)
                    .setMessage(getString(R.string.etq_exito))
                    .setPositiveButton("Si") { i, w ->
                        // reiniciamos el número
                        //numero = Random.nextInt(0, nivelDificultad())
                        numero = Random.nextInt(0, NIVELES[dif])
                        // reseteamos las tiradas
                        tiradas = 5
                        // incrementamos los puntos
                        puntuacion += 1000
                        // actualizamos el panel
                        panel()
                    }
                    .setNegativeButton("No") { i, w ->
                        puntuacion += 1000
                        addRanking()
                        finish()
                        return@setNegativeButton
                    }
                    .create()
                madb.show()
            } else {
                // Damos una pista sobre si el número es mayor o menor
                val idmensaje = if (valor < numero) getString(R.string.etq_fallo_mayor)
                else getString(R.string.etq_Fallo_menor)
                // Mostramos mensaje
                Snackbar.make(it,idmensaje, Snackbar.LENGTH_SHORT).show()
                // actualizamos el panel
                panel()

                // Si no quedan tiradas, el juego acaba
                if ((tiradas == 0) && (puntuacion == 0)) {
                    val madb = MaterialAlertDialogBuilder(this@GameActivity)
                        .setTitle(R.string.app_name)
                        .setMessage(getString(R.string.etq_dardos, puntuacion))
                        .setNegativeButton("Salir") { i, w ->
                            finish()
                            return@setNegativeButton
                        }
                        .create()
                    madb.show()
                } else if ((tiradas == 0) && (puntuacion > 0)) {
                    addRanking()
                    val madb = MaterialAlertDialogBuilder(this@GameActivity)
                        .setTitle(R.string.app_name)
                        .setMessage(getString(R.string.etq_dardos, puntuacion))
                        .setNegativeButton("Salir") { i, w ->
                            finish()
                            return@setNegativeButton
                        }
                        .create()
                    madb.show()
                }
            }
        }
    }

    /**
     * Actualiza el marcador de puntos y de tiradas
     */
    private fun panel() {
        with(binding) {
            etqTexto.text = resources.getString(R.string.etq_mensaje, numero)
            etqPuntos.text = resources.getString(R.string.etq_puntos, puntuacion)
            etqTiradas.text = resources.getQuantityString(R.plurals.etq_tiradas, tiradas, tiradas)
            etqJugador.text = resources.getString(R.string.etq_jugador_valor, iniciales)

            btnVolver.setOnClickListener {
                finish()
                return@setOnClickListener
            }
        }
    }

    /**
     * enviar iniciales y puntuacion al ranking
     */
    fun addRanking() {
        val dao = Conector.getDatabase(this@GameActivity)
        with(dao) {
            // insertamos valores
            this?.insert(Entrada(this@GameActivity.iniciales, this@GameActivity.puntuacion, this@GameActivity.nivel))
        }

        /**
         * Bloqueamos el botón de vuelta atrás
         */
//    fun onBackPressed() {
//        super.onBackPressed()
//    }
    }
}