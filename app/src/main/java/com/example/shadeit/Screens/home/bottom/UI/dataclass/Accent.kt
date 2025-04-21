package com.example.shadeit.Screens.home.bottom.UI.dataclass

data class Accent(
    val color: String = "",
    val is_perfect_match: Boolean = false,
    val reason: String = "",
    val suggested_color: String = ""
) {
    constructor() : this("", false, "", "")
}