package com.isabri.dragonballandroidavanzado.data

import android.content.SharedPreferences
import com.isabri.dragonballandroidavanzado.data.local.LocalDataSource
import com.isabri.dragonballandroidavanzado.data.mappers.Mappers
import com.isabri.dragonballandroidavanzado.data.remote.RemoteDataSource
import com.isabri.dragonballandroidavanzado.data.dataState.HeroesListState
import com.isabri.dragonballandroidavanzado.data.dataState.LoginState
import com.isabri.dragonballandroidavanzado.domain.models.Hero
import com.isabri.dragonballandroidavanzado.domain.models.Location
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSourceImpl: LocalDataSource,
    private val sharedPreferences: SharedPreferences,
    private val mapper: Mappers
): Repository {

    companion object {
        val TOKEN = "TOKEN"
    }

    override suspend fun getHeroes(heroName: String?): HeroesListState {
        val heroesListState = remoteDataSource.getHeroes(heroName)
        heroesListState
            .onSuccess {
                return HeroesListState.Success(mapper.mapRemoteToHeroesList(heroesListState.getOrThrow()))
            }
        return HeroesListState.Failure("Error fetching heroes") // On failure
    }

    override suspend fun getHeroesToCache(): HeroesListState {
        var localHeroes = localDataSourceImpl.getHeroes()
        if(localHeroes.isEmpty()) {
            val heroesListState = getHeroes()
            when (heroesListState) {
                is HeroesListState.Failure -> return heroesListState
                is HeroesListState.Success -> {
                    localHeroes = mapper.mapHeroToEntityHeroesList(heroesListState.heroes)
                    localDataSourceImpl.insertHeroes(localHeroes)
                    return heroesListState
                }
            }
        }
        return HeroesListState.Success(mapper.mapEntityToHeroesList(localHeroes))
    }

    override suspend fun getToken(): LoginState {
        val token = remoteDataSource.getToken()
        token
            .onSuccess {
                sharedPreferences.edit().putString(TOKEN, token.getOrThrow()).apply()
                return LoginState.Success(token.getOrThrow())
            }
        return LoginState.Failure("Error while retrieving the token")
    }

    override suspend fun getLocations(heroId: String): List<Location> {
        val locations = remoteDataSource.getLocations(heroId)
        locations.onSuccess { return mapper.mapLocationsRemoteToLocations(locations.getOrThrow())}
        return emptyList() // On failure returns empty list
    }

    override suspend fun toggleFavoriteRemote(heroId: String) {
        remoteDataSource.toggleFavorite(heroId)
    }

    override fun updateHero(hero: Hero) {
        val heroEntity = mapper.mapHeroToHeroEntity(hero)
        localDataSourceImpl.updateHero(heroEntity)
    }
}