package com.tuusuario.taskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tuusuario.taskapp.presentation.ui.TaskListScreen
import com.tuusuario.taskapp.ui.theme.TaskAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskAppTheme {
                TaskListScreen()
            }
        }
    }
}