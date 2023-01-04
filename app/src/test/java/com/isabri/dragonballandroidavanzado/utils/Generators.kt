package com.isabri.dragonballandroidavanzado.utils

import com.isabri.dragonballandroidavanzado.data.local.model.HeroEntity
import com.isabri.dragonballandroidavanzado.data.remote.model.HeroRemote
import com.isabri.dragonballandroidavanzado.domain.models.Hero


fun generateHerosRemote(): List<HeroRemote> {
    return (0 until 10).map {
        HeroRemote(
            "ID: $it",
            "Name $it",
            "Photo $it",
            "Description $it",
            false
        )
    }
}

fun generateHerosLocal(): List<HeroEntity> {
    return (0 until 10).map {
        HeroEntity(
            "ID: $it",
            "Name $it",
            "Photo $it",
            "Description $it",
            false
        )
    }
}


fun generateHeros(): List<Hero> {
    return (0 until 10).map {
        Hero(
            "ID: $it",
            "Name $it",
            "Photo $it",
            "Description $it",
            false
        )
    }
}

