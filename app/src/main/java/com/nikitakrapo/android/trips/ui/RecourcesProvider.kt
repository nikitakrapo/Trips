package com.nikitakrapo.android.trips.ui

import android.content.Context
import android.content.res.Resources
import androidx.annotation.StringRes
import javax.inject.Inject

class RecoursesProvider @Inject constructor(
    private val context: Context
) {

    @Throws(Resources.NotFoundException::class)
    fun getString(@StringRes stringId: Int): String {
        return context.getString(stringId)
    }
}