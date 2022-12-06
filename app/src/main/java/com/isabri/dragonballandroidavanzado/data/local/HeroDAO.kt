package com.isabri.dragonballandroidavanzado.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.isabri.dragonballandroidavanzado.data.local.model.HeroEntity

@Dao
interface HeroDAO {
    @Query("SELECT * FROM heroEntities")
    fun getAllHeroes(): List<HeroEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(superHeroes: List<HeroEntity>)
}