package com.isabri.dragonballandroidavanzado.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isabri.dragonballandroidavanzado.data.Repository
import com.isabri.dragonballandroidavanzado.data.dataState.HeroesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

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
            setValueOnMainThread(heroListState)
        }
    }

    private fun setValueOnMainThread(value: HeroesListState) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = value
        }
    }
}
