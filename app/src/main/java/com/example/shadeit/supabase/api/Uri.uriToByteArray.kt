package com.example.shadeit.supabase.api

import android.content.Context
import android.net.Uri
import java.io.IOException

@Throws(IOException::class)
fun Uri.uriToByteArray(context: Context) =
    context.contentResolver.openInputStream(this)?.use { it.buffered().readBytes() }
