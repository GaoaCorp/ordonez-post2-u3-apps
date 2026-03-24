package com.tuusuario.taskapp.data.repository

import com.tuusuario.taskapp.domain.model.Task
import com.tuusuario.taskapp.domain.repository.TaskRepository

class InMemoryTaskRepository : TaskRepository {

    private val tasks = mutableListOf(
        Task(1, "Estudiar Clean Architecture"),
        Task(2, "Implementar Use Cases en la capa Domain"),
        Task(3, "Migrar de Hilt a Koin"),
        Task(4, "Leer documentación de Koin DSL", completed = true),
        Task(5, "Escribir test unitario del Use Case"),
    )

    override suspend fun getAllTasks(): List<Task> = tasks.toList()

    override suspend fun addTask(title: String) {
        tasks.add(Task(id = tasks.size.toLong() + 1, title = title))
    }
}