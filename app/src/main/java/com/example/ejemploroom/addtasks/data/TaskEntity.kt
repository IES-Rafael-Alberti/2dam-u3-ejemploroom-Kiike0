package com.example.ejemploroom.addtasks.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//La Entidad es el modelo de datos que vamos a persistir en nuestra base de datos...
@Entity
data class TaskEntity (
    @PrimaryKey
    val id: Int,
    val task: String,
    var selected: Boolean = false
)