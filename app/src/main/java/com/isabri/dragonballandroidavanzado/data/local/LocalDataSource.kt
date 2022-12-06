package com.isabri.dragonballandroidavanzado.data.local

import android.content.Context
import androidx.room.Room
import com.isabri.dragonballandroidavanzado.data.local.model.HeroEntity
import com.isabri.dragonballandroidavanzado.data.remote.model.HeroRemote
import com.isabri.dragonballandroidavanzado.domain.models.Hero
import kotlinx.coroutines.GlobalScope

class LocalDataSource {

    private lateinit var dao: HeroDAO

    fun initDatabase(context: Context) {
        val database = Room.databaseBuilder(
            context,
            HeroDatabase::class.java, "database-name"
        ).build()
        dao = database.getDAO()
    }

    fun getHeroes(): List<HeroEntity> {
        return dao.getAllHeroes()
    }

    fun insertHeroes(entityHeroes: List<HeroEntity>) {
        dao.insertAll(entityHeroes)
    }
}