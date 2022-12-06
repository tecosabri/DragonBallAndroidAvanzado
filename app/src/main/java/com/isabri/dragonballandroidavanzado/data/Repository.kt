package com.isabri.dragonballandroidavanzado.data

import android.content.Context
import com.isabri.dragonballandroidavanzado.data.local.LocalDataSource
import com.isabri.dragonballandroidavanzado.data.mappers.Mappers
import com.isabri.dragonballandroidavanzado.data.remote.RemoteDataSource
import com.isabri.dragonballandroidavanzado.domain.models.Hero

class Repository {

    private val remoteDataSource = RemoteDataSource()
    private val localDataSource = LocalDataSource()
    private val mapper = Mappers()

    suspend fun getHeroes(): List<Hero> {
        return mapper.mapRemoteToHeroesList(remoteDataSource.getHeroes())
    }

    suspend fun getHeroesToCache(): List<Hero> {
        var localHeroes = localDataSource.getHeroes()
        if(localHeroes.isEmpty()) {
            val remoteHeroes = remoteDataSource.getHeroes()
            localHeroes = mapper.mapRemoteToEntityHeroesList(remoteHeroes)
            localDataSource.insertHeroes(localHeroes)
        }
        return mapper.mapEntityToHeroesList(localDataSource.getHeroes())
    }

    fun initDatabase(context: Context) {
        localDataSource.initDatabase(context)
    }
}