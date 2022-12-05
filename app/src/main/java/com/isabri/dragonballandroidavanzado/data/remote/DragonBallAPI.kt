package com.isabri.dragonballandroidavanzado.data.remote

import com.isabri.dragonballandroidavanzado.domain.models.Hero
import retrofit2.http.POST

interface DragonBallAPI {

    @POST("api/heros/all")
    suspend fun getHeroes(): List<Hero>
}