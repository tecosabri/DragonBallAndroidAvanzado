package com.isabri.dragonballandroidavanzado.data

import com.isabri.dragonballandroidavanzado.data.remote.RemoteDataSource
import com.isabri.dragonballandroidavanzado.domain.models.Hero

class Repository {

    private val remoteDataSource = RemoteDataSource()

    suspend fun getHeroes(): List<Hero> {
        return remoteDataSource.getHeroes()
    }
}