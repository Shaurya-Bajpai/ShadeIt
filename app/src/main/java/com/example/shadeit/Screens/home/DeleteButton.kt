package com.example.shadeit.Screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shadeit.ui.theme.BBlue

@Composable
fun AddColor(onClick: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier.padding(5.dp),
        contentColor = Color.White,
        backgroundColor = BBlue,
        onClick = onClick
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = null)
    }
}

@Composable
fun AddUIColor(onClick: () -> Unit, rotationAngle: Float) {
    FloatingActionButton(
        modifier = Modifier.padding(5.dp),
        contentColor = Color.White,
        backgroundColor = BBlue,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Upload Image",
            modifier = Modifier.rotate(rotationAngle)
        )
    }
}

@Composable
fun DeleteColor(onClick: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier.padding(5.dp),
        contentColor = Color.Red,
        backgroundColor = Color.White,
        onClick = onClick
    ) {
        Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
    }
}

@Preview(showBackground = true)
@Composable
fun DeleteSolidPreview() {
    DeleteColor(onClick = {})
}

@Preview(showBackground = true)
@Composable
fun AddSolidPreview() {
    AddColor(onClick = {})
}