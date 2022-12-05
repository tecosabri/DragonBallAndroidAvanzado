package com.isabri.dragonballandroidavanzado.data.remote

import com.isabri.dragonballandroidavanzado.data.remote.request.HeroesRequest
import com.isabri.dragonballandroidavanzado.domain.models.Hero
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface DragonBallAPI {

    @POST("api/heros/all")
    @Headers("Authorization: Bearer eyJraWQiOiJwcml2YXRlIiwiYWxnIjoiSFMyNTYiLCJ0eXAiOiJKV1QifQ.eyJleHBpcmF0aW9uIjo2NDA5MjIxMTIwMCwiZW1haWwiOiJoaXNtZTE0QGdtYWlsLmNvbSIsImlkZW50aWZ5IjoiQTczOTVGRTQtNkYwMy00MEJBLUE3RDctMDRFMUE5NUNEMEM5In0.Q3hnm0KSxhyKJBYXeG5k6p-8CF8gYy9MIJHvrD56XSg")
    suspend fun getHeroes(@Body heroesRequest: HeroesRequest): List<Hero>
}