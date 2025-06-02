package com.example.shadeit.Screens.bottom.UI.dataclass

data class Text(
    val color: String,
    val is_perfect_match: Boolean,
    val reason: String,
    val suggested_color: String
) {
    constructor() : this(
        color = "",
        is_perfect_match = false,
        reason = "",
        suggested_color = ""
    )
}