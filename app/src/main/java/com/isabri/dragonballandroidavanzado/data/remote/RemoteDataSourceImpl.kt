package com.isabri.dragonballandroidavanzado.data.remote

import com.isabri.dragonballandroidavanzado.data.remote.model.HeroRemote
import com.isabri.dragonballandroidavanzado.data.remote.request.HeroesRequest
import com.isabri.dragonballandroidavanzado.domain.models.Hero
import javax.inject.Inject


class RemoteDataSourceImpl @Inject constructor(private val api: DragonBallAPI): RemoteDataSource {

    override suspend fun getToken(): Result<String> {
        return runCatching { api.getToken() }
    }

    override suspend fun getHeroes(): Result<List<HeroRemote>> {
        return runCatching { api.getHeroes(HeroesRequest()) }
    }
}

