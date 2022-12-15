package com.isabri.dragonballandroidavanzado.data

import com.isabri.dragonballandroidavanzado.data.dataState.LocationsState
import com.isabri.dragonballandroidavanzado.data.dataState.HeroesListState
import com.isabri.dragonballandroidavanzado.data.dataState.LoginState


interface Repository {
    suspend fun getHeroes(heroName: String? = null): HeroesListState
    suspend fun getHeroesToCache(): HeroesListState
    suspend fun getToken(): LoginState
    suspend fun getLocations(heroId: String): LocationsState
}