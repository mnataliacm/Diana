package com.ncm.diana.actividades
/**
 * Natalia Castillo Muñoz
 */
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.android.material.snackbar.Snackbar
import com.ncm.diana.databinding.ActivityConfigBinding

class ConfigActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfigBinding
    private lateinit var sp: SharedPreferences
    private var nivel: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Vinculación de vistas
        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Preference Manager -> gestiona los archivos de configuración|shared preferences
        // La información se almacena en formato XML en parejas clave-valor
        sp = getSharedPreferences("com.ncm.diana", MODE_PRIVATE)

        with(binding) {

            // Leemos el nivel desde el archivo de configuración y
            // seleccionamos en el spinner el nivel de dificultad
            etqDificultadSpinner.setSelection(sp.getInt("dificultad", 0))

            // leemos el valor del umbral del archivo de preferencias
            txtUmbral.setText(sp.getInt("umbral", 5).toString())

            //leemos las iniciales del archivo de preferencias
            txtJugador.setText(sp.getString(("iniciales"), "NCM"))

            // definimos el listener para el guardado
            btnGuardar.setOnClickListener {

                // recuperamos el valor del umbral y la dificultad
                val valorU = txtUmbral.text.toString()
                val valorN = etqDificultadSpinner.selectedItemId.toInt()
                val valorJ = txtJugador.text.toString()

                if (valorU == "") {
                    // Si el cuadro del umbral está vacío, mostramos un mensaje de error
                    Snackbar.make(
                        it,
                        "Introduce un valor para el umbral de puntos.",
                        Snackbar.LENGTH_LONG
                    ).show()
                } else if (valorJ == "") {
                    // Si el cuadro del umbral está vacío, mostramos un mensaje de error
                    Snackbar.make(
                        it,
                        "Introduce tus iniciales.",
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    nivel = textoNivel(valorN)
                    // guardamos si hay algún valor
                    sp.edit {
                        putInt("umbral", valorU.toInt())
                        putInt("dificultad", valorN)
                        putString("iniciales", valorJ)
                        putString("txtnivel", nivel)
                        commit()
                    }

                    // volvemos a la actividad anterior y la reiniciamos para que actualice datos
                    finish()
                    val i = Intent(this@ConfigActivity, MainActivity::class.java)
                    startActivity(i)
                    return@setOnClickListener
                }
            }
            btnCancelar.setOnClickListener {
                finish()
                return@setOnClickListener
            }
        }
    }

    private fun textoNivel(diferencia: Any): String {
        when (diferencia) {
            0 -> {
                nivel = "Min"
            }
            1 -> {
                nivel = "Med"
            }
            2 -> {
                nivel = "Dif"
            }
            3 -> {
                nivel = "Max"
            }
        }
        return nivel
    }
}