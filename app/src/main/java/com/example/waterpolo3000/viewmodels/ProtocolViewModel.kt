package com.example.waterpolo3000.viewmodels

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.waterpolo3000.data.*
import com.example.waterpolo3000.game.GameControl
import com.example.waterpolo3000.utilities.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// viewModel for protocol fragment

@HiltViewModel
class ProtocolViewModel @Inject internal constructor(
    gameEventRepository: GameEventRepository
) : ViewModel() {
    lateinit var db: AppDatabase
    var playerToBeUpdated: MutableList<Player> = mutableListOf()

    val protocolForTeamBlue: LiveData<List<ProtocolTeam>> =
        gameEventRepository.getProtocolForTeam(
            BLUE,
            EXCLUSION_TYPE_MINIMUM,
            EXCLUSION_TYPE_MAXIMUM,
            GOAL_TYPE_MINIMUM,
            GOAL_TYPE_MAXIMUM
        ).asLiveData()
    val protocolForTeamWhite: LiveData<List<ProtocolTeam>> =
        gameEventRepository.getProtocolForTeam(
            WHITE,
            EXCLUSION_TYPE_MINIMUM,
            EXCLUSION_TYPE_MAXIMUM,
            GOAL_TYPE_MINIMUM,
            GOAL_TYPE_MAXIMUM
        ).asLiveData()

    val protocolForPersonalFoul: LiveData<List<ProtocolGameEventType>> =
        gameEventRepository.getProtocolByGameEventType(EXCLUSION_TYPE_MINIMUM, EXCLUSION_TYPE_MAXIMUM).asLiveData()

    val protocolGoalType: LiveData<List<ProtocolGoalType>> =
        gameEventRepository.getProtocolGoalType(
            GOAL_TYPE_MINIMUM, GOAL_TYPE_MAXIMUM
        ).asLiveData()

    val editTeamWhite: LiveData<List<EditTeam>> =
        gameEventRepository.getPlayerNames(WHITE).asLiveData()
    val editTeamBlue: LiveData<List<EditTeam>> =
        gameEventRepository.getPlayerNames(BLUE).asLiveData()

    // result
    val goals: LiveData<GameResult> =
        gameEventRepository.getGameResult(GOAL_TYPE_MINIMUM, GOAL_TYPE_MAXIMUM, intArrayOf(1, 2, 3, 4)).asLiveData()

    val goalsFirstQuarter: LiveData<GameResult> =
        gameEventRepository.getGameResult(GOAL_TYPE_MINIMUM, GOAL_TYPE_MAXIMUM, intArrayOf(1)).asLiveData()

    val goalsSecondQuarter: LiveData<GameResult> =
        gameEventRepository.getGameResult(GOAL_TYPE_MINIMUM, GOAL_TYPE_MAXIMUM, intArrayOf(2)).asLiveData()

    val goalsThirdQuarter: LiveData<GameResult> =
        gameEventRepository.getGameResult(GOAL_TYPE_MINIMUM, GOAL_TYPE_MAXIMUM, intArrayOf(3)).asLiveData()

    val goalsFourthQuarter: LiveData<GameResult> =
        gameEventRepository.getGameResult(GOAL_TYPE_MINIMUM, GOAL_TYPE_MAXIMUM, intArrayOf(4)).asLiveData()

    fun updatePlayerName(guid: String, column: String, value: String) {
        var found = false
        playerToBeUpdated.forEach { player ->
            when (column) {
                "firstName" -> {
                    if (player.guid == guid) {
                        player.playerFirstName = value
                        found = true
                    }
                }
                "lastName" -> {
                    if (player.guid == guid) {
                        player.playerLastName = value
                        found = true
                    }
                }
            }
        }
        if (!found) {
            val player = Player(guid)
            when (column) {
                "firstName" -> {
                    player.playerFirstName = value
                    playerToBeUpdated.add(player)
                }
                "lastName" -> {
                    player.playerLastName = value
                    playerToBeUpdated.add(player)
                }
            }
        }
        Log.d(ContentValues.TAG, "size: ${playerToBeUpdated.size}")
    }

    fun storePlayerUpdated() {
        if(playerToBeUpdated.size>0) {
            playerToBeUpdated.forEach {
                viewModelScope.launch {
                    db.playerDao().updatePlayer(it.guid,it.playerFirstName,it.playerLastName,System.currentTimeMillis())
                }
            }
            playerToBeUpdated = mutableListOf()
        }
    }

    fun clearPlayerUpdate(){
        playerToBeUpdated = mutableListOf()
    }
}