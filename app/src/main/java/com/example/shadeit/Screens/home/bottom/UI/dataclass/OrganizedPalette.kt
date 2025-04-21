package com.example.shadeit.Screens.home.bottom.UI.dataclass

data class OrganizedPalette(
    val accent: String,
    val additional: List<String>,
    val background: String,
    val primary: String,
    val secondary: String,
    val text: String,
    val ui_components: UiComponents
) {
    // No-argument constructor for Firestore
    constructor() : this(
        accent = "",
        additional = emptyList(),
        background = "",
        primary = "",
        secondary = "",
        text = "",
        ui_components = UiComponents()
    )
}