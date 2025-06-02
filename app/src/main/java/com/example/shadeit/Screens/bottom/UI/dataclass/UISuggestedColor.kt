package com.example.shadeit.Screens.bottom.UI.dataclass

data class UISuggestedColor(
    val additional_notes: List<String> = emptyList(),
    val all_colors: List<String> = emptyList(),
    val color_analysis: ColorAnalysis = ColorAnalysis(),
    val description_based: List<String> = emptyList(),
    val image_based: List<String> = emptyList(),
    val organized_palette: OrganizedPalette = OrganizedPalette(),
    val ui_recommendations: List<UiRecommendation> = emptyList()
) {
    // No-argument constructor for Firestore
    constructor() : this(
        additional_notes = emptyList(),
        all_colors = emptyList(),
        color_analysis = ColorAnalysis(),
        description_based = emptyList(),
        image_based = emptyList(),
        organized_palette = OrganizedPalette(),
        ui_recommendations = emptyList()
    )
}