package com.tuusuario.taskapp.domain

data class Task(
    val id       : Long,
    val title    : String,
    val completed: Boolean = false,
)