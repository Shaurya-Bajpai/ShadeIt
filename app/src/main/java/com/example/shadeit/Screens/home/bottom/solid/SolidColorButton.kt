package com.example.shadeit.Screens.home.bottom.solid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shadeit.ui.theme.FloatColor
import com.example.shadeit.ui.theme.FloatTextColor
import com.example.shadeit.ui.theme.FloatTextColor2
import com.example.shadeit.viewmodel.ColorViewModel

@Composable
fun AddSolidColorButton(onAddClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.fillMaxSize()
    ) {
        TextButton(
            onClick = onAddClick,
            colors = ButtonDefaults.buttonColors(FloatColor),
//            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.padding(end = 10.dp, bottom = 6.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Add Color", fontSize = 18.sp, color = FloatTextColor2)
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier
                    .clip(CircleShape)
                    .background(FloatTextColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = FloatColor,
                    )
                }
            }
        }
    }
}