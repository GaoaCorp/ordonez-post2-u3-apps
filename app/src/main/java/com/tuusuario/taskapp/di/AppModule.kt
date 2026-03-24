package com.tuusuario.taskapp.di

import com.tuusuario.taskapp.data.repository.InMemoryTaskRepository
import com.tuusuario.taskapp.domain.repository.TaskRepository
import com.tuusuario.taskapp.domain.usecase.GetPendingTasksUseCase
import com.tuusuario.taskapp.presentation.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<TaskRepository> { InMemoryTaskRepository() }
    factory { GetPendingTasksUseCase(get()) }
    viewModel { TaskViewModel(get()) }
}