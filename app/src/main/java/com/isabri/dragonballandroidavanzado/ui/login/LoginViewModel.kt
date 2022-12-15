package com.isabri.dragonballandroidavanzado.ui.login

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isabri.dragonballandroidavanzado.data.Repository
import com.isabri.dragonballandroidavanzado.data.dataState.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository, private val sharedPreferences: SharedPreferences): ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState>
        get() = _loginState

    private fun getToken() {
        viewModelScope.launch (Dispatchers.IO) {
            val token = withContext(Dispatchers.IO) {
                repository.getToken()
            }
            setValueOnMainThread(token)
        }
    }

    fun login(user: String, password: String) {
        setValueOnMainThread(LoginState.Loading)
        if(!userIsValid(user)) return
        if(!passwordIsValid(password)) return
        if(sharedPreferences.getString("TOKEN", null) == null) {
            sharedPreferences.edit().putString("CREDENTIAL", getCredentials(user, password)).apply()
            getToken()
        }
        else setValueOnMainThread(LoginState.Success(sharedPreferences.getString("TOKEN", null)!!))
    }

    private fun userIsValid(user: String): Boolean {
        val regex = "^[A-Za-z0-9_\\-!#\$%&'*+/=?^`{|]+@[A-Za-z0-9\\-]+\\.[a-z]+\$".toRegex()
        if(!regex.matches(user)) {
            setValueOnMainThread(LoginState.InvalidUser("Invalid user $user"))
            return false
        }
        return true
    }

    private fun passwordIsValid(password: String): Boolean {
        val regex = "[0-9]{6}".toRegex()
        if(!regex.matches(password)) {
            setValueOnMainThread(LoginState.InvalidPassword("Invalid password"))
            return false
        }
        return true
    }

    private fun getCredentials(user: String, password: String): String {
        return Credentials.basic(user, password, StandardCharsets.UTF_8)
    }

    private fun setValueOnMainThread(value: LoginState) {
        viewModelScope.launch(Dispatchers.Main) {
            _loginState.value = value
        }
    }
}

