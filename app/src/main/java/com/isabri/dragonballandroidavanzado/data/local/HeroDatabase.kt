package com.isabri.dragonballandroidavanzado.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.isabri.dragonballandroidavanzado.data.local.model.HeroEntity

@Database(entities = [HeroEntity::class], version = 1)
abstract class HeroDatabase: RoomDatabase() {

    abstract fun getDAO(): HeroDAO
}