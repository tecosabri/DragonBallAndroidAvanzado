package com.isabri.dragonballandroidavanzado.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.isabri.dragonballandroidavanzado.domain.models.Hero

@Dao
interface HeroDAO {
    @Query("SELECT * FROM heroEntities")
    fun getAllHeroes(): List<Hero>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(superHeroes: List<Hero>)
}