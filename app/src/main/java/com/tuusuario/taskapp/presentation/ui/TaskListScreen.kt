package com.tuusuario.taskapp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tuusuario.taskapp.domain.model.Task
import com.tuusuario.taskapp.presentation.viewmodel.TaskUiState
import com.tuusuario.taskapp.presentation.viewmodel.TaskViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskListScreen(viewModel: TaskViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Tareas Pendientes", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        when (val state = uiState) {
            is TaskUiState.Loading ->
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            is TaskUiState.Success ->
                LazyColumn {
                    items(state.tasks, key = { it.id }) { task ->
                        TaskItem(task = task)
                    }
                }
            is TaskUiState.Error ->
                Text(state.message, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun TaskItem(task: Task) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = task.completed, onCheckedChange = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = task.title,
                style = if (task.completed)
                    MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.LineThrough)
                else MaterialTheme.typography.bodyMedium
            )
        }
    }
}