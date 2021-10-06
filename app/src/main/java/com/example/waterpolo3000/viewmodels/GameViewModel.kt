package com.example.waterpolo3000.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.*
import com.example.waterpolo3000.data.*
import com.example.waterpolo3000.game.GameControl
import com.example.waterpolo3000.utilities.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

// viewModel for game fragment

@HiltViewModel
class GameViewModel @Inject internal constructor(
    gameEventRepository: GameEventRepository
) : ViewModel() {
    lateinit var db: AppDatabase

    // set the recyclreview for the gameEvents
    val gameEvents: LiveData<List<GameEventView>> = gameEventRepository.getGameEvents().asLiveData()

    val goals: LiveData<GameResult> = gameEventRepository.getGameResult(GOAL_TYPE_MINIMUM, GOAL_TYPE_MAXIMUM, intArrayOf(1, 2, 3, 4)).asLiveData()

    val timeoutForWhite: LiveData<TimeoutCount> = gameEventRepository.getTimeoutByTeam(WHITE).asLiveData()

    val timeoutForBlue: LiveData<TimeoutCount> = gameEventRepository.getTimeoutByTeam(BLUE).asLiveData()

    val exB1: LiveData<ExclResult> = gameEventRepository.getExByPlayer(BLUE, 1).asLiveData()
    val exB2: LiveData<ExclResult> = gameEventRepository.getExByPlayer(BLUE, 2).asLiveData()
    val exB3: LiveData<ExclResult> = gameEventRepository.getExByPlayer(BLUE, 3).asLiveData()
    val exB4: LiveData<ExclResult> = gameEventRepository.getExByPlayer(BLUE, 4).asLiveData()
    val exB5: LiveData<ExclResult> = gameEventRepository.getExByPlayer(BLUE, 5).asLiveData()
    val exB6: LiveData<ExclResult> = gameEventRepository.getExByPlayer(BLUE, 6).asLiveData()
    val exB7: LiveData<ExclResult> = gameEventRepository.getExByPlayer(BLUE, 7).asLiveData()
    val exB8: LiveData<ExclResult> = gameEventRepository.getExByPlayer(BLUE, 8).asLiveData()
    val exB9: LiveData<ExclResult> = gameEventRepository.getExByPlayer(BLUE, 9).asLiveData()
    val exB10: LiveData<ExclResult> = gameEventRepository.getExByPlayer(BLUE, 10).asLiveData()
    val exB11: LiveData<ExclResult> = gameEventRepository.getExByPlayer(BLUE, 11).asLiveData()
    val exB12: LiveData<ExclResult> = gameEventRepository.getExByPlayer(BLUE, 12).asLiveData()
    val exB13: LiveData<ExclResult> = gameEventRepository.getExByPlayer(BLUE, 13).asLiveData()

    val exW1: LiveData<ExclResult> = gameEventRepository.getExByPlayer(WHITE, 1).asLiveData()
    val exW2: LiveData<ExclResult> = gameEventRepository.getExByPlayer(WHITE, 2).asLiveData()
    val exW3: LiveData<ExclResult> = gameEventRepository.getExByPlayer(WHITE, 3).asLiveData()
    val exW4: LiveData<ExclResult> = gameEventRepository.getExByPlayer(WHITE, 4).asLiveData()
    val exW5: LiveData<ExclResult> = gameEventRepository.getExByPlayer(WHITE, 5).asLiveData()
    val exW6: LiveData<ExclResult> = gameEventRepository.getExByPlayer(WHITE, 6).asLiveData()
    val exW7: LiveData<ExclResult> = gameEventRepository.getExByPlayer(WHITE, 7).asLiveData()
    val exW8: LiveData<ExclResult> = gameEventRepository.getExByPlayer(WHITE, 8).asLiveData()
    val exW9: LiveData<ExclResult> = gameEventRepository.getExByPlayer(WHITE, 9).asLiveData()
    val exW10: LiveData<ExclResult> = gameEventRepository.getExByPlayer(WHITE, 10).asLiveData()
    val exW11: LiveData<ExclResult> = gameEventRepository.getExByPlayer(WHITE, 11).asLiveData()
    val exW12: LiveData<ExclResult> = gameEventRepository.getExByPlayer(WHITE, 12).asLiveData()
    val exW13: LiveData<ExclResult> = gameEventRepository.getExByPlayer(WHITE, 13).asLiveData()

    // Create a LiveData with a String
    val mainMinutes: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val mainSeconds: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val mainSecondsSmall: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val shotclockSeconds: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val shotclockSecondsSmall: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val currentGameSection: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val timeClickable: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val exclusionTime: MutableLiveData<String> by lazy { MutableLiveData<String>() }

//    fun setExclusionTime(player: String, value: Int){
//        Log.d(TAG, "ExclusionTime for $player: $value")
//    }

    fun processGameEvent(event: String, player: String) {
        Log.d(TAG, "GameViewModel.processGameEvent")
        GameControl.processGameEvent(event, player)
    }

    fun processTime(time: String) {
        Log.d(TAG, "GameViewModel.processTime")
        when (time) {
            "StartStop" -> GameControl.startStopCounter()
            "ShotclockSmall" -> GameControl.newShotclockSmall()
            "ShotclockBig" -> GameControl.newShotclockBig()
            "timeout" -> GameControl.startTimeout()
        }
    }

    fun setPauseTime(minutes: Int, seconds: Int){
        currentGameSection.value = "$minutes:$seconds"
    }

    fun setNewMainTime(minutes: Int, seconds: Int, secondsSmall: Int) {
        mainMinutes.value = minutes.toString()
        mainSeconds.value = if (seconds < 10) "0$seconds" else seconds.toString()
        mainSecondsSmall.value = secondsSmall.toString()
    }

    fun setNewShotclock(seconds: Int?, secondsSmall: Int?) {
        if (seconds == null) {
            shotclockSeconds.value = mainSeconds.value
            shotclockSecondsSmall.value = mainSecondsSmall.value
        } else {
            shotclockSeconds.value = if (seconds < 10) "0$seconds" else seconds.toString()
            shotclockSecondsSmall.value = secondsSmall.toString()
        }
    }

    fun timeControlAvailable(clickable: Boolean) {
        timeClickable.value = clickable
    }

    fun setCurrentGameSection(currentSection: Int) {
        currentGameSection.value = currentSection.toString()
    }

    fun init() {
        GameControl.myViewModel = this
        timeClickable.postValue(true)

        // main time
        mainMinutes.postValue(MyTimeConverter.getMinutesFromLong((DEFAULT_GAME_SECTION_LENGTH * 1000).toLong()).toString())
        val seconds = MyTimeConverter.getSecondsFromLong((DEFAULT_GAME_SECTION_LENGTH * 1000).toLong())
        mainSeconds.postValue(if (seconds.toString().length < 2) "0$seconds" else "$seconds")
        mainSecondsSmall.postValue("0")

        // shotclock
        val shSeconds = MyTimeConverter.getSecondsFromLong((DEFAULT_SHOTCLOCK_BIG_LENGTH * 1000).toLong())
        shotclockSeconds.postValue(if (shSeconds.toString().length < 2) "0$shSeconds" else "$shSeconds")
        shotclockSecondsSmall.postValue("0")

        // other
        currentGameSection.postValue("1")
        GameControl.init()
    }

    // store in db
    fun addGame(game: Game) {
        viewModelScope.launch {
            db.gameDao().insert(game)
        }
    }

    // store in db
    fun addPlayer(player: Player) {
        viewModelScope.launch {
            db.playerDao().insert(player)
        }
    }

    // store in db
    fun addPlayerList(playerList: List<Player>) {
        viewModelScope.launch {
            db.playerDao().insertAll(playerList)
        }
    }

    // store in db
    fun addParticipant(participant: Participant) {
        viewModelScope.launch {
            db.participantDao().insert(participant)
        }
    }

    // store in db
    fun addParticipantList(participantList: List<Participant>) {
        viewModelScope.launch {
            db.participantDao().insertAll(participantList)
        }
    }

    // store in db
    fun addTeam(team: Team) {
        viewModelScope.launch {
            db.teamDao().insert(team)
        }
    }

    // store in db
    fun addTeamList(teamList: List<Team>) {
        viewModelScope.launch {
            db.teamDao().insertAll(teamList)
        }
    }

    fun storeTimeout(cap: String, currentCountdown: Long){
        storeGameEventInternal(9,currentCountdown,GameControl.getCurrentGameSection(),if(cap==BLUE) GameControl.getTeamBlueParticipantGuid() else GameControl.getTeamWhiteParticipantGuid())
    }

    fun storeGameEvent(gameEventType: Int, countdown: Long, gameSection: Int, participant: String) {
        viewModelScope.launch {
            storeGameEventInternal(gameEventType, countdown, gameSection, participant)
        }
    }

    private fun storeGameEventInternal(gameEventType: Int, countdown: Long, gameSection: Int, participant: String) {
        Log.d(TAG, "storeGameEventInternal: ${GameControl.game.guid}")
        val currentGameEvent = GameEvent(
            UUID.randomUUID().toString(),
            GameControl.game.guid,
            gameSection,
            countdown,
            participant,
            gameEventType,
        )
        addGameEvent(currentGameEvent)
    }

    // store in db
    private fun addGameEvent(gameEvent: GameEvent) {
        Log.d(TAG, "GameViewModel.addGameEvent")
        viewModelScope.launch {
            db.gameEventDao().insert(gameEvent)
        }
    }

    // update the deleted column(set to true) of the specified gameEvent
    fun deleteGameEvent(guid: String) {
        Log.d(TAG, "GameViewModel.DeleteGameEvent")
        viewModelScope.launch {
            db.gameEventDao().updateToDelete(guid,System.currentTimeMillis())
        }
    }

    fun initAll(game: Game, teamList: List<Team>, playerList: List<Player>, participantList: List<Participant>) {
        viewModelScope.launch {
            db.gameDao().insert(game)
            db.teamDao().insertAll(teamList)
            db.playerDao().insertAll(playerList)
            db.participantDao().insertAll(participantList)
        }
    }

    fun updateGameEventTime(gameEvent: GameEventView, newTime: Long){
        Log.d(TAG, "Check: ${gameEvent.number}")
        viewModelScope.launch {
            val newGameEvent = GameEvent(
                UUID.randomUUID().toString(),
                GameControl.game.guid,
                gameEvent.gameSection.toInt(),
                newTime,
                GameControl.getParticipantByCapNumber(gameEvent.cap,if(gameEvent.number=="") 0 else gameEvent.number.toInt()),
                gameEvent.gameEventType
            )
            db.gameEventDao().insert(newGameEvent)
            db.gameEventDao().updateToDelete(gameEvent.guid,System.currentTimeMillis())
        }
    }

    fun newGame(){
        GameControl.newGame()
    }
}