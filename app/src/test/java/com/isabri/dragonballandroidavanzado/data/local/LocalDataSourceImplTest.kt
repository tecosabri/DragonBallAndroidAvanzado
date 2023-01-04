package com.isabri.dragonballandroidavanzado.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import com.isabri.dragonballandroidavanzado.data.local.model.HeroEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4


@RunWith(AndroidJUnit4::class)
class LocalDataSourceImplTest{

    private lateinit var localDataSourceImpl: LocalDataSource
    private lateinit var context: Context
    private lateinit var heroDao: HeroDAO
    private lateinit var db: HeroDatabase

    @Before
    fun createDb() {
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, HeroDatabase::class.java).build()
        heroDao = db.getDAO()
        localDataSourceImpl = LocalDataSourceImpl(heroDao)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN getHeroes with empty db EXPECT empty list`() = runTest {
        // GIVEN

        // WHEN
        launch(Dispatchers.IO) {
            val actual = localDataSourceImpl.getHeroes()

            // THEN
            Truth.assertThat(actual).isEmpty()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN getHeroes with non empty db EXPECT non empty list`() = runTest {
        // GIVEN
        val heroes = listOf<HeroEntity>(HeroEntity("id", "name", "photo", "description", true))

        // WHEN
        launch(Dispatchers.IO) {
            localDataSourceImpl.insertHeroes(heroes)
            val actual = localDataSourceImpl.getHeroes()

            // THEN
            Truth.assertThat(actual).hasSize(1)
        }
    }
}