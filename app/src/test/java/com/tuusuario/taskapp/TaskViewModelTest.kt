package com.tuusuario.taskapp

import com.tuusuario.taskapp.data.repository.InMemoryTaskRepository
import com.tuusuario.taskapp.domain.usecase.GetPendingTasksUseCase
import com.tuusuario.taskapp.presentation.viewmodel.TaskUiState
import com.tuusuario.taskapp.presentation.viewmodel.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: TaskViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        val repo    = InMemoryTaskRepository()
        val useCase = GetPendingTasksUseCase(repo)
        viewModel   = TaskViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `carga de tareas produce estado Success con tareas pendientes`() = runTest {
        viewModel.loadTasks()
        val state = viewModel.uiState.value
        assertTrue(state is TaskUiState.Success)
        // 4 tareas pendientes de 5 (la completada se filtra)
        assertEquals(4, (state as TaskUiState.Success).tasks.size)
    }
}