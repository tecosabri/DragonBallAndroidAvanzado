package com.isabri.dragonballandroidavanzado.data

import android.content.SharedPreferences
import android.security.KeyChain
import com.isabri.dragonballandroidavanzado.data.local.LocalDataSource
import com.isabri.dragonballandroidavanzado.data.mappers.Mappers
import com.isabri.dragonballandroidavanzado.data.remote.RemoteDataSource
import com.isabri.dragonballandroidavanzado.domain.models.Hero
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSourceImpl: LocalDataSource,
    private val sharedPreferences: SharedPreferences,
    private val mapper: Mappers
): Repository {

    companion object {
        val TOKEN = "TOKEN"
    }

    override suspend fun getHeroes(): List<Hero> {
        return mapper.mapRemoteToHeroesList(remoteDataSource.getHeroes())
    }

    override suspend fun getHeroesToCache(): List<Hero> {
        var localHeroes = localDataSourceImpl.getHeroes()
//        if(localHeroes.isEmpty()) {
            val remoteHeroes = remoteDataSource.getHeroes()
            localHeroes = mapper.mapRemoteToEntityHeroesList(remoteHeroes)
            localDataSourceImpl.insertHeroes(localHeroes)
//        }
        return mapper.mapEntityToHeroesList(localDataSourceImpl.getHeroes())
    }

    override suspend fun getToken(): String {
        val token = remoteDataSource.getToken()
        sharedPreferences.edit().putString(TOKEN, token).apply()
        return token
    }
}