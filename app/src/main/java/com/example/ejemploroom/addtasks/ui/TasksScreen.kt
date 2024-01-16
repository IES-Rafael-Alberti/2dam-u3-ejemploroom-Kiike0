package com.example.ejemploroom.addtasks.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TasksScreen(tasksViewModel: TasksViewModel) {
    //Para poder alinear el botón abajo a la derecha del contenedor, le pasamos el modifier con la alineación desde su contenedor...
    Box(modifier = Modifier.fillMaxSize()) {
        FabDialog(
            Modifier.align(Alignment.BottomEnd))
    }
}

@Composable
fun FabDialog(
    modifier: Modifier
) {
    FloatingActionButton(
        onClick = {
            //Mostrar diálogo para añadir una tarea
        }, modifier = modifier.padding(16.dp)
    ) {
        Icon(Icons.Filled.Add, contentDescription = "")
    }
}
