package com.example.shadeit.api

import com.example.shadeit.Screens.home.bottom.UI.dataclass.UISuggestedColor
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("/api/suggest-colors")
    suspend fun suggestColors(
        @Part image: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): UISuggestedColor

    companion object {
        fun create(): ApiService {
            return RetrofitClient.instance.create(ApiService::class.java)
        }
    }
}
