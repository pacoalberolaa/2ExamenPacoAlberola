package com.example.practica5

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

    fun addTarea(tarea: Tarea)= modelTareas.addTarea(tarea)
    suspend fun delTarea(tarea: Tarea)= modelTareas.delTarea(tarea)
    fun getAllTareas()=modelTareas.getAllTareas()
    fun getAllTareasFitness (fitness:Boolean) = modelTareas.getAllTareasFitness(fitness)
    fun getAllTareasFavoritas(favoritas:Boolean) = modelTareas.getTareasFavoritas(favoritas)
    fun getAllTareasFitnessYFavoritas(fitness: Boolean, favoritas: Boolean) = modelTareas.getAllTareasFitnessYFavoritas(fitness, favoritas)

    }