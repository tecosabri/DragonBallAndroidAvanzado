package com.isabri.dragonballandroidavanzado.data

import com.isabri.dragonballandroidavanzado.data.remote.RemoteDataSource

class Repository {

    private val remoteDataSource = RemoteDataSource()

    suspend fun getHeroes() {
        remoteDataSource.getHeroes()
    }
}