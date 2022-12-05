package com.isabri.dragonballandroidavanzado.domain.models

import java.lang.ref.PhantomReference

data class Hero(
    val id: String,
    val name: String,
    val photo: String,
    val description: String,
    val favorite: String
)
