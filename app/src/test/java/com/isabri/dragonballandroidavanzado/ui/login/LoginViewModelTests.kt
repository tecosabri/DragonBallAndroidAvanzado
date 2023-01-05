package com.isabri.dragonballandroidavanzado.ui.login

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
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTests {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // SUT o UUT
    private lateinit var loginViewModel: LoginViewModel

    // Dependencies
    private lateinit var repository: Repository
    private lateinit var sharedPreferences: SharedPreferences

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup(){
        Dispatchers.setMain(mainThreadSurrogate)
        repository = mockk()
        sharedPreferences = ApplicationProvider.getApplicationContext<Context>()
            .getSharedPreferences(Default.getSharedPreferencesName(), Context.MODE_PRIVATE)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `WHEN login EXPECTS success returns login token`() = runTest {
        // GIVEN
        loginViewModel = LoginViewModel(repository, sharedPreferences)
        coEvery {  repository.getToken() } returns LoginState.Success("TOKEN")

        // WHEN
        loginViewModel.login("hisme14@gmail.com","123456")
        val actualLiveData = loginViewModel.loginState.getOrAwaitValue(latchCount = 2)

        // THEN
        Truth.assertThat((actualLiveData as LoginState.Success).token).isEqualTo("TOKEN")

    }
}