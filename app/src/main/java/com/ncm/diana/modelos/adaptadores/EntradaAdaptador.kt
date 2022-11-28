package com.ncm.diana.modelos.adaptadores

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ncm.diana.databinding.EntradaLayoutBinding
import com.ncm.diana.modelos.Entrada

/* Como quito borrar quito lamda->>
class EntradaAdaptador(val lista: MutableList<Entrada>,
                      val lambda: (Unit) -> Unit):RecyclerView.Adapter<EntradaAdaptador.ElementoContenedor>()
*/
class EntradaAdaptador(val lista: MutableList<Entrada>):RecyclerView.Adapter<EntradaAdaptador.ElementoContenedor>()
{

//    fun borrarItem(position: Int) {
//        lista.removeAt(position)
//        notifyItemRemoved(position)
//    }

    /**
     * Informa al adaptador del tamaño de la lista
     *  @return Int
     */
    override fun getItemCount(): Int = lista.size

    /**
     * Crea un contenedor para cada elemento de la lista y lo asocia
     * al componente Recycler View que lo va a contener.
     * @param parent: ViewGroup
     * @param viewType: Int
     * @return ElementoContenedor
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementoContenedor {
        // recuperamos el inflador a través del contexto de la actividad
        // donde se encuentra el componente RecyclerView.
        val inflador = LayoutInflater.from(parent.context)
        // inflamos el layout de cada elemento
        val binding = EntradaLayoutBinding.inflate(inflador, parent, false)
        // creamos el contenedor y le pasamos el layout inflado
        return ElementoContenedor(binding)
    }

    /**
     * Le pasa al contenedor cada elemento de la lista.
     *  @param holder: ElementoContenedor
     *  @param position: Int
     */
    override fun onBindViewHolder(holder: ElementoContenedor, position: Int) {
        holder.bindItem(lista[position])
    }

    /**
     * Contenedor basado en el patrón View Holder
     */
    inner class ElementoContenedor(val binding:EntradaLayoutBinding):RecyclerView.ViewHolder(binding.root) {

        /**
         * Muestra la información del elemento de tipo Entrada en
         * las vistas correspondientes.
         *
         * @param item: Entrada
         */
        fun bindItem(item: Entrada) {
            with(binding) {
                // mostramos la información
                etqIniciales.text = item.iniciales
                etqPuntos.text = "${item.puntos}"
                etqNivel.text = item.nivel

                //reaccionamos a la pulsación de un botón
//                btnBorrar.setOnClickListener {
//                    lambda
//                    borrarItem(adapterPosition)
//                }
            }
        }
    }
}