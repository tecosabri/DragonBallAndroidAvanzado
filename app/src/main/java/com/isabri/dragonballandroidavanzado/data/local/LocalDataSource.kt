package com.isabri.dragonballandroidavanzado.data.local

import android.content.Context
import androidx.room.Room
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
}