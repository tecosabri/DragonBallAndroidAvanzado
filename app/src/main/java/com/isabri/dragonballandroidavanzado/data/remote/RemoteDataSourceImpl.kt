package com.isabri.dragonballandroidavanzado.data.remote

import com.isabri.dragonballandroidavanzado.data.remote.model.HeroRemote
import com.isabri.dragonballandroidavanzado.data.remote.request.HeroesRequest
import com.isabri.dragonballandroidavanzado.domain.models.Hero
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
}

