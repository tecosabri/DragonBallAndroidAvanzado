package com.isabri.dragonballandroidavanzado.data.remote

import com.isabri.dragonballandroidavanzado.data.remote.model.HeroRemote
import com.isabri.dragonballandroidavanzado.domain.models.Location

interface RemoteDataSource {
    suspend fun getToken(): Result<String>
    suspend fun getHeroes(heroName: String? = null): Result<List<HeroRemote>>
    suspend fun getLocations(heroId: String): Result<List<Location>>
}