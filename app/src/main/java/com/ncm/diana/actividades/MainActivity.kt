package com.ncm.diana.actividades
/**
 * Natalia Castillo Muñoz
 */
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.ncm.diana.R
import com.ncm.diana.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var dificultad: Int = 0
    private var umbral: Int = 5
    private var iniciales: String = ""
    private var nivel: String = ""

    /**
     * @param savedInstanceState: Bundle?
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // leemos el valor del umbral de la configuración
        val sp: SharedPreferences = getSharedPreferences("com.ncm.diana", MODE_PRIVATE)
        umbral = sp.getInt("umbral",5)
        dificultad = sp.getInt("dificultad", 0)
        iniciales = sp.getString("iniciales", "NCM").toString()
        nivel = sp.getString("nivel", "Min").toString()

        with(binding) {
            etqUmbral.text = resources.getString(R.string.etq_umbral_valor, umbral)
            etqDificil.text = resources.getString(R.string.etq_dificil_nivel, nivel)
            etqJugador.text = resources.getString(R.string.etq_jugador_valor, iniciales)


            btnJugar.setOnClickListener {
                finish()
                val i = Intent(this@MainActivity, GameActivity::class.java)
                startActivity(i)
            }
        }
    }

    /**
     * Mostramos el menú en la barra de aplicación
     * @param menu: Menu?
     * @return Boolean
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Reaccionamos a la pulsación de una opción de menú
     * @param item: MenuItem
     * @return Boolean
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {
            R.id.btn_configuracion -> {
                val i = Intent(this@MainActivity, ConfigActivity::class.java)
                startActivity(i)

                true    // devolvemos TRUE
            }
            R.id.btn_ranking -> {
                val i = Intent(this@MainActivity, RankingActivity::class.java)
                startActivity(i)

                true       // devolvemos TRUE
            }
            R.id.btn_info -> {
                Snackbar.make( binding.root, R.string.etq_mensaje_info, Snackbar.LENGTH_LONG).show()

                true    // devolvemos TRUE
            }
            else -> super.onOptionsItemSelected(item)   // devuelve FALSE por defecto
        }
    }
}

