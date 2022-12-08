package com.isabri.dragonballandroidavanzado.di

import android.content.Context
import androidx.room.Room
import com.isabri.dragonballandroidavanzado.data.local.HeroDAO
import com.isabri.dragonballandroidavanzado.data.local.HeroDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): HeroDatabase {
        return Room.databaseBuilder(
            context,
            HeroDatabase::class.java, "database-name"
        ).build()
    }

    @Provides
    fun provideDao(heroDatabase: HeroDatabase): HeroDAO {
        return heroDatabase.getDAO()
    }
}