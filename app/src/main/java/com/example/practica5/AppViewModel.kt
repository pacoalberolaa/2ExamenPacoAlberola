package com.example.practica5

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private  val repositorio: Repository
    //liveData de lista de tareas
    val tareasLiveData : LiveData<ArrayList<Tarea>>
    //creamos el LiveData de tipo Booleano. Repesenta nuestro filtro
    //private val soloSinPagarLiveData= MutableLiveData<Boolean>(false)
    private val porEstadoLiveData = MutableLiveData<Int>()

    val FAVORITO="FAVORITO"
    val FITNESS="FITNESS"
    private val filtrosLiveData by lazy{
        val mutableMap = mutableMapOf<String, Any?>(
            FAVORITO to false,
        )
        MutableLiveData(mutableMap)
    }
    init {
        //inicia repositorio
        Repository(getApplication<Application>().applicationContext)
        repositorio=Repository
        //tareasLiveData=Transformations.switchMap(soloSinPagarLiveData)
        //{soloSinPagar->Repository.getTareasFiltroSinPagar(soloSinPagar)}
        tareasLiveData=Transformations.switchMap(filtrosLiveData)
        { mapFiltro ->
            val aplicarFavoritos = mapFiltro!![FAVORITO] as Boolean
            val aplicarFitness = mapFiltro[FITNESS] as Boolean
            //Devuelve el resultado del when
            when {//trae toda la lista de tareas
                (!(aplicarFavoritos && aplicarFitness)) -> repositorio.getAllTareas()
                //Sólo filtra por ESTADO
                (!aplicarFavoritos && aplicarFitness) -> repositorio.getAllTareasFitness(aplicarFitness)
                //Sólo filtra SINPAGAR
                (aplicarFavoritos && aplicarFitness) -> repositorio.getAllTareasFitnessYFavoritas(aplicarFitness, aplicarFavoritos)
                else -> repositorio.getAllTareasFavoritas(aplicarFavoritos)
            }
        }
    }
    fun addTarea(tarea: Tarea) = viewModelScope.launch(Dispatchers.IO){
        Repository.addTarea(tarea)}
    fun delTarea(tarea: Tarea) = viewModelScope.launch(Dispatchers.IO){
        Repository.delTarea(tarea)}
}