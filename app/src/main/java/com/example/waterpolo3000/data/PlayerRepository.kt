package com.example.waterpolo3000.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerRepository @Inject constructor(private val playerDao: PlayerDao) {

    fun getAllPlayer() = playerDao.getAll()

    fun getPlayerByGuid(guid: String) = playerDao.getPlayer(guid)

}