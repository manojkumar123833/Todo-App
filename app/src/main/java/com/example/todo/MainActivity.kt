package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.screen.TodoScreen
import com.example.todo.ui.theme.ToDoTheme
import com.example.todo.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            ToDoTheme {

                val todoViewModel: TodoViewModel = viewModel()

                TodoScreen(
                    viewModel = todoViewModel
                )
            }
        }
    }
}