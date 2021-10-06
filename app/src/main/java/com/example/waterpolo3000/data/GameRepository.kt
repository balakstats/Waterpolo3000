package com.example.waterpolo3000.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(private val gameDao: GameDao) {

    fun getAllGames() = gameDao.getAll()

    fun getGameByGuid(guid: String) = gameDao.getGame(guid)

    suspend fun newGame(game: Game) = gameDao.insert(game)
}