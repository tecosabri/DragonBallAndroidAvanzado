package com.isabri.dragonballandroidavanzado.defaultData

import com.isabri.dragonballandroidavanzado.data.remote.model.HeroRemote

class Default {
    enum class Response {
        SUCCESS,
        NETWORK_ERROR
    }
    companion object {
        fun getHeroRemote(): HeroRemote {
            return HeroRemote("DefaultID", "DefaultName", "DefaultPhoto", "DefaultDescription", false)
        }

        fun getSharedPreferencesName(): String { return "SHARED_PREFERENCES"}

        fun getToken(): String { return "TOKEN"}
    }
}