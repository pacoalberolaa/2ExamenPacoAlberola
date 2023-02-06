package com.example.examenandroid

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

object ModelTempTareas {
    //lista de tareas
    private val tareas = ArrayList<Tarea>()
    //LiveData para observar en la vista los cambios en la lista
    private val tareasLiveData = MutableLiveData<ArrayList<Tarea>>(tareas)
    //el context que suele ser necesario en acceso a datos
    private lateinit var application: Application

    operator fun invoke(context: Context){
        this.application= context.applicationContext as Application
        GlobalScope.launch { iniciaPruebaTareas() }
    }

    fun getAllTareas(): LiveData<ArrayList<Tarea>> {
        tareasLiveData.value= tareas
        return tareasLiveData
    }

    fun addTarea(tarea: Tarea) {
        val pos = tareas.indexOf(tarea)
        if (pos < 0) {//si no existe
            tareas.add(tarea)
        } else {
            //si existe se sustituye
            tareas.set(pos, tarea)
        }
        //actualiza el LiveData
        tareasLiveData.postValue(tareas)
    }
    fun iniciaPruebaTareas() {
        lateinit var tarea: Tarea
        var i = 1
        (1..10).forEach {
            tarea = Tarea(
                Random.nextBoolean(),
                Random.nextBoolean(),
                "Lista $i",
                "Lista $i"
            )
            tareas.add(tarea)
            i++
        }
        //actualiza el LiveData
        tareasLiveData.value = tareas
    }

    fun delTarea(tarea: Tarea) {
        //Thread.sleep(10000)
        tareas.remove(tarea)
        tareasLiveData.postValue(tareas)
    }


    fun getAllTareasFitness(fitness:Boolean): LiveData<ArrayList<Tarea>> {
        //devuelve el LiveData con la  lista filtrada o entera
        tareasLiveData.value=if(fitness)
            tareas.filter { it.fitness } as ArrayList<Tarea>
        else
            tareas
        return tareasLiveData
    }

}