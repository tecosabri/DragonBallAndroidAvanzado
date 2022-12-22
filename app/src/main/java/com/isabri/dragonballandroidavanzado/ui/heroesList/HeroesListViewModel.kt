package com.isabri.dragonballandroidavanzado.ui.heroesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isabri.dragonballandroidavanzado.data.Repository
import com.isabri.dragonballandroidavanzado.data.dataState.HeroesListState
import com.isabri.dragonballandroidavanzado.domain.models.Hero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HeroesListViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _state = MutableLiveData<HeroesListState>()
    val state: LiveData<HeroesListState>
        get() = _state

    fun getHeroes() {
        viewModelScope.launch {
            val apiHeroes = withContext(Dispatchers.IO) {
                repository.getHeroesToCache()
            }
            setValueOnMainThread(apiHeroes)
        }
    }

    fun filterLikedHeroes(): HeroesListState.Success {
        val value = state.value as HeroesListState.Success
        val heroes = value.heroes
        val newState = HeroesListState.Success(heroes.filter { it.favorite })
        setValueOnMainThread(newState)
        return newState
    }

    private fun setValueOnMainThread(value: HeroesListState) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = value
        }
    }
}

