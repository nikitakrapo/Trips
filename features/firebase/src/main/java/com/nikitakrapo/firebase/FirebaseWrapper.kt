package com.nikitakrapo.firebase

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object FirebaseWrapper {
    fun init(
        context: Context,
    ) {
        FirebaseApp.initializeApp(context)
    }

    val auth: FirebaseAuth
        get() = Firebase.auth
}