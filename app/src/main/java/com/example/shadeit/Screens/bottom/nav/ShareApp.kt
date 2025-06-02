package com.example.shadeit.Screens.bottom.nav

import android.content.Intent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ShareAppButton() {
    val context = LocalContext.current

    Button(onClick = {
        // Replace this with your actual app link on Play Store
        val appPackageName = context.packageName
        val shareText = "Check out this awesome app: https://play.google.com/store/apps/details?id=$appPackageName"

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Try this app")
            putExtra(Intent.EXTRA_TEXT, shareText)
        }

        // Show system share sheet
        context.startActivity(
            Intent.createChooser(intent, "Share via")
        )
    }) {
        Text("Share App")
    }
}
