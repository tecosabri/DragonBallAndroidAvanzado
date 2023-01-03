package com.isabri.dragonballandroidavanzado.fakes

import com.isabri.dragonballandroidavanzado.defaultData.Default
import com.isabri.dragonballandroidavanzado.data.remote.RemoteDataSource
import com.isabri.dragonballandroidavanzado.data.remote.model.HeroRemote
import com.isabri.dragonballandroidavanzado.data.remote.model.LocationRemote

class FakeRemoteDataSource: RemoteDataSource {
    override suspend fun getToken(): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getHeroes(heroName: String?): Result<List<HeroRemote>> {
        return when(heroName){
            null -> Result.success(listOf(Default.getHeroRemote(), Default.getHeroRemote(), Default.getHeroRemote()))
            Default.Response.SUCCESS.toString() -> Result.success(listOf(Default.getHeroRemote()))
            else -> Result.failure(Exception(Default.Response.NETWORK_ERROR.toString()))// Error
        }
    }

    override suspend fun getLocations(heroId: String): Result<List<LocationRemote>> {
        TODO("Not yet implemented")
    }

    override suspend fun toggleFavorite(heroId: String) {
        TODO("Not yet implemented")
    }
}