package com.isabri.dragonballandroidavanzado.data

import com.isabri.dragonballandroidavanzado.ui.heroesList.HeroesListState
import com.isabri.dragonballandroidavanzado.ui.login.LoginState


interface Repository {
    suspend fun getHeroes(heroName: String? = null): HeroesListState
    suspend fun getHeroesToCache(): HeroesListState
    suspend fun getToken(): LoginState
}