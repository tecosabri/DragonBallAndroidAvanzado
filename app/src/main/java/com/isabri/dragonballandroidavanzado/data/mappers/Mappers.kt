package com.isabri.dragonballandroidavanzado.data.mappers

import com.isabri.dragonballandroidavanzado.data.remote.model.HeroRemote
import com.isabri.dragonballandroidavanzado.domain.models.Hero

class Mappers {

    fun map(heroRemoteList: List<HeroRemote>): List<Hero> {
        return heroRemoteList.map { Hero(it.id, it.name, it.photo, it.description, it.favorite) }
    }
}