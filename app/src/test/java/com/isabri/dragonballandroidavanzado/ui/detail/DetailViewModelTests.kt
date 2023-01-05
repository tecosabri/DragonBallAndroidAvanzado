package com.isabri.dragonballandroidavanzado.ui.detail

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.isabri.dragonballandroidavanzado.data.Repository
import com.isabri.dragonballandroidavanzado.data.dataState.HeroesListState
import com.isabri.dragonballandroidavanzado.data.dataState.LoginState
import com.isabri.dragonballandroidavanzado.defaultData.Default
import com.isabri.dragonballandroidavanzado.domain.models.Hero
import com.isabri.dragonballandroidavanzado.domain.models.Location
import com.isabri.dragonballandroidavanzado.ui.heroesList.HeroesListViewModel
import com.isabri.dragonballandroidavanzado.utils.generateHero
import com.isabri.dragonballandroidavanzado.utils.generateHeros
import com.isabri.dragonballandroidavanzado.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTests {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // SUT o UUT
    private lateinit var detailViewModel: DetailViewModel

    // Dependencies
    private lateinit var repository: Repository

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup(){
        Dispatchers.setMain(mainThreadSurrogate)
        repository = mockk()
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `WHEN getHeroes EXPECTS returns hero`() = runTest {
        // GIVEN
        detailViewModel = DetailViewModel(repository)
        coEvery {  repository.getHeroes("HERONAME") } returns HeroesListState.Success(generateHero())
        coEvery {  repository.getLocations("ID: 0") } returns listOf(Location("id","0", "0", "0"))


        // WHEN
        detailViewModel.getHeroes("HERONAME")
        val actualLiveData = detailViewModel.state.getOrAwaitValue()

        // THEN
        Truth.assertThat((actualLiveData as HeroesListState.Success).heroes).hasSize(1)
    }

    @Test
    fun `WHEN getMarkers EXPECTS returns markers`() = runTest {
        // GIVEN
        detailViewModel = DetailViewModel(repository)
        coEvery {  repository.getHeroes("HERONAME") } returns HeroesListState.Success(generateHero())
        coEvery {  repository.getLocations("ID: 0") } returns listOf(Location("id","0", "0", "2022-02-20T00:00:00Z"))


        // WHEN
        detailViewModel.getHeroes("HERONAME")
        val actualLiveData = detailViewModel.state.getOrAwaitValue()
        val markers = detailViewModel.getMarkers((actualLiveData as HeroesListState.Success).heroes.first())

        // THEN
        Truth.assertThat(markers).hasSize(1)
    }
}