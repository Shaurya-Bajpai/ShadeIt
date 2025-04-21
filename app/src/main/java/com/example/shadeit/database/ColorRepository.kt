package com.example.shadeit.database

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.shadeit.Screens.home.bottom.UI.UIUpload
import com.example.shadeit.Screens.home.bottom.UI.dataclass.UISuggestedColor
import com.example.shadeit.Screens.home.bottom.gradient.GradientColor
import com.example.shadeit.api.ApiService
import com.example.shadeit.supabase.viewmodel.SupaViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ColorRepository(private val apiService: ApiService) {

    private val supaViewModel = SupaViewModel()

    private val db = FirebaseFirestore.getInstance()
    internal val colorsCollection = db.collection("colors")

    // Solid Color Functions
    fun fetchSolidColors(userId: String): Flow<List<Colors>> = flow {
        try {
            val snapshot = colorsCollection.document(userId).collection("Solid").get().await()
            val colors = snapshot.documents.mapNotNull { it.toObject(Colors::class.java) }
                .sortedByDescending { it.timestamp }
            emit(colors)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    suspend fun addSolidColor(userId: String, color: Colors) {
        try {
            val subCollection = colorsCollection.document(userId).collection("Solid")
            val docRef = subCollection.document()
            val colorData = color.copy(firestoreId = docRef.id)
            docRef.set(colorData).await()
            Log.d("ColorRepository", "Added color for user $userId: $colorData")
        } catch (e: Exception) {
            Log.e("ColorRepository", "Error adding color for user $userId", e)
        }
    }

    suspend fun deleteSolidColors(userId: String, colorIds: List<String>) {
        try {
            val subCollection = colorsCollection.document(userId).collection("Solid")
            colorIds.forEach { id ->
                subCollection.document(id).delete().await()
            }
            Log.d("ColorRepository", "Deleted colors for user $userId: $colorIds")
        } catch (e: Exception) {
            Log.e("ColorRepository", "Error deleting colors for user $userId", e)
        }
    }


    // Gradient Color Functions
    suspend fun addGradientColor(userId: String, numColors: Int, gradientColor: GradientColor) {
        try {
            val subCollection = colorsCollection.document(userId).collection("Gradient")
                .document("Combination of $numColors Colors").collection("List")
            val docRef = subCollection.document()
            val colorData = gradientColor.copy(firestoreId = docRef.id)
            docRef.set(colorData).await()
            Log.d("ColorRepository", "Added gradient color for user $userId: $colorData")
        } catch (e: Exception) {
            Log.e("ColorRepository", "Error adding gradient color for user $userId", e)
        }
    }

    fun fetchGradientColors(userId: String, numColors: Int): Flow<List<GradientColor>> = flow {
        try {
            val snapshot = colorsCollection.document(userId).collection("Gradient")
                .document("Combination of $numColors Colors").collection("List").get().await()
            val colors = snapshot.documents.mapNotNull { it.toObject(GradientColor::class.java) }
                .sortedByDescending { it.timestamp }
            emit(colors)
            Log.d("ColorRepository", "Emitted gradient colors for user $userId: $colors")
        } catch (e: Exception) {
            Log.e("ColorRepository", "Error fetching gradient colors for user $userId", e)
            emit(emptyList())
        }
    }

    suspend fun deleteGradientColors(userId: String, numColors: Int, colorIds: List<String>) {
        try {
            val subCollection = colorsCollection.document(userId).collection("Gradient")
                .document("Combination of $numColors Colors").collection("List")
            colorIds.forEach { id ->
                subCollection.document(id).delete().await()
            }
            Log.d("ColorRepository", "Deleted colors for user $userId: $colorIds")
        } catch (e: Exception) {
            Log.e("ColorRepository", "Error deleting colors for user $userId", e)
        }
    }




    // UI Color Functions
    suspend fun addUIImage(docRef: DocumentReference, uiUpload: UIUpload): String? {
        return try {
            docRef.set(uiUpload).await()
//            Log.d("ColorRepository", "Added UI image for user ${docRef.id}: $uiUpload")

            docRef.id.toString()
        } catch (e: Exception) {
            Log.e("ColorRepository", "Error adding UI image for user", e)
            null
        }
    }

    fun fetchUIImages(userId: String): Flow<List<UIUpload>> = flow {
        try {
            val snapshot = colorsCollection.document(userId).collection("UI").get().await()
            val uiImages = snapshot.documents.mapNotNull { it.toObject(UIUpload::class.java) }
                .sortedByDescending { it.timestamp }
            emit(uiImages)
        } catch (e: Exception) {
            Log.e("ColorRepository", "Error fetching UI images for user $userId", e)
            emit(emptyList())
        }
    }

    suspend fun deleteUIImage(userId: String, UiIds: List<String>) {
        try {
            val subCollection = colorsCollection.document(userId).collection("UI")
            UiIds.forEach { id ->
                supaViewModel.deleteFile("images", userId, id)
                subCollection.document(id).delete().await()
            }
            Log.d("ColorRepository", "Deleted UI image for user $userId: $UiIds")
        } catch (e: Exception) {
            Log.e("ColorRepository", "Error deleting UI image for user $userId", e)
        }
    }

    suspend fun addUiSuggestedColors(userId: String, firestoreId: String, suggestedColors: UISuggestedColor): Boolean {
        return try {
            val subCollection = colorsCollection.document(userId).collection("UI")
            val imageDoc = subCollection.document(firestoreId)

            // Store the suggested colors under the field named `firestoreId`
            val colorDoc = imageDoc.collection("SuggestedColors").document(firestoreId)
            colorDoc.set(suggestedColors).await()

            Log.d("ColorRepository", "Added suggested colors for user $userId under field $firestoreId")
            true
        } catch (e: Exception) {
            Log.e("ColorRepository", "Error adding suggested colors for user $userId", e)
            false
        }
    }

    suspend fun fetchSuggestedColors(userId: String, firestoreId: String): UISuggestedColor? {
        return try {
            val subCollection = colorsCollection.document(userId).collection("UI")
            val imageDoc = subCollection.document(firestoreId)
            val snapshot = imageDoc.collection("SuggestedColors").get().await()
            val color = snapshot.documents.firstOrNull()?.toObject(UISuggestedColor::class.java)
            color // return the suggested colors
        } catch (e: Exception) {
            Log.e("ColorRepository", "Error fetching suggested colors", e)
            null
        }
    }

    suspend fun deleteUiSuggestedColors(userId: String, UiIds: List<String>) {
        try {
            val subCollection = colorsCollection.document(userId).collection("UI")
            UiIds.forEach { id ->
                val imageDoc = subCollection.document(id)
                imageDoc.collection("SuggestedColors").document(id).delete().await()
            }
            Log.d("ColorRepository", "Deleted suggested colors for user $userId: $UiIds")
        } catch (e: Exception) {
            Log.e("ColorRepository", "Error deleting suggested colors for user $userId", e)
        }
    }

    // Fetch all UI images and their suggested colors from API
    suspend fun suggestColors(imageUri: Uri, description: String, context: Context): UISuggestedColor? {
        val imageFile = uriToFile(imageUri, context) // Convert Uri to File
        val requestImage = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull()) // Create RequestBody for the image
        val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestImage) // Create MultipartBody.Part for the image
        val descriptionPart = description.toRequestBody("text/plain".toMediaTypeOrNull()) // Create RequestBody for the description
//        Log.d("ColorRepository", "Image part: $imagePart")
//        Log.d("ColorRepository", "Description part: $description")
        return try {
            val response = apiService.suggestColors(imagePart, descriptionPart) // Call the API
            Log.d("ColorRepository", "API Response: $response")
            if (response == null) {
                Log.e("ColorRepository", "No response received from the API")
                null
            } else {
                response
            }
        } catch (e: Exception) {
            Log.e("ColorRepository", "Error calling API: ${e.message}", e)
            null
        }
    }

    // Convert Uri to File
    fun uriToFile(uri: Uri, context: Context): File {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: throw IllegalArgumentException("Unable to open URI")
        val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
        val outputStream = tempFile.outputStream()
        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return tempFile
    }

}