package com.isabri.dragonballandroidavanzado.ui.heroesList

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isabri.dragonballandroidavanzado.data.Repository
import com.isabri.dragonballandroidavanzado.domain.models.Hero
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HeroesListViewModel: ViewModel() {

    private val repository = Repository()
    private val _heroes = MutableLiveData<List<Hero>>()
    val heroes: LiveData<List<Hero>>
        get() = _heroes

    companion object {
        private val TAG = "HeroesListViewModel: "
    }

    fun getHeroes() {
        viewModelScope.launch {
            val apiHeroes = withContext(Dispatchers.IO) {
                repository.getHeroesToCache()
            }
            _heroes.value = apiHeroes
            Log.d(TAG, heroes.toString())
        }
    }

    fun initDatabase(context: Context) {
        repository.initDatabase(context)
    }
}