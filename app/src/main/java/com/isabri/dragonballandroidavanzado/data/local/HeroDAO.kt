package com.isabri.dragonballandroidavanzado.data.local

import androidx.annotation.WorkerThread
import androidx.room.*
import com.isabri.dragonballandroidavanzado.data.local.model.HeroEntity

@Dao
interface HeroDAO {
    @WorkerThread
    @Query("SELECT * FROM heroEntities")
    fun getAllHeroes(): List<HeroEntity>

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(superHeroes: List<HeroEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateHero(hero: HeroEntity)
}