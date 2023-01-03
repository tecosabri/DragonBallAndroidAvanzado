package com.isabri.dragonballandroidavanzado

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import com.isabri.dragonballandroidavanzado.data.Repository
import com.isabri.dragonballandroidavanzado.data.RepositoryImpl
import com.isabri.dragonballandroidavanzado.data.dataState.HeroesListState
import com.isabri.dragonballandroidavanzado.fakes.FakeRemoteDataSource
import com.isabri.dragonballandroidavanzado.data.local.LocalDataSource
import com.isabri.dragonballandroidavanzado.data.mappers.Mappers
import com.isabri.dragonballandroidavanzado.data.remote.RemoteDataSource
import com.isabri.dragonballandroidavanzado.defaultData.Default
import com.isabri.dragonballandroidavanzado.domain.models.Hero
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.lang.Exception

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryImplTests {

    private lateinit var repositoryImpl: Repository
    private lateinit var localDataSource: LocalDataSource
    private lateinit var fakeRemoteDataSource: RemoteDataSource
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mappers: Mappers

    @Before
    fun setUp() {
        fakeRemoteDataSource = FakeRemoteDataSource(Hero("id", "name", "photo", "description", true))
        localDataSource = mockk()
        sharedPreferences = ApplicationProvider.getApplicationContext<Context>()
            .getSharedPreferences(Default.getSharedPreferencesName(), Context.MODE_PRIVATE)
        mappers = Mappers()
        repositoryImpl = RepositoryImpl(
            fakeRemoteDataSource,
            localDataSource,
            sharedPreferences,
            mappers
        )
    }

    @Test
    fun `WHEN getHeroes with Response Success name THEN returns a list of a single hero`() =
        runTest {
            //GIVEN

            //WHEN
            val actual = repositoryImpl.getHeroes(Default.Response.SUCCESS.toString())

            //THEN
            assert(actual is HeroesListState.Success)
            val successList = actual as HeroesListState.Success
            Truth.assertThat(successList.heroes.size).isEqualTo(1)
            Truth.assertThat(successList.heroes.first().name)
                .isEqualTo(Default.getHeroRemote().name)
        }

    @Test
    fun `WHEN getHeroes with null name as response THEN returns a list of a three heroes`() =
        runTest {
            //GIVEN

            //WHEN
            val actual = repositoryImpl.getHeroes(null)

            //THEN
            assert(actual is HeroesListState.Success)
            val successList = actual as HeroesListState.Success
            Truth.assertThat(successList.heroes.size).isEqualTo(3)
            Truth.assertThat(successList.heroes.first().name)
                .isEqualTo(Default.getHeroRemote().name)
        }

    @Test()
    fun `WHEN getHeroes with Response Network Error THEN returns a list of a three heroes`() =
        runTest {
            //GIVEN

            //WHEN
            try {
                repositoryImpl.getHeroes(Default.Response.NETWORK_ERROR.toString())
            } catch (exception: Exception) {
                // THEN
                Truth.assertThat(exception.message).isEqualTo(Default.Response.NETWORK_ERROR.toString())
            }
        }
}