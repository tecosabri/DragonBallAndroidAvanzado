package com.isabri.dragonballandroidavanzado.data

import android.content.SharedPreferences
import com.isabri.dragonballandroidavanzado.data.local.LocalDataSource
import com.isabri.dragonballandroidavanzado.data.mappers.Mappers
import com.isabri.dragonballandroidavanzado.data.remote.RemoteDataSource
import com.isabri.dragonballandroidavanzado.data.dataState.HeroesListState
import com.isabri.dragonballandroidavanzado.data.dataState.LoginState
import com.isabri.dragonballandroidavanzado.data.remote.request.HeroesRequest
import com.isabri.dragonballandroidavanzado.domain.models.Location
import retrofit2.http.HEAD
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
        val heroesListState = getHeroes()
        if(localHeroes.isEmpty()) {
            when (heroesListState) {
                is HeroesListState.Failure -> return heroesListState
                is HeroesListState.Success -> {
                    localHeroes = mapper.mapHeroToEntityHeroesList(heroesListState.heroes)
                    localDataSourceImpl.insertHeroes(localHeroes)
                }
            }
        }
        return heroesListState
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

    override suspend fun toggleFavorite(heroId: String) {
        remoteDataSource.toggleFavorite(heroId)
    }
}