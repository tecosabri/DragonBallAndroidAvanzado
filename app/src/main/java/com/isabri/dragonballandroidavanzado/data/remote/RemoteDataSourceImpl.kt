package com.isabri.dragonballandroidavanzado.data.remote

import com.isabri.dragonballandroidavanzado.data.remote.model.HeroRemote
import com.isabri.dragonballandroidavanzado.data.remote.model.LocationRemote
import com.isabri.dragonballandroidavanzado.data.remote.request.HeroIdRequest
import com.isabri.dragonballandroidavanzado.data.remote.request.HeroesRequest
import com.isabri.dragonballandroidavanzado.data.remote.request.IdRequest
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val api: DragonBallAPI): RemoteDataSource {

    override suspend fun getToken(): Result<String> {
        return runCatching { api.getToken() }
    }

    override suspend fun getHeroes(heroName: String?): Result<List<HeroRemote>> {
        // Gets only one hero if the name is not null
        heroName?.apply { return kotlin.runCatching { api.getHeroes(HeroesRequest(heroName)) } }
        // Gets all heroes if no name -> name = null
        return runCatching { api.getHeroes(HeroesRequest()) }
    }

    override suspend fun getLocations(heroId: String): Result<List<LocationRemote>> {
        return runCatching { api.getLocations(IdRequest(heroId)) }
    }

    override suspend fun toggleFavorite(heroId: String) {
        api.toggleFavorite(HeroIdRequest(heroId))
    }
}

