package com.example.shadeit.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shadeit.database.ColorRepository
import com.example.shadeit.supabase.viewmodel.SupaViewModel

class ColorViewModelFactory(
    private val repository: ColorRepository,
    private val supaViewmodel: SupaViewModel,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ColorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ColorViewModel(repository, supaViewmodel, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
