package com.isabri.dragonballandroidavanzado.data.dataState

sealed class LoginState {
    data class Success(val token: String): LoginState()
    data class Failure(val errorMessage: String): LoginState()
    data class InvalidUser(val errorMessage: String): LoginState()
    data class InvalidPassword(val errorMessage: String): LoginState()
    object Loading: LoginState()
}