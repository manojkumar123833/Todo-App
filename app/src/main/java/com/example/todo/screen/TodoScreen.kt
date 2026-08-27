package com.example.todo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.todo.data.Todo
import com.example.todo.viewmodel.TodoViewModel

@Composable
fun TodoScreen(
    viewModel: TodoViewModel
) {

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    val todos by viewModel.todos.collectAsState()
    var blank by remember { mutableStateOf(false) }

    var message by remember {
        mutableStateOf("")
    }

    if (blank) {

        AlertDialog(
            onDismissRequest = { blank = false },
            title = { Text(message, color = Color.Red) },
            confirmButton = {
                Button(
                    onClick = {
                        blank = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("OK", fontWeight = FontWeight.Bold,color = Color.Black)
                }
            }
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(
            text = "Todo App",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
            },
            label = {
                Text("Todo Title")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            )

        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
            },
            label = {
                Text("Description")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            maxLines = 3,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            )
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            onClick = {

                if (title.isBlank()) {
                    message = "Title can't be blank!"
                    blank = true
                } else if (description.isBlank()) {
                    message = "Description can't be blank!"
                    blank = true
                } else {
                    viewModel.addTodo(
                        title = title,
                        description = description
                    )

                    title = ""
                    description = ""
                }

            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue.copy(alpha = 0.6f),
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Add Todo")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(
                items = todos,
                key = { it.id }
            ) { todo ->

                TodoItem(
                    todo = todo,
                    onCheckedChange = {
                        viewModel.toggleTodo(todo)
                    },
                    onDelete = {
                        viewModel.deleteTodo(todo)
                    }
                )
            }
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onCheckedChange: () -> Unit,
    onDelete: () -> Unit
) {
    var dlt by remember { mutableStateOf(false) }

    var message1 by remember {
        mutableStateOf("")
    }
    if (dlt) {

        AlertDialog(
            onDismissRequest = { dlt = false },
            title = { Text(message1, color = Color.Red) },
            confirmButton = {
                Button(
                    onClick = {
                        dlt = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Cancel", fontWeight = FontWeight.Bold,color = Color.Black)
                }
                Button(
                    onClick = {
                        dlt = false
                        onDelete()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("OK", fontWeight = FontWeight.Bold,color = Color.Black)
                }

            }
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE3F2FD)
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = {
                    onCheckedChange()
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Black,
                    checkmarkColor = Color.White,
                    uncheckedColor = Color.Gray
                )
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {

                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.titleMedium
                )

                if (todo.description.isNotBlank()) {

                    Text(

                        text = todo.description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Text(
                    text = if (todo.isCompleted) {
                        "Completed"
                    } else {
                        "Pending"
                    }
                )
            }

            Button(
                onClick = {
                    message1 = "Are you sure you want to delete this todo?"
                    dlt = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red.copy(alpha = 0.6f),
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
}