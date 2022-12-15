package com.isabri.dragonballandroidavanzado.data.dataState

import com.isabri.dragonballandroidavanzado.domain.models.Hero

sealed class HeroesListState {
    data class Success(val heroes: List<Hero>): HeroesListState()
    data class Failure(val error: String): HeroesListState()
}