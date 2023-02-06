package com.example.examenandroid

import android.os.Parcelable
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tarea(
    @PrimaryKey(autoGenerate = true) var id:Long?=null,//id único
    val favorito:Boolean,
    val fitness:Boolean,
    val nombre:String,
    val fecha:String

): Parcelable {
    @Ignore
    constructor( favorito: Boolean,
                 fitness: Boolean,
                 nombre: String,
                 fecha: String
    ):this(null,favorito, fitness, nombre, fecha){}


    override fun equals(other: Any?): Boolean {
        return (other is Tarea) && (this.id == other?.id)
    }
}