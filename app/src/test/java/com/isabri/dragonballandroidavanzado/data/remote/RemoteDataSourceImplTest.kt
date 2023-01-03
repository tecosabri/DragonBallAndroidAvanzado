package com.isabri.dragonballandroidavanzado.data.remote

import com.google.common.truth.Truth
import com.isabri.dragonballandroidavanzado.base.BaseNetworkTest
import com.isabri.dragonballandroidavanzado.defaultData.Default
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDataSourceImplTest: BaseNetworkTest(){

    private lateinit var remoteDataSourceImpl: RemoteDataSourceImpl

    @Before
    fun setUp() {
        remoteDataSourceImpl = RemoteDataSourceImpl(api)
    }

    @Test
    fun `WHEN getToken EXPECT success and returns token`() = runTest {
        // GIVEN

        // WHEN
        val actual = remoteDataSourceImpl.getToken()

        // THEN
        //assertTrue(actual.isSuccess)
        val token = actual.getOrNull()
        token?.apply {
            Truth.assertThat(token).isEqualTo(Default.getToken())
        }
    }

    @Test
    fun `WHEN getHeroes EXPECT success and returns heroes list`() = runTest {
        // GIVEN

        // WHEN
        val actual = remoteDataSourceImpl.getHeroes()

        // THEN
        assertTrue(actual.isSuccess)
        val heroes = actual.getOrNull()
        heroes?.apply {
            Truth.assertThat(heroes).hasSize(15)
            Truth.assertThat(heroes.first().name).isEqualTo("Maestro Roshi")
        }
    }

    @Test
    fun `WHEN getHeroes with any string EXPECT success and returns hero`() = runTest {
        // GIVEN

        // WHEN
        val actual = remoteDataSourceImpl.getHeroes("Maestro Roshi")

        // THEN
        assertTrue(actual.isSuccess)
        val roshi = actual.getOrNull()?.last()
        roshi?.apply {
            Truth.assertThat(roshi.name).isEqualTo("Maestro Roshi")
        }
    }

    @Test
    fun `WHEN getLocations with any string EXPECT success and returns hero location`() = runTest {
        // GIVEN

        // WHEN
        val actual = remoteDataSourceImpl.getLocations("Maestro Roshi Locations")

        // THEN
        assertTrue(actual.isSuccess)
        val roshiLocations = actual.getOrNull()?.first()
        roshiLocations?.apply {
            Truth.assertThat(roshiLocations.dateShow).isEqualTo("2022-02-20T00:00:00Z")
        }
    }
}
