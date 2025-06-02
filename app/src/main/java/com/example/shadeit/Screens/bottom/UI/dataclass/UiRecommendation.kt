package com.example.shadeit.Screens.bottom.UI.dataclass

data class UiRecommendation(
    val color: String,
    val component: String
) {
    // No-argument constructor for Firestore
    constructor() : this("", "")
}