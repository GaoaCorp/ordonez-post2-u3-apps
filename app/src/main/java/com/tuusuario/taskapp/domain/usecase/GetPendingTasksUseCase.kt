package com.tuusuario.taskapp.domain.usecase

import com.tuusuario.taskapp.domain.model.Task
import com.tuusuario.taskapp.domain.repository.TaskRepository

class GetPendingTasksUseCase(private val repository: TaskRepository) {

    suspend operator fun invoke(): List<Task> {
        return repository.getAllTasks()
            .filter { !it.completed }
            .sortedByDescending { it.id }
    }
}