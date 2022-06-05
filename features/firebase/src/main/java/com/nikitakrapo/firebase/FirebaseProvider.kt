package com.nikitakrapo.firebase

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseProvider @Inject constructor() {

    @Volatile
    private var isInitialized = false

    fun init(
        context: Context,
    ) {
        if (!isInitialized) {
            FirebaseApp.initializeApp(context)
            isInitialized = true
        }
    }

    val auth: FirebaseAuth
        get() = Firebase.auth
}