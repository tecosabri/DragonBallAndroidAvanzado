package com.isabri.dragonballandroidavanzado.data.local

import com.isabri.dragonballandroidavanzado.data.local.model.HeroEntity

interface LocalDataSource {
    fun getHeroes(): List<HeroEntity>
    fun insertHeroes(entityHeroes: List<HeroEntity>)
}