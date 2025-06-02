package com.example.shadeit.Screens.bottom.UI.dataclass

data class UiComponents(
    val border: String,
    val button: String,
    val button_hover: String,
    val card_background: String,
    val header: String
) {
    // No-argument constructor for Firestore
    constructor() : this(
        border = "",
        button = "",
        button_hover = "",
        card_background = "",
        header = ""
    )
}