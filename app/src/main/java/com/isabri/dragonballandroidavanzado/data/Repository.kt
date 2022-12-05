package com.isabri.dragonballandroidavanzado.data

import android.content.Context
import com.isabri.dragonballandroidavanzado.data.local.LocalDataSource
import com.isabri.dragonballandroidavanzado.data.remote.RemoteDataSource
import com.isabri.dragonballandroidavanzado.domain.models.Hero

class Repository {

    private val remoteDataSource = RemoteDataSource()
    private val localDataSource = LocalDataSource()

    suspend fun getHeroes(): List<Hero> {
        return remoteDataSource.getHeroes()
    }

    fun initDatabase(context: Context) {
        localDataSource.initDatabase(context)
    }
}