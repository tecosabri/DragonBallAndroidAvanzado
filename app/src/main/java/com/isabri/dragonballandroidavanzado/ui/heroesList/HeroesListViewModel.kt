package com.isabri.dragonballandroidavanzado.ui.heroesList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isabri.dragonballandroidavanzado.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HeroesListViewModel: ViewModel() {

    private val repository = Repository()

    companion object {
        private val TAG = "HeroesListViewModel: "
    }

    fun getHeroes() {
        viewModelScope.launch {
            val heroes = withContext(Dispatchers.IO) {
                repository.getHeroes()
            }
            Log.d(TAG, heroes.toString())
        }
    }
}