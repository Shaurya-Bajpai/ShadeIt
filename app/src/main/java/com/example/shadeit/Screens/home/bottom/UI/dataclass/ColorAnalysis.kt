package com.example.shadeit.Screens.home.bottom.UI.dataclass

data class ColorAnalysis(
    val accent: Accent = Accent(),
    val background: Background = Background(),
    val primary: Primary = Primary(),
    val secondary: Secondary = Secondary(),
    val text: Text = Text()
) {
    constructor() : this(
        accent = Accent(),
        background = Background(),
        primary = Primary(),
        secondary = Secondary(),
        text = Text()
    )
}

