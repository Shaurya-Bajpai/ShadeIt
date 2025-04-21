package com.example.shadeit.supabase.api

import com.example.shadeit.Secrets
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = Secrets.SUPABASE_URL, // enter your supabase url here in "https://your-project.supabase.co" format
        supabaseKey = Secrets.SUPABASE_KEY  // enter your supabase key here in "your-anon-key" format
    ) {
        install(Storage)
    }
}