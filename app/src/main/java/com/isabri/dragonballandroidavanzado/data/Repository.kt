package com.isabri.dragonballandroidavanzado.data

import com.isabri.dragonballandroidavanzado.domain.models.Hero

interface Repository {
    suspend fun getHeroes(): List<Hero>
    suspend fun getHeroesToCache(): List<Hero>
}