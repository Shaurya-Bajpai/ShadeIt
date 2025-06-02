package com.example.shadeit.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shadeit.Screens.bottom.UI.UIUpload
import com.example.shadeit.Screens.bottom.UI.dataclass.UISuggestedColor
import com.example.shadeit.Screens.bottom.gradient.GradientColor
import com.example.shadeit.database.ColorRepository
import com.example.shadeit.database.Colors
import com.example.shadeit.network.NetworkUtils
import com.example.shadeit.supabase.api.uriToByteArray
import com.example.shadeit.supabase.viewmodel.SupaViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ColorViewModel(
    private val repository: ColorRepository,
    private val supaViewmodel: SupaViewModel,
    private val context: Context
) : ViewModel() {


    // Firebase Auth
    private val userId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid


    // Solid Color State
    private val _Solidcolors = MutableStateFlow<List<Colors>>(emptyList())
    val Solidcolors: StateFlow<List<Colors>> = _Solidcolors

    // Selection state for solid colors
    private val _selectedSolidColors = mutableStateListOf<Colors>()
    val selectedSolidColors: List<Colors> get() = _selectedSolidColors

    // Selection mode state for solid colors`
    private val _isSolidSelectionModeActive = mutableStateOf(false)
    val isSolidSelectionModeActive = _isSolidSelectionModeActive



    // Gradient Color State
    private val _GradientColors = MutableStateFlow<List<GradientColor>>(emptyList())
    val GradientColors: StateFlow<List<GradientColor>> = _GradientColors

    // Selection state for gradient colors
    private val _selectedGradientColors = mutableStateListOf<GradientColor>()
    val selectedGradientColors: List<GradientColor> get() = _selectedGradientColors

    // Selection mode state for gradient colors
    private val _isGradientSelectionModeActive = mutableStateOf(false)
    val isGradientSelectionModeActive = _isGradientSelectionModeActive



    // UI Upload State
    private val _uiImages = MutableStateFlow<List<UIUpload>>(emptyList())
    val uiImages: StateFlow<List<UIUpload>> = _uiImages

    // Selection state for UI colors
    private val _selectedUIColors = mutableStateListOf<UIUpload>()
    val selectedUIColors: List<UIUpload> get() = _selectedUIColors

    // Selection mode state for UI colors
    private val _isUISelectionModeActive = mutableStateOf(false)
    val isUISelectionModeActive = _isUISelectionModeActive

    // Suggested Colors State
    private val _UIsuggestedColors = MutableStateFlow<UISuggestedColor?>(null)
    val UIsuggestedColors: StateFlow<UISuggestedColor?> = _UIsuggestedColors

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    var selectedUIUpload by mutableStateOf<UIUpload?>(null)

    // Initialize the ViewModel
    init {
        fetchSolidColors()
    }

    // Solid Color Functions

    fun fetchSolidColors() {
        userId?.let { id ->
            viewModelScope.launch {
                repository.fetchSolidColors(id).collect { colorList ->
                    _Solidcolors.value = colorList
                    Log.d("ColorViewModel", "Solid Colors for user $id: $colorList")
                }
            }
        }
    }

    fun addSolidColor() {
        userId?.let { id ->
            if (NetworkUtils.isNetworkAvailable(context)) {
                viewModelScope.launch {
                    val newColor = String.format(
                        "#%06X",
                        (0xFFFFFF and (Math.random() * 0xFFFFFF).toInt())
                    )
                    Log.d("ColorViewModel", "Adding new solid color for user $id: $newColor")
                    repository.addSolidColor(
                        id,
                        Colors(
                            firestoreId = "",
                            color = newColor,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                    fetchSolidColors()
                }
            } else {
                Toast.makeText(context, "You are offline", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deleteSolidColors() {
        userId?.let { id ->
            if (NetworkUtils.isNetworkAvailable(context)) {
                val colorIdsToDelete = _selectedSolidColors.mapNotNull { it.firestoreId }

                viewModelScope.launch {
                    // Delete from Firestore using the repository
                    repository.deleteSolidColors(id, colorIdsToDelete)

                    // Clear selections after deleting
                    clearSolidSelections()

                    // Refresh the solid colors
                    fetchSolidColors()
                }
            } else {
                Toast.makeText(context, "You are offline", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Toggle selection for a color
    fun toggleSolidColorSelection(color: Colors) {
        if (_selectedSolidColors.contains(color)) {
            _selectedSolidColors.remove(color)
        } else {
            _selectedSolidColors.add(color)
        }

        // Update selection mode based on whether any colors are selected
        _isSolidSelectionModeActive.value = _selectedSolidColors.isNotEmpty()
    }

    // Clear all selections
    fun clearSolidSelections() {
        _selectedSolidColors.clear()
        _isSolidSelectionModeActive.value = false
    }


    // Gradient Color Functions

    fun fetchGradientColors(numColors: Int?) {
        userId?.let { id ->
            if (numColors == null) {
                Log.e("ColorViewModel", "Invalid parameter: numColors is null")
                return
            }

            viewModelScope.launch {
                try {
                    repository.fetchGradientColors(id, numColors).collect { colorList ->
                        _GradientColors.value = colorList
                    }
                } catch (e: Exception) {
                    Log.e("ColorViewModel", "Error fetching gradient colors", e)
                }
            }
        }
    }

    fun addGradientColor(numColors: Int?) {
        userId?.let { id ->
            if (NetworkUtils.isNetworkAvailable(context)) {
                viewModelScope.launch {
                    try {
                        val colors = List(numColors!!) {
                            String.format(
                                "#%06X",
                                (0xFFFFFF and (Math.random() * 0xFFFFFF).toInt())
                            )
                        }
                        val gradientColor = GradientColor(
                            color = colors.toString(),
                            colorStops = colors,
                            timestamp = System.currentTimeMillis()
                        )
                        Log.d(
                            "ColorViewModel",
                            "Adding new gradient color for user $id: $gradientColor"
                        )
                        repository.addGradientColor(id, numColors, gradientColor)
                        fetchGradientColors(numColors)
                    } catch (e: Exception) {
                        Log.e("ColorViewModel", "Error adding gradient color", e)
                    }
                }
            } else {
                Toast.makeText(context, "You are offline", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deleteGradientColors(numColors: Int?) {
        userId?.let { id ->
            if (NetworkUtils.isNetworkAvailable(context)) {
                val colorIdsToDelete = _selectedGradientColors.mapNotNull { it.firestoreId }

                viewModelScope.launch {
                    // Delete from Firestore using the repository
                    repository.deleteGradientColors(id, numColors!!, colorIdsToDelete)

                    // Clear selections after deleting
                    clearGradientSelections()

                    // Refresh the solid colors
                    fetchGradientColors(numColors)
                }
            } else {
                Toast.makeText(context, "You are offline", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Gradient Color Functions
    fun toggleGradientColorSelection(color: GradientColor) {
        if (_selectedGradientColors.contains(color)) {
            _selectedGradientColors.remove(color)
        } else {
            _selectedGradientColors.add(color)
        }

        // Update selection mode based on whether any colors are selected
        _isGradientSelectionModeActive.value = _selectedGradientColors.isNotEmpty()
    }

    // Clear all selections
    fun clearGradientSelections() {
        _selectedGradientColors.clear()
        _isGradientSelectionModeActive.value = false
    }




    // UI Color Functions

    fun fetchUIImage() {
        userId?.let { id ->
            if (NetworkUtils.isNetworkAvailable(context)) {
                viewModelScope.launch {
                    try {
                        repository.fetchUIImages(id).collect { images ->
                            _uiImages.value = images
                        }
                    } catch (e: Exception) {
                        Log.e("ColorViewModel", "Error fetching UI images", e)
                    }
                }
            }
        }
    }

    fun suggestColors(imageUri: Uri, description: String) {
        userId?.let { id ->
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    // Upload the image and description to Firebase
                    val subCollection = repository.colorsCollection.document(id).collection("UI")
                    val docRef = subCollection.document()
                    val firestoreId = docRef.id


                    val imageByteArray = imageUri.uriToByteArray(context) // Convert the image URI to a byte array
                    if (imageByteArray == null) {
                        Toast.makeText(context, "Failed to read image data", Toast.LENGTH_SHORT).show()
                        return@launch
                    }

                    supaViewmodel.uploadFile("images", id, firestoreId, imageByteArray) // Upload the image to Supabase Storage

                    val imageUrl = supaViewmodel.readFile("images", id, firestoreId) // Read the image URL from Supabase Storage
                    if (imageUrl == null) {
                        Toast.makeText(context, "Failed to retrieve image URL", Toast.LENGTH_SHORT).show()
                        return@launch
                    }

                    // Add the UI details to Firestore
                    val uiUpload = UIUpload(imageUrl = imageUrl, description = description, firestoreId = firestoreId)
                    repository.addUIImage(docRef, uiUpload) // Add the UI to Firestore

                    val suggestedColors = repository.suggestColors(imageUri, description, context) // Fetch suggested colors from the API
                    if(suggestedColors != null) {
                        repository.addUiSuggestedColors(id, firestoreId, suggestedColors)
                    }

                    fetchSuggestedColors(firestoreId)

                    selectedUIUpload = uiUpload

                } catch (e: Exception) {
                    Log.e("ColorViewModel", "Error suggesting colors", e)
                    Toast.makeText(context, "An error occurred in suggestColor(): ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun deleteUIColors() {
        userId?.let { id ->
            if (NetworkUtils.isNetworkAvailable(context)) {
                val uiIds = _selectedUIColors.mapNotNull { it.firestoreId }

                viewModelScope.launch {
                    // Delete from Firestore using the repository
                    repository.deleteUiSuggestedColors(id, uiIds)
                    repository.deleteUIImage(id, uiIds)

                    // Clear selections after deleting
                    clearUISelections()

                    // Refresh the UI images
                    fetchUIImage()
                }
            } else {
                Toast.makeText(context, "You are offline", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fetch suggested colors
    fun fetchSuggestedColors(firestoreId: String?) {
        userId?.let { id ->
            viewModelScope.launch {
                try {
                    val response = repository.fetchSuggestedColors(id, firestoreId!!)
                    _UIsuggestedColors.value = response

                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    // Gradient Color Functions
    fun toggleUISelection(color: UIUpload) {
        if (_selectedUIColors.contains(color)) {
            _selectedUIColors.remove(color)
        } else {
            _selectedUIColors.add(color)
        }

        // Update selection mode based on whether any colors are selected
        _isUISelectionModeActive.value = _selectedUIColors.isNotEmpty()
    }

    // Clear all selections
    fun clearUISelections() {
        _selectedUIColors.clear()
        _isUISelectionModeActive.value = false
    }
}
