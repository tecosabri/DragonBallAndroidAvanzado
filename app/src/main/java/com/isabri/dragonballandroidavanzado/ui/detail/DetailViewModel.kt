package com.isabri.dragonballandroidavanzado.ui.detail

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.isabri.dragonballandroidavanzado.data.Repository
import com.isabri.dragonballandroidavanzado.data.dataState.HeroesListState
import com.isabri.dragonballandroidavanzado.data.dataState.LocationsState
import com.isabri.dragonballandroidavanzado.domain.models.Hero
import com.isabri.dragonballandroidavanzado.domain.models.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.*
import javax.annotation.meta.When
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _state = MutableLiveData<HeroesListState>()
    val state: LiveData<HeroesListState>
        get() = _state

    fun getHeroes(heroName: String) {
        viewModelScope.launch {
            val heroListState = withContext(Dispatchers.IO) {
                repository.getHeroes(heroName)
            }
            when(heroListState) {
                is HeroesListState.Failure -> Log.d("LOCATIONS FETCHING", "Locations fetching error")
                is HeroesListState.Success -> {
                    val hero = heroListState.heroes.first()
                    val locations = withContext(Dispatchers.IO) {
                        repository.getLocations(hero.id)
                    }
                    hero.locations = locations
                }
            }
            setValueOnMainThread(heroListState)
        }
    }

    private fun setValueOnMainThread(value: HeroesListState) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = value
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMarker(location: Location): MarkerOptions {
        return MarkerOptions().position(getLatLng(location)).title(getTitle(location))
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTitle(location: Location): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val date = LocalDate.parse(location.dateShow, formatter)
        return "Seen the ${date.dayOfMonth} of ${Month.values()[date.monthValue].toString().lowercase(Locale.getDefault())}"
    }
    private fun getLatLng(location: Location): LatLng {
        return LatLng(location.latitude.toDouble(), location.longitude.toDouble())
    }
}
