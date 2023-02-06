package com.example.examenandroid

import android.app.Application
import android.content.Context

object Repository {
    private lateinit var modelTareas: ModelTempTareas
    //el context suele ser necesario para recuperar datos
    private lateinit var application: Application
    //inicio del objeto singleton
    operator fun invoke(context: Context){
        this.application= context.applicationContext as Application
        //iniciamos el modelo
        ModelTempTareas(application)
        modelTareas=ModelTempTareas
    }

    fun addCompra(compra: Tarea)= modelTareas.addTarea(compra)
    fun delCompra(compra: Tarea)= modelTareas.delTarea(compra)
    fun getAllCompras()= modelTareas.getAllTareas()
    fun getComprasFiltroFitness (soloFitness:Boolean)= modelTareas.getAllTareasFitness(soloFitness)

}

