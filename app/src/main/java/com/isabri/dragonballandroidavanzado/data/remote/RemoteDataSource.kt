package com.isabri.dragonballandroidavanzado.data.remote

import com.isabri.dragonballandroidavanzado.data.remote.model.HeroRemote

interface RemoteDataSource {
    suspend fun getHeroes(): List<HeroRemote>
}