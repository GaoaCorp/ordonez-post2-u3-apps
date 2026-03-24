package com.tuusuario.taskapp.domain

interface TaskRepository {
    suspend fun getAllTasks(): List<Task>
    suspend fun addTask(title: String)
}