package com.example.shadeit.session

import android.content.Context
import android.content.SharedPreferences

class Manager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveUserSession(userId: String, token: String) {
        val editor = prefs.edit()
        editor.putString("user_id", userId)
        editor.putString("token", token)
        editor.apply()
    }

    fun getUserId(): String? {
        return prefs.getString("user_id", null)
    }

    fun getIdToken(): String? {
        return prefs.getString("token", null)
    }

    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}