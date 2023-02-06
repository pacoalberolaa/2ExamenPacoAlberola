package com.example.examenandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.practica5.databinding.FragmentSecondBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    val args: SecondFragmentArgs by navArgs()
    private val viewModel: AppViewModel by activityViewModels()
    val esNuevo by lazy { args.tarea==null }


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun iniciaCompra(tarea: Tarea){
        binding.tvNombreTarea.setText(tarea.nombre)
        binding.etFechaTarea.setText(tarea.fecha)
        binding.swFitnessTarea.isChecked= tarea.fitness
        binding.cbFavoritos.isChecked= tarea.favorito
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Compra ${tarea.id}"


    }

    private fun iniciaFabGuardar(){
        binding.fabGuardar.setOnClickListener(){
            if (binding.tvNombreTarea.text.toString().isNullOrEmpty() || binding.etFechaTarea.text.toString().isNullOrEmpty() ){
                val mensaje="El campo de datos \"Nombre\" o  \"Fecha Realización\" están vacios."
                //mostramos el mensaje donde "binding.root" es el ContrainLayout principal
                Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            else
                guardaTarea()
        }
    }

    private fun guardaTarea() {
        //recuperamos los datos

        val fitness=binding.swFitnessTarea.isChecked
        val nombre=binding.tvNombreTarea.text.toString()
        val fecha=binding.etFechaTarea.text.toString()
        val favorito=binding.cbFavoritos.isChecked

        //creamos la tarea: si es nueva, generamos un id, en otro caso le asignamos su id
        val tarea = if(esNuevo)
            Tarea(fitness,favorito, nombre, fecha)
        else
            Tarea(args.tarea!!.id,fitness,favorito, nombre, fecha)
        //guardamos la tarea desde el viewmodel
        viewModel.addCompra(tarea)
        //salimos de editarFragment
        findNavController().popBackStack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciaFabGuardar()
        if (esNuevo)//nueva tarea
        //cambiamos el título de la ventana
            (requireActivity() as AppCompatActivity).supportActionBar?.title = "Nueva compra"
        else
            iniciaCompra(args.tarea!!)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}