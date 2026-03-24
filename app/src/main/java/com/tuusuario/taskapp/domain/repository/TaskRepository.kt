package com.tuusuario.taskapp.domain.repository

import com.tuusuario.taskapp.domain.model.Task

interface TaskRepository {
    suspend fun getAllTasks(): List<Task>
    suspend fun addTask(title: String)
}