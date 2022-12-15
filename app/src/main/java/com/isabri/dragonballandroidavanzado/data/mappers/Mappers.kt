package com.isabri.dragonballandroidavanzado.data.mappers

import com.isabri.dragonballandroidavanzado.data.local.model.HeroEntity
import com.isabri.dragonballandroidavanzado.data.remote.model.HeroRemote
import com.isabri.dragonballandroidavanzado.data.remote.model.LocationRemote
import com.isabri.dragonballandroidavanzado.domain.models.Hero
import com.isabri.dragonballandroidavanzado.domain.models.Location
import javax.inject.Inject

class Mappers @Inject constructor() {

    fun mapRemoteToHeroesList(heroRemoteList: List<HeroRemote>): List<Hero> {
        return heroRemoteList.map { Hero(it.id, it.name, it.photo, it.description, it.favorite) }
    }

    fun mapRemoteToEntityHeroesList(heroRemoteList: List<HeroRemote>): List<HeroEntity> {
        return heroRemoteList.map { HeroEntity(it.id, it.name, it.photo, it.description, it.favorite) }
    }

    fun mapEntityToHeroesList(heroEntityList: List<HeroEntity>): List<Hero> {
        return heroEntityList.map { Hero(it.id, it.name, it.photo, it.description, it.favorite) }
    }

    fun mapHeroToEntityHeroesList(heroesList: List<Hero>): List<HeroEntity> {
        return heroesList.map { HeroEntity(it.id, it.name, it.photo, it.description, it.favorite) }
    }

    fun mapLocationsRemoteToLocations(locationsRemote: List<LocationRemote>): List<Location> {
        return locationsRemote.map { Location(it.id, it.longitude, it.latitude, it.dateShow) }
    }
}