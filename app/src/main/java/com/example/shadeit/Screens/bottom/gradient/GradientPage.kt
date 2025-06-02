package com.example.shadeit.Screens.bottom.gradient

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.shadeit.ui.theme.Grad
import com.example.shadeit.viewmodel.ColorViewModel

@Composable
fun GradientPage(selectedNumColors: (Int) -> Unit) {

    val numColorsList = (2..10).toList()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(6.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            contentPadding = PaddingValues(4.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(numColorsList) { numColors ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { selectedNumColors(numColors) },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Grad)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "$numColors Colors",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}