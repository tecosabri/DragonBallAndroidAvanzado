package com.isabri.dragonballandroidavanzado.data

import com.isabri.dragonballandroidavanzado.data.local.LocalDataSource
import com.isabri.dragonballandroidavanzado.data.mappers.Mappers
import com.isabri.dragonballandroidavanzado.data.remote.RemoteDataSource
import com.isabri.dragonballandroidavanzado.domain.models.Hero
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSourceImpl: LocalDataSource,
    private val mapper: Mappers
): Repository {

    override suspend fun getHeroes(): List<Hero> {
        return mapper.mapRemoteToHeroesList(remoteDataSource.getHeroes())
    }

    override suspend fun getHeroesToCache(): List<Hero> {
        var localHeroes = localDataSourceImpl.getHeroes()
        if(localHeroes.isEmpty()) {
            val remoteHeroes = remoteDataSource.getHeroes()
            localHeroes = mapper.mapRemoteToEntityHeroesList(remoteHeroes)
            localDataSourceImpl.insertHeroes(localHeroes)
        }
        return mapper.mapEntityToHeroesList(localDataSourceImpl.getHeroes())
    }
}