package com.example.examenandroid

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {
    //repositorio
    private  val repositorio: Repository
    val comprasLiveData : LiveData<ArrayList<Tarea>>
    private val fitnessLiveData = MutableLiveData<Boolean>(false)
    //inicio ViewModel
    init {
        //inicia repositorio
        Repository(getApplication<Application>().applicationContext)
        repositorio=Repository
        //tareasLiveData= Transformations.switchMap(estadoLiveData)
        //{estadoLiveData->Repository.getTareasFiltroEstado(estadoLiveData)}
        comprasLiveData= Transformations.switchMap(fitnessLiveData)
        { fitnessLiveData ->
            when (fitnessLiveData) {
                false -> repositorio.getAllCompras()
                true -> repositorio.getComprasFiltroFitness(fitnessLiveData)
            }
        }

    }

    fun addCompra(tarea: Tarea) = viewModelScope.launch(Dispatchers.IO){
        Repository.addCompra(tarea)}
    //fun delTarea(tarea: Tarea) = repositorio.delTarea(tarea)
    fun delCompra(tarea: Tarea) = viewModelScope.launch(Dispatchers.IO){
        Repository.delCompra(tarea)}

    /**
     * activa el LiveData del filtro
     */
    fun setFitness(fitness:Boolean){fitnessLiveData.value= fitness}



}