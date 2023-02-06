package com.example.examenandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practica5.R
import com.example.practica5.databinding.ItemTareaBinding

class TareasAdapter()
: RecyclerView.Adapter<TareasAdapter.ComprasViewHolder>() {

    lateinit var listaCompras: List<Tarea>
    var listener: OnCompraClickListener? = null

    interface OnCompraClickListener{
        fun onCompraBorrarClick(tarea: Tarea?)
        fun onCompraClick(tarea: Tarea?)
    }

    fun setCompras(listaCompras: List<Tarea>) {
        this.listaCompras = listaCompras
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = listaCompras.size

    inner class ComprasViewHolder(val binding: ItemTareaBinding) :
        RecyclerView.ViewHolder(binding.root){
        init {
            //inicio del click de icono borrar
            binding.ivBasura.setOnClickListener(){
                //recuperamos la tarea de la lista
                val compra=listaCompras.get(this.adapterPosition)
                //llamamos al evento borrar que estará definido en el fragment
                listener?.onCompraBorrarClick(compra)
            }
            //inicio del click sobre el Layout(constraintlayout)
            binding.root.setOnClickListener(){
                val compra=listaCompras.get(this.adapterPosition)
                listener?.onCompraClick(compra)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComprasViewHolder {
        //utilizamos binding, en otro caso hay que indicar el item.xml. Para más detalles puedes verlo en la documentación
        val binding = ItemTareaBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ComprasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComprasViewHolder, position: Int) {
        //Nos pasan la posición del  item a mostrar en el viewHolder
        with(holder) {
            //cogemos la tarea a mostrar y rellenamos los campos del ViewHolder
            with(listaCompras!!.get(position)) {
                binding.tvNombre.text = nombre
                binding.tvFecha.text = fecha
                binding.ivFavoritos.setImageResource(
                    when(favorito){
                        true -> R.drawable.ic_star
                        false -> R.drawable.ic_border_star
                    }
                )
                //cambiamos el color de fondo si la prioridad es alta
            }
        }

    }

}