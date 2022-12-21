package com.isabri.dragonballandroidavanzado.data.remote

import com.isabri.dragonballandroidavanzado.data.remote.model.HeroRemote
import com.isabri.dragonballandroidavanzado.data.remote.model.LocationRemote
import com.isabri.dragonballandroidavanzado.data.remote.request.HeroIdRequest
import com.isabri.dragonballandroidavanzado.data.remote.request.HeroesRequest
import com.isabri.dragonballandroidavanzado.data.remote.request.IdRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface DragonBallAPI {

    @POST("api/auth/login")
    suspend fun getToken(): String

    @POST("api/heros/all")
    suspend fun getHeroes(@Body heroesRequest: HeroesRequest): List<HeroRemote>

    @POST("api/heros/locations")
    suspend fun getLocations(@Body heroId: IdRequest): List<LocationRemote>

    @POST("api/data/herolike")
    suspend fun toggleFavorite(@Body heroesRequest: HeroIdRequest)
}