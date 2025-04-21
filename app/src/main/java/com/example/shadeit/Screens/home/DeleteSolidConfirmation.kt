package com.example.shadeit.Screens.home

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.shadeit.ui.theme.BBlue

@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Delete") },
        text = { Text(text = "Do you really want to delete colors?") },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(text = "Yes", color = BBlue)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "No", color = BBlue)
            }
        },
        titleContentColor = BBlue,
        textContentColor = Color.LightGray,
        containerColor = Color.Black,
        tonalElevation = 20.dp
    )
}