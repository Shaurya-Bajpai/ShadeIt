package com.example.shadeit.database

data class Colors(
    val id: Int = 0,
    val firestoreId: String? = null,
    val color: String,
    val timestamp: Long = System.currentTimeMillis()
) {
    // No-argument constructor required for Firestore
    constructor() : this(0, null, "", 0L)
}
