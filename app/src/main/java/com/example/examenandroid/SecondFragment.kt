package com.example.examenandroid

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.practica5.R
import com.example.practica5.databinding.FragmentSecondBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    val args: SecondFragmentArgs by navArgs()
    private val viewModel: AppViewModel by activityViewModels()
    //será una tarea nueva si no hay argumento
    val esNuevo by lazy { args.tarea==null }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_TareaFragment_to_ListaFragment)
        }*/

        iniciaFabGuardar()

        //si es nueva tarea o es una edicion
        if (esNuevo)//nueva tarea
        //cambiamos el título de la ventana
            (requireActivity() as AppCompatActivity).supportActionBar?.title = "Nueva tarea"
        else
            iniciaTarea(args.tarea!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Carga los valores de la tarea  a editar
     */
    private fun iniciaTarea(tarea: Tarea) {
        binding.cbFavoritos.isSelected = tarea.favorito
        binding.rbFitness.isChecked = tarea.fitness
        binding.tvNombreTarea.text = tarea.nombre
        binding.etFechaTarea.setText(tarea.fecha)
        //cambiamos el título
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Tarea ${tarea.id}"
    }

    private fun guardaTarea() {
        //recuperamos los datos
        val fitness=binding.rbFitness.isChecked
        val favorito=binding.cbFavoritos.isChecked

        val nombre=binding.tvNombreTarea.text.toString()
        val fecha=binding.etFechaTarea.text.toString()
        //creamos la tarea: si es nueva, generamos un id, en otro caso le asignamos su id
        val tarea = if(esNuevo)
            Tarea(fitness,favorito,nombre,fecha)
        else
            Tarea(args.tarea!!.id,fitness,favorito,nombre,fecha)
        //guardamos la tarea desde el viewmodel
        viewModel.addTarea(tarea)
        //salimos de editarFragment
        findNavController().popBackStack()
    }

    private fun iniciaFabGuardar() {
        binding.fabGuardar.setOnClickListener {
            if (!binding.tvNombreTarea.text.toString().isEmpty() || !binding.etFechaTarea.text.toString().isEmpty())
                guardaTarea()
            else
                muestraMensajeError()
        }
    }

    private fun muestraMensajeError() {
        Snackbar.make(binding.root, "Es necesario rellenar todos los campos", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }
}