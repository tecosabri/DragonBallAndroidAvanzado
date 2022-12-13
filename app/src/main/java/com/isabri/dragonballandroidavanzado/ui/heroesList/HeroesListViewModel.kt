package com.isabri.dragonballandroidavanzado.ui.heroesList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isabri.dragonballandroidavanzado.data.Repository
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

    companion object {
        private const val TAG = "HeroesListViewModel: "
    }

    fun getHeroes() {
        viewModelScope.launch {
            val apiHeroes = withContext(Dispatchers.IO) {
                repository.getHeroesToCache()
            }
            _state.postValue(apiHeroes)
        }
    }

}

sealed class HeroesListState {
    data class Success(val heroes: List<Hero>): HeroesListState()
    data class Failure(val error: String): HeroesListState()
}