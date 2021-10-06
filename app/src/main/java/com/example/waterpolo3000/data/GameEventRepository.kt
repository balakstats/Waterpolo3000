package com.example.waterpolo3000.data

import android.content.ContentValues
import android.util.Log
import com.example.waterpolo3000.game.GameControl
import com.example.waterpolo3000.utilities.EXCLUSION_TYPE_MAXIMUM
import com.example.waterpolo3000.utilities.EXCLUSION_TYPE_MINIMUM
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameEventRepository @Inject constructor(private val gameEventDao: GameEventDao) {

//    fun getGameEvents(guid: String) = gameEventDao.getAllGameEventPlayer(guid)
//
//    fun getAll() = gameEventDao.getAll()
//
//    fun getProtocolForTeam(guid: String, cap: String, exclTypeMin: Int, exclTypeMax: Int, goalTypeMin: Int, goalTypeMax: Int) =
//        gameEventDao.getProtocolTeam(guid, cap, exclTypeMin, exclTypeMax, goalTypeMin, goalTypeMax)
//
//    fun getProtocolByGameEventType(guid: String, typeMin: Int, typeMax: Int) = gameEventDao.getProtocolOrderedByGameEventType(guid, typeMin, typeMax)
//    fun getProtocolGoalType(guid: String, typeMin: Int, typeMax: Int) = gameEventDao.getProtocolOrderedByGoals(guid, typeMin, typeMax)
//
//    fun getGameResult(guid: String, goalTypeMin: Int, goalTypeMax: Int, gameSection: IntArray) =
//        gameEventDao.getResult(guid, goalTypeMin, goalTypeMax, gameSection)
//
//    fun getExByPlayer(game: String, cap: String, number: Int) =
//        gameEventDao.getExclusionByPlayer(game, cap, number, EXCLUSION_TYPE_MINIMUM, EXCLUSION_TYPE_MAXIMUM)
//
//    fun getPlayerNames(game: String, cap: String) = gameEventDao.getNamesPlayer(game, cap)
//
//    fun getTimeoutByTeam(game: String, cap: String) = gameEventDao.getTimeout(game, cap)


    fun getGameEvents() = gameEventDao.getAllGameEventPlayer()

    fun getAll() = gameEventDao.getAll()

    fun getProtocolForTeam(cap: String, exclTypeMin: Int, exclTypeMax: Int, goalTypeMin: Int, goalTypeMax: Int) =
        gameEventDao.getProtocolTeam(cap, exclTypeMin, exclTypeMax, goalTypeMin, goalTypeMax)

    fun getProtocolByGameEventType(typeMin: Int, typeMax: Int) = gameEventDao.getProtocolOrderedByGameEventType(typeMin, typeMax)
    fun getProtocolGoalType(typeMin: Int, typeMax: Int) = gameEventDao.getProtocolOrderedByGoals(typeMin, typeMax)

    fun getGameResult(goalTypeMin: Int, goalTypeMax: Int, gameSection: IntArray) =
        gameEventDao.getResult(goalTypeMin, goalTypeMax, gameSection)

    fun getExByPlayer(cap: String, number: Int) =
        gameEventDao.getExclusionByPlayer(cap, number, EXCLUSION_TYPE_MINIMUM, EXCLUSION_TYPE_MAXIMUM)

    fun getPlayerNames(cap: String) = gameEventDao.getNamesPlayer(cap)

    fun getTimeoutByTeam(cap: String) = gameEventDao.getTimeout(cap)

}