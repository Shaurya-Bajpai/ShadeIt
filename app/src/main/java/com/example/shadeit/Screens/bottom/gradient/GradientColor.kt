package com.example.shadeit.Screens.bottom.gradient

data class GradientColor(
    val id: Int = 0,
    val color: String,
    val colorStops: List<String> = emptyList(), // List of colors for multi-stop gradients
    val gradientAngle: Float = 0f, // Angle for linear gradient
    val timestamp: Long = System.currentTimeMillis(),
    val firestoreId: String? = null
) {
    // No-argument constructor required for Firestore
    constructor() : this(0, "", emptyList(), 0f, 0L, null)
}