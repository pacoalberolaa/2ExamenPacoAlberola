package com.example.examenandroid

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tarea(
    var id:Long?=null,//id único
    val favorito:Boolean,
    val fitness:Boolean,
    val nombre:String,
    val fecha:String
): Parcelable {
    constructor( favorito: Boolean,
                 fitness: Boolean,
                 nombre: String,
                 fecha: String):this(generateId(),favorito, fitness, nombre, fecha){}
    companion object {
        var idContador = 1L//iniciamos contador de tareas
        private fun generateId(): Long {
            return idContador++//sumamos uno al contador

        }
    }
    override fun equals(other: Any?): Boolean {
        return (other is Tarea) && (this.id == other?.id)
    }
}