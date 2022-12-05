package com.isabri.dragonballandroidavanzado.data.remote

import com.isabri.dragonballandroidavanzado.data.remote.request.HeroesRequest
import com.isabri.dragonballandroidavanzado.domain.models.Hero
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface DragonBallAPI {

    @POST("api/heros/all")
    suspend fun getHeroes(@Body heroesRequest: HeroesRequest): List<Hero>
}