package com.tuusuario.taskapp.domain

import com.tuusuario.taskapp.domain.model.Task
import com.tuusuario.taskapp.domain.repository.TaskRepository
import com.tuusuario.taskapp.domain.usecase.GetPendingTasksUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GetPendingTasksUseCaseTest {

    private class FakeRepo(private val tasks: List<Task>) : TaskRepository {
        override suspend fun getAllTasks() = tasks
        override suspend fun addTask(title: String) {}
    }

    @Test
    fun `retorna solo tareas pendientes`() = runTest {
        val repo = FakeRepo(listOf(
            Task(1, "Pendiente"),
            Task(2, "Completada", completed = true),
            Task(3, "Otra pendiente"),
        ))
        val useCase = GetPendingTasksUseCase(repo)
        val result  = useCase()

        assertEquals(2, result.size)
        assertTrue(result.none { it.completed })
    }

    @Test
    fun `retorna lista en orden descendente por ID`() = runTest {
        val repo = FakeRepo(listOf(
            Task(1, "Primera"),
            Task(3, "Tercera"),
            Task(2, "Segunda"),
        ))
        val useCase = GetPendingTasksUseCase(repo)
        val result  = useCase()

        assertEquals(listOf(3L, 2L, 1L), result.map { it.id })
    }
}