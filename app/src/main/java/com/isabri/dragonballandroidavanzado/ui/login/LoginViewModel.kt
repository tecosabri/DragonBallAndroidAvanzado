package com.isabri.dragonballandroidavanzado.ui.login

import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isabri.dragonballandroidavanzado.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository, private val sharedPreferences: SharedPreferences): ViewModel() {

    val stateLiveData: MutableLiveData<LoginState> by lazy { MutableLiveData<LoginState>() }


    private fun getToken() {
        viewModelScope.launch (Dispatchers.IO) {
            val token = withContext(Dispatchers.IO) {
                repository.getToken()
            }
            Log.d("TOKEN", token)
        }
    }

    fun login(user: String, password: String) {
        if(sharedPreferences.getString("TOKEN", null) == null) {
            sharedPreferences.edit().putString("CREDENTIAL", getCredentials(user, password)).apply()
            getToken()
        }
        else setValueOnMainThread(LoginState.Success(sharedPreferences.getString("TOKEN", null)!!))
    }

    private fun getCredentials(user: String, password: String): String {
        return Credentials.basic(user, password, StandardCharsets.UTF_8)
    }

    sealed class LoginState {
        data class Success(val token: String): LoginState()
        data class Failure(val errorMessage: String): LoginState()
        object Loading: LoginState()
    }

    private fun setValueOnMainThread(value: LoginState) {
        viewModelScope.launch(Dispatchers.Main) {
            stateLiveData.value = value
        }
    }
}