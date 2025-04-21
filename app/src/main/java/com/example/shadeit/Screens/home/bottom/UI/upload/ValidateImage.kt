package com.example.shadeit.Screens.home.bottom.UI.upload

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

fun validateImage(context: Context, imageUri: Uri): Pair<Boolean, String> {
    // Step 1: Check the file size
    val cursor = context.contentResolver.query(imageUri, null, null, null, null)
    val sizeInBytes = cursor?.use {
        val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
        it.moveToFirst()
        it.getLong(sizeIndex)
    } ?: return Pair(false, "Unable to determine file size.")
    val sizeInMB = sizeInBytes / (1024.0 * 1024.0)

    // Step 2: Check the file format
    val fileExtension = context.contentResolver.getType(imageUri)?.substringAfterLast("/") ?: ""
    val validFormats = listOf("jpeg", "jpg", "png")
    if (fileExtension.lowercase() !in validFormats) {
        return Pair(false, "Invalid image format. Only JPEG, JPG, or PNG are allowed.")
    }

    // Step 3: Validate size
    return if (sizeInMB > 10) {
        Pair(false, "Image size exceeds 10MB.")
    } else {
        Pair(true, "Image is valid.")
    }
}