package com.isabri.dragonballandroidavanzado.data.remote.model

import com.squareup.moshi.Json

data class LocationRemote(
    @Json(name = "id") val id: String,
    @Json(name = "longitud") val longitude: String,
    @Json(name = "latitud") val latitude: String,
    @Json(name = "dateShow") val dateShow: String
)

