package com.example.examenandroid

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica5.R
import com.example.practica5.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    lateinit var comprasAdapter: TareasAdapter
    private val viewModel: AppViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciaRecyclerView()
        iniciaCRUD()
        viewModel.comprasLiveData.observe(viewLifecycleOwner, Observer<List<Tarea>> { tarea ->
            //actualizaLista(lista)
            comprasAdapter.setCompras(tarea)

        })

        binding.fabNuevo.setOnClickListener {
            findNavController().navigate(R.id.action_editar)
        }
        binding.fabNuevo.setOnClickListener {
            //creamos acción enviamos argumento nulo porque queremos crear NuevaTarea
            val action = FirstFragmentDirections.actionEditar(null)
            findNavController().navigate(action)

        }
        iniciaFiltros()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun iniciaFiltros() {
        binding.swFitness.setOnCheckedChangeListener() { _, isChecked ->
            viewModel.setFitness(isChecked)
        }
    }

    private fun iniciaRecyclerView() {
        //creamos el adaptador
        comprasAdapter = TareasAdapter()

        with(binding.rvTareas) {
            //Creamos el layoutManager
            layoutManager = LinearLayoutManager(activity)
            //le asignamos el adaptador
            adapter = comprasAdapter
        }
    }

    private fun iniciaCRUD() {
        //Nueva Tarea
        binding.fabNuevo.setOnClickListener {
            val action = FirstFragmentDirections.actionEditar(null)
            findNavController().navigate(action)
        }
        comprasAdapter.listener = object : TareasAdapter.OnCompraClickListener {
            //**************Editar  Tarea*************
            override fun onCompraClick(tarea: Tarea?) {
                //creamos acción enviamos argumento la compra para editarla
                val action = FirstFragmentDirections.actionEditar(tarea)
                findNavController().navigate(action)
            }

            //***********Borrar Tarea************
            override fun onCompraBorrarClick(tarea: Tarea?) {
                //borramos la compra
                if (tarea != null) {
                    borrarCompra(tarea)
                }
            }
        }

    }

    fun borrarCompra(tarea: Tarea) {
        AlertDialog.Builder(activity as Context)
            .setTitle(android.R.string.dialog_alert_title)
            //recuerda: todo el texto en string.xml
            .setMessage("Desea borrar la Compra ${tarea.id}?")
            //acción si pulsa si
            .setPositiveButton(android.R.string.ok) { v, _ ->
                //borramos la tarea
                viewModel.delCompra(tarea)
                //cerramos el dialogo
                v.dismiss()
            }
            //accion si pulsa no
            .setNegativeButton(android.R.string.cancel) { v, _ -> v.dismiss() }
            .setCancelable(false)
            .create()
            .show()
    }
}