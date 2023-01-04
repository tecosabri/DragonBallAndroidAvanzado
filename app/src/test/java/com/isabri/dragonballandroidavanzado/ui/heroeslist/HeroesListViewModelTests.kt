package com.isabri.dragonballandroidavanzado.ui.heroeslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.isabri.dragonballandroidavanzado.data.Repository
import com.isabri.dragonballandroidavanzado.data.dataState.HeroesListState
import com.isabri.dragonballandroidavanzado.domain.models.Hero
import com.isabri.dragonballandroidavanzado.ui.heroesList.HeroesListViewModel
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

@OptIn(ExperimentalCoroutinesApi::class)
class HeroesListViewModelTests {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // SUT o UUT
    private lateinit var heroesListViewModel: HeroesListViewModel

    // Dependencies
    private lateinit var repository: Repository

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup(){
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `WHEN getSuperheros EXPECTS success returns list of superheros`() = runTest {
        // GIVEN
        repository = mockk()
        heroesListViewModel = HeroesListViewModel(repository)
        coEvery {  repository.getHeroesToCache() } returns HeroesListState.Success(generateHeros())

        // WHEN
        heroesListViewModel.getHeroes()
        val actualLiveData = heroesListViewModel.state.getOrAwaitValue()

        // THEN
        Truth.assertThat((actualLiveData as HeroesListState.Success).heroes).containsExactlyElementsIn(generateHeros())
    }

    @Test
    fun `WHEN filterLikedHeroes EXPECTS success returns heroesListStateSuccess with filtered heroes`() = runTest {
        // GIVEN a list with non favorite heroes
        repository = mockk()
        heroesListViewModel = HeroesListViewModel(repository)
        coEvery {  repository.getHeroesToCache() } returns HeroesListState.Success(generateHeros())
        heroesListViewModel.getHeroes()

        // WHEN
        lateinit var actualLiveData: HeroesListState
        lateinit var heroes: List<Hero>
        withContext(Dispatchers.IO) {
            actualLiveData = heroesListViewModel.state.getOrAwaitValue()
            heroes = heroesListViewModel.filterLikedHeroes().heroes
        }

        // THEN
        Truth.assertThat((actualLiveData as HeroesListState.Success).heroes).containsExactlyElementsIn(generateHeros())
        Truth.assertThat(heroes).hasSize(0)
    }
}