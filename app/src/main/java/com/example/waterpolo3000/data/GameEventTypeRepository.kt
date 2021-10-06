package com.example.waterpolo3000.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameEventTypeRepository @Inject constructor(
    private val gameEventsTypesDao: GameEventTypeDao
) {

//    fun getGameEventTypeById(id: Int) = gameEventsTypesDao.getTypeById(id)

//    fun getFirstType() = gameEventTypeDao.getFirst()
}