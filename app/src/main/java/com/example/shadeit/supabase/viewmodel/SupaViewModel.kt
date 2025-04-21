package com.example.shadeit.supabase.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shadeit.supabase.api.SupabaseClient
import com.example.shadeit.supabase.api.UserState
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.launch

class SupaViewModel: ViewModel() {

    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState

    suspend fun uploadFile(bucketName: String, folderName: String, fileName: String, byteArray: ByteArray) {
        try {
            _userState.value = UserState.Loading
            val bucket = SupabaseClient.client.storage[bucketName]
            bucket.upload("$folderName/$fileName.jpg",byteArray,true)
            _userState.value = UserState.Success("File uploaded successfully!")
        } catch(e: Exception) {
            _userState.value = UserState.Error("Error: ${e.message}")
        }
    }

    fun readFile(bucketName: String, folderName: String, fileName: String): String? {
        return try {
            _userState.value = UserState.Loading
            val bucket = SupabaseClient.client.storage[bucketName]
            val url = bucket.publicUrl("$folderName/$fileName.jpg")
            _userState.value = UserState.Success("File read successfully!")
            url
        } catch (e: Exception) {
            _userState.value = UserState.Error("Error: ${e.message}")
            null
        }
    }

    fun deleteFile(bucketName: String, folderName: String, fileName: String) {
        viewModelScope.launch {
            try {
                val bucket = SupabaseClient.client.storage[bucketName]
                bucket.delete("$folderName/$fileName.jpg")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error deleting file: ${e.message}")
            }
        }
    }

}