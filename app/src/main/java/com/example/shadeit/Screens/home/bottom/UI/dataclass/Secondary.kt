package com.example.shadeit.Screens.home.bottom.UI.dataclass

data class Secondary(
    val color: String,
    val is_perfect_match: Boolean,
    val reason: String,
    val suggested_color: String
) {
    // No-argument constructor for Firestore
    constructor() : this(
        color = "",
        is_perfect_match = false,
        reason = "",
        suggested_color = ""
    )
}