package com.example.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.Todo
import com.example.todo.data.TodoDatabase
import com.example.todo.data.TodoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val database = TodoDatabase.getDatabase(application)
    private val repository = TodoRepository(database.todoDao())
    val todos: StateFlow<List<Todo>> =
        repository.allTodos.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addTodo(title: String, description: String) {

        if (title.isBlank()) return

        viewModelScope.launch {

            repository.insertTodo(
                Todo(
                    title = title,
                    description = description
                )
            )
        }
    }

    fun updateTodo(todo: Todo) {

        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo) {

        viewModelScope.launch {
            repository.deleteTodo(todo)
        }
    }

    fun toggleTodo(todo: Todo) {

        viewModelScope.launch {

            repository.updateTodo(
                todo.copy(
                    isCompleted = !todo.isCompleted
                )
            )
        }
    }
}