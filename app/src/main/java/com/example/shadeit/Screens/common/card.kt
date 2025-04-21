package com.example.shadeit.frontend.common

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Card() {
    Card(
        onClick = { /*TODO*/ },
        modifier = Modifier.aspectRatio(1.3f).padding(1.dp),
        colors = CardDefaults.cardColors(Color.Blue),
        shape = RoundedCornerShape(12.dp)
    ) {



    }
}