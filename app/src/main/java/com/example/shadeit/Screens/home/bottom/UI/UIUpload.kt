package com.example.shadeit.Screens.home.bottom.UI

data class UIUpload(
    val imageUrl: String = "",
    val description: String = "",
    val firestoreId: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
)