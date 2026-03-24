package com.tuusuario.taskapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuusuario.taskapp.domain.model.Task
import com.tuusuario.taskapp.domain.usecase.GetPendingTasksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class TaskUiState {
    object Loading                            : TaskUiState()
    data class Success(val tasks: List<Task>) : TaskUiState()
    data class Error(val message: String)     : TaskUiState()
}

class TaskViewModel(
    private val getPendingTasks: GetPendingTasksUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<TaskUiState>(TaskUiState.Loading)
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    init { loadTasks() }

    fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = TaskUiState.Loading
            _uiState.value = try {
                TaskUiState.Success(getPendingTasks())
            } catch (e: Exception) {
                TaskUiState.Error(e.message ?: "Error al cargar tareas")
            }
        }
    }
}