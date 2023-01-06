package com.isabri.dragonballandroidavanzado.data.local

import com.isabri.dragonballandroidavanzado.data.local.model.HeroEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val dao: HeroDAO): LocalDataSource {

    override fun getHeroes(): List<HeroEntity> {
        return dao.getAllHeroes()
    }

    override fun insertHeroes(entityHeroes: List<HeroEntity>) {
        dao.insertAll(entityHeroes)
    }
}