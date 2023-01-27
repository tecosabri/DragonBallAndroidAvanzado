package com.isabri.dragonballandroidavanzado.data.dataState

import com.isabri.dragonballandroidavanzado.domain.models.Location

sealed class LocationsState {
    data class Success(val locations: List<Location>): LocationsState()
    data class Failure(val errorMessage: String): LocationsState()
    object Loading: LocationsState()
}