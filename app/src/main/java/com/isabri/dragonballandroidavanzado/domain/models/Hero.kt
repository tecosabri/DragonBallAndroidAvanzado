package com.isabri.dragonballandroidavanzado.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hero(
    val id: String,
    val name: String,
    val photo: String,
    val description: String,
    val favorite: Boolean
) : Parcelable
