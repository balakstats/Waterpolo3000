package com.example.waterpolo3000.game

import android.content.ContentValues.TAG
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.CountDownTimer
import android.util.Log
import com.example.waterpolo3000.data.*
import com.example.waterpolo3000.utilities.*
import com.example.waterpolo3000.viewmodels.GameViewModel
import java.util.*

class GameControl() {
    companion object {
        lateinit var myViewModel: GameViewModel

        // time related stuff
        var gameSectionLength = DEFAULT_GAME_SECTION_LENGTH
        var currentCountdown: Long = gameSectionLength.toLong() * 1000

        private var shotclockLongLength = DEFAULT_SHOTCLOCK_BIG_LENGTH
        private var shotclockShortLength = DEFAULT_SHOTCLOCK_SMALL_LENGTH
        var currentCountdownShotclock: Long = shotclockLongLength.toLong() * 1000

        private var gameStarted = false
        private var timeIsRunning = false
        private var currentGameSection = 1
        var numberOfGameSection = DEFAULT_NUMBER_OF_GAME_SECTION
        private var maxGameSection = numberOfGameSection

        var timerCountdown: CountDownTimer? = null
        var timerPause: CountDownTimer? = null
        var currentGameGuid = UUID.randomUUID().toString()
        var savedCountdown: Long = 0

        var game = Game(currentGameGuid)
        private var teamBlue = Team(UUID.randomUUID().toString())
        private var teamWhite = Team(UUID.randomUUID().toString())
        var playersListBlue = listOf(
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
        )
        var playersListWhite = listOf(
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
            Player(UUID.randomUUID().toString()),
        )
        var playersListAll = playersListBlue + playersListWhite
        var participantListBlue = listOf(
            Participant(UUID.randomUUID().toString(), game.guid, teamBlue.guid, BLUE, 0, teamBlue.guid, FUNCTION_TYPE_OTHER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[0].guid, BLUE, 1, teamBlue.guid, FUNCTION_TYPE_GOALKEEPER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[1].guid, BLUE, 2, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[2].guid, BLUE, 3, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[3].guid, BLUE, 4, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[4].guid, BLUE, 5, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[5].guid, BLUE, 6, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[6].guid, BLUE, 7, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[7].guid, BLUE, 8, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[8].guid, BLUE, 9, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[9].guid, BLUE, 10, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[10].guid, BLUE, 11, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[11].guid, BLUE, 12, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[12].guid, BLUE, 13, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER)
        )
        var participantListWhite = listOf(
            Participant(UUID.randomUUID().toString(), game.guid, teamWhite.guid, WHITE, 0, teamWhite.guid, FUNCTION_TYPE_OTHER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[0].guid, WHITE, 1, teamWhite.guid, FUNCTION_TYPE_GOALKEEPER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[1].guid, WHITE, 2, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[2].guid, WHITE, 3, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[3].guid, WHITE, 4, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[4].guid, WHITE, 5, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[5].guid, WHITE, 6, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[6].guid, WHITE, 7, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[7].guid, WHITE, 8, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[8].guid, WHITE, 9, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[9].guid, WHITE, 10, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[10].guid, WHITE, 11, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[11].guid, WHITE, 12, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
            Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[12].guid, WHITE, 13, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER)
        )
        var participantListAll = participantListBlue + participantListWhite

        val excutionTimeOffset = mutableMapOf<String, Long>()

        fun init() {
            Log.d(TAG, "GameControl.Init")
            setDefaults()

            // create all initial records for the db
            teamBlue.teamName = "Mannschaft blau"
            teamBlue.teamLocation = "Blaudorf"
            teamWhite.teamName = "Mannschaft weiss"
            teamWhite.teamLocation = "Weissdorf"
            myViewModel.initAll(game, listOf(teamBlue, teamWhite), playersListAll, participantListAll)
        }

        fun newGame() {
            currentCountdown = gameSectionLength.toLong() * 1000
            currentCountdownShotclock = shotclockLongLength.toLong() * 1000
            gameStarted = false
            timeIsRunning = false
            currentGameSection = 1
            maxGameSection = numberOfGameSection
            myViewModel.currentGameSection.value = currentGameSection.toString()
            timerCountdown = null
            timerPause = null
            currentGameGuid = UUID.randomUUID().toString()
            savedCountdown = 0
            game = Game(currentGameGuid)
            teamBlue = Team(UUID.randomUUID().toString())
            teamWhite = Team(UUID.randomUUID().toString())
            teamBlue.teamName = "Mannschaft blau"
            teamBlue.teamLocation = "Blaudorf"
            teamWhite.teamName = "Mannschaft weiss"
            teamWhite.teamLocation = "Weissdorf"
            createPlayerList()
            playersListAll = playersListBlue + playersListWhite
            createParticipantList()
            participantListAll = participantListBlue + participantListWhite
            myViewModel.initAll(game, listOf(teamBlue, teamWhite), playersListAll, participantListAll)
            myViewModel.timeControlAvailable(true)

            setMainTimeDefaults()
            setShotclockDefaults(shotclockLongLength)

            val seconds = gameSectionLength % 60
            val secondsString = if (seconds < 10) "0$seconds" else "$seconds"
            ProcessBT.sendMessageToMainBoard("timeGame%${gameSectionLength / 60}:$secondsString%default")
            ProcessBT.sendMessageToAllShotclock("time%${gameSectionLength / 60}:$secondsString%default")
            Thread.sleep(200)
            ProcessBT.sendMessageToAllShotclock("shotclock%$shotclockLongLength%default%0")
            Log.d(TAG, "NewGame: $currentGameGuid")

            Thread.sleep(100)
            playersListBlue.forEachIndexed { index, _ ->
                ProcessBT.sendMessageToMainBoard("player%$BLUE%${index + 1}%0")
                Thread.sleep(100)
            }
            playersListWhite.forEachIndexed { index, _ ->
                ProcessBT.sendMessageToMainBoard("player%$WHITE%${index + 1}%0")
                Thread.sleep(100)
            }
        }

        private fun createPlayerList() {
            playersListBlue = listOf(
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
            )
            playersListWhite = listOf(
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
                Player(UUID.randomUUID().toString()),
            )
        }

        private fun createParticipantList() {
            participantListBlue = listOf(
                Participant(UUID.randomUUID().toString(), game.guid, teamBlue.guid, BLUE, 0, teamBlue.guid, FUNCTION_TYPE_OTHER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[0].guid, BLUE, 1, teamBlue.guid, FUNCTION_TYPE_GOALKEEPER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[1].guid, BLUE, 2, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[2].guid, BLUE, 3, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[3].guid, BLUE, 4, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[4].guid, BLUE, 5, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[5].guid, BLUE, 6, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[6].guid, BLUE, 7, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[7].guid, BLUE, 8, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[8].guid, BLUE, 9, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[9].guid, BLUE, 10, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[10].guid, BLUE, 11, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[11].guid, BLUE, 12, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListBlue[12].guid, BLUE, 13, teamBlue.guid, FUNCTION_TYPE_FIELDPLAYER)
            )
            participantListWhite = listOf(
                Participant(UUID.randomUUID().toString(), game.guid, teamWhite.guid, WHITE, 0, teamWhite.guid, FUNCTION_TYPE_OTHER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[0].guid, WHITE, 1, teamWhite.guid, FUNCTION_TYPE_GOALKEEPER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[1].guid, WHITE, 2, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[2].guid, WHITE, 3, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[3].guid, WHITE, 4, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[4].guid, WHITE, 5, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[5].guid, WHITE, 6, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[6].guid, WHITE, 7, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[7].guid, WHITE, 8, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[8].guid, WHITE, 9, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[9].guid, WHITE, 10, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[10].guid, WHITE, 11, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[11].guid, WHITE, 12, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER),
                Participant(UUID.randomUUID().toString(), game.guid, playersListWhite[12].guid, WHITE, 13, teamWhite.guid, FUNCTION_TYPE_FIELDPLAYER)
            )
        }

        private fun setDefaults() {
            Log.d(TAG, "GameControl.SetDefaults: ${(DEFAULT_GAME_SECTION_LENGTH % 60)}")

            // main time
            setMainTimeDefaults()
            // shotclock
            setShotclockDefaults(DEFAULT_SHOTCLOCK_BIG_LENGTH)
        }

        private fun setMainTimeDefaults() {
            myViewModel.setNewMainTime(gameSectionLength / 60, gameSectionLength % 60, 0)
        }

        private fun setShotclockDefaults(default: Int) {
            myViewModel.setNewShotclock(default % 60, 0)
        }

        fun processGameEvent(event: String, player: String) {
            Log.d(TAG, "GameControl.processGameEvent: $event,$player")
            if (event.toInt() in GOAL_TYPE_MINIMUM..GOAL_TYPE_MAXIMUM) {
                addGoal(event.toInt(), player)
            } else {
                addExclusion(event.toInt(), player)
                if(event.toInt() == 200) { // simple exclusion id  = 200
                    excutionTimeOffset[player] = currentCountdown
                    myViewModel.exclusionTime.value = "$player:20.0"
                }
            }
        }

        private fun addGoal(event: Int, player: String) {
            Log.d(TAG, "GameControl.addGoal: $event,$player")
            val participant =
                if (player.split("_")[1] == "B") participantListBlue[player.split("_")[2].toInt()] else participantListWhite[player.split("_")[2].toInt()]
            myViewModel.storeGameEvent(event, currentCountdown, currentGameSection, participant.guid)
        }

        private fun addExclusion(exclusionEvent: Int, player: String) {
            Log.d(TAG, "GameControl.addExclusion: $exclusionEvent,$player")
            val index = player.split("_")[2].toInt()
            val participant = if (player.split("_")[1] == "B") participantListBlue[index] else participantListWhite[index]
            myViewModel.storeGameEvent(exclusionEvent, currentCountdown, currentGameSection, participant.guid)
        }

        fun startStopCounter() {
            if (!gameStarted) {
                gameStarted = true
//                myViewModel.storeGameEvent(START_GAME, currentCountdown, currentGameSection, "-")
            }
            if (timeIsRunning) {
                timeIsRunning = false
                timerCountdown?.cancel()
//                myViewModel.storeGameEvent(STOP_TIME, currentCountdown, currentGameSection, "-")
            } else {
                timeIsRunning = true
                createTimerCountdown()
//                myViewModel.storeGameEvent(START_TIME, currentCountdown, currentGameSection, "-")
                timerCountdown?.start()
            }
        }

        // edit main time
        fun setGameTimeEdit() {
            val temp = currentCountdown
            val minutes = MyTimeConverter.getMinutesFromLong(temp)
            val seconds = MyTimeConverter.getSecondsFromLong(temp)
            myViewModel.setNewMainTime(minutes, seconds, MyTimeConverter.getSecondsSmallFromLong(temp))
            val mainBoardSecondsString = if (seconds < 10) "0$seconds" else seconds.toString()
            val color = if (minutes < 1) "red" else "default"
            ProcessBT.sendMessageToMainBoard("timeGame%$minutes:$mainBoardSecondsString%$color")
            ProcessBT.sendMessageToAllShotclock("time%$minutes:$mainBoardSecondsString%$color")
        }

        // edit shotclock
        fun setShotclockEdit(){
            val temp = currentCountdownShotclock
            val shotclockSeconds = MyTimeConverter.getSecondsFromLong(temp)
            val shotclockSecondsSmall = MyTimeConverter.getSecondsSmallFromLong(temp)
            val color = if (shotclockSeconds < 6) "red" else "default"
            val shotclockSecondsString = if (shotclockSeconds < 10) "0$shotclockSeconds" else "$shotclockSeconds"
            myViewModel.setNewShotclock(shotclockSeconds, shotclockSecondsSmall)
            ProcessBT.sendMessageToAllShotclock("shotclock%$shotclockSecondsString%$color%$shotclockSecondsSmall")
        }

        fun setGameTime() {
            // main time
            val currentCountdownTemp = currentCountdown
            val currentCountdownShotclockTemp = currentCountdownShotclock

            val minutes = MyTimeConverter.getMinutesFromLong(currentCountdownTemp)
            val seconds = MyTimeConverter.getSecondsFromLong(currentCountdownTemp)
            val secondsSmall = MyTimeConverter.getSecondsSmallFromLong(currentCountdownTemp)
            myViewModel.setNewMainTime(minutes, seconds, secondsSmall)
            // set led boards
            if (secondsSmall == 9) {
                val mainBoardSecondsString = if (seconds < 10) "0$seconds" else seconds.toString()
                val color = if (minutes < 1) "red" else "default"
                ProcessBT.sendMessageToMainBoard("timeGame%$minutes:$mainBoardSecondsString%$color")
                ProcessBT.sendMessageToAllShotclock("time%$minutes:$mainBoardSecondsString%$color")
            }

            // shotclock
            val shotclockSeconds = MyTimeConverter.getSecondsFromLong(currentCountdownShotclockTemp)
            val shotclockSecondsSmall = MyTimeConverter.getSecondsSmallFromLong(currentCountdownShotclockTemp)
            val color = if (shotclockSeconds < 6) "red" else "default"
            val shotclockSecondsString = if (shotclockSeconds < 10) "0$shotclockSeconds" else "$shotclockSeconds"
            myViewModel.setNewShotclock(shotclockSeconds, shotclockSecondsSmall)
            ProcessBT.sendMessageToAllShotclock("shotclock%$shotclockSecondsString%$color%$shotclockSecondsSmall")

            // stop timer if shotclock reached zero
            if ((shotclockSeconds == 0 && shotclockSecondsSmall == 0) && (secondsSmall > 0 || minutes > 0 || seconds > 0) && (currentCountdownShotclockTemp != currentCountdownTemp)) {
                startStopCounter()
                playSound(1)
                if (currentCountdownTemp < (DEFAULT_SHOTCLOCK_BIG_LENGTH * 1000)) {
                    currentCountdownShotclock = currentCountdownTemp
                } else {
                    currentCountdownShotclock += DEFAULT_SHOTCLOCK_BIG_LENGTH * 1000
                }
            }

            //set player btn exclusion time
            val toBeRemove = mutableListOf<String>()
            if(excutionTimeOffset.isNotEmpty()){
                excutionTimeOffset.forEach {
//                    val remainingExclusionTime = 20 - MyTimeConverter.getSecondsFromLong(it.value - currentCountdownTemp)
                    val remainingExclusionTimeSeconds = 19 - MyTimeConverter.getSecondsFromLong(it.value - currentCountdownTemp)
                    val remainingExclusionTimeSecondsSmall = 9 - (MyTimeConverter.getSecondsSmallFromLong(it.value - currentCountdownTemp) % 10)
                    Log.d(TAG, "Exclusion1: $remainingExclusionTimeSeconds.$remainingExclusionTimeSecondsSmall")
                    if(remainingExclusionTimeSecondsSmall == 9) {
                        Log.d(TAG, "Exclusion2: $remainingExclusionTimeSeconds")
                        myViewModel.exclusionTime.value = "${it.key}:$remainingExclusionTimeSeconds.$remainingExclusionTimeSecondsSmall"
                    }
                    if(remainingExclusionTimeSeconds == 0){
                        Log.d(TAG, "Exclusion3: $remainingExclusionTimeSeconds")
                        myViewModel.exclusionTime.value = "${it.key}:0.${remainingExclusionTimeSecondsSmall}"
                        if(remainingExclusionTimeSecondsSmall == 0) {
                            myViewModel.exclusionTime.value = "${it.key}:0.0"
                            toBeRemove.add(it.key)
                        }
                    }
                }
            }
            toBeRemove.forEach {
                excutionTimeOffset.remove(it)
            }
        }

        fun newShotclockBig() {
            if ((currentCountdownShotclock == currentCountdown) || (currentCountdownShotclock == (DEFAULT_SHOTCLOCK_BIG_LENGTH * 1000).toLong())) return

            if (currentCountdown <= (DEFAULT_SHOTCLOCK_BIG_LENGTH * 1000)) {
                currentCountdownShotclock = currentCountdown
                myViewModel.setNewShotclock(null, null)
                val shotclockSeconds = MyTimeConverter.getSecondsFromLong(currentCountdownShotclock)
                val shotclockSecondsSmall = MyTimeConverter.getSecondsSmallFromLong(currentCountdownShotclock)
                val shotclockSecondsString = if (shotclockSeconds < 10) "0$shotclockSeconds" else "$shotclockSeconds"
                val color = if (shotclockSeconds < 6) "red" else "default"
                ProcessBT.sendMessageToAllShotclock("shotclock%$shotclockSecondsString%$color%$shotclockSecondsSmall")
            } else {
                currentCountdownShotclock = shotclockLongLength.toLong() * 1000
                setShotclockDefaults(shotclockLongLength)
                val shotclockSeconds = MyTimeConverter.getSecondsFromLong(currentCountdownShotclock)
                ProcessBT.sendMessageToAllShotclock("shotclock%$shotclockSeconds%default%0")
            }
//            myViewModel.storeGameEvent(NEW_SHOTCLOCK_BIG, currentCountdown, currentGameSection, "-")
        }

        fun newShotclockSmall() {
            if ((currentCountdownShotclock == currentCountdown) || (currentCountdownShotclock == (DEFAULT_SHOTCLOCK_SMALL_LENGTH * 1000).toLong())) return

            if (currentCountdown <= (DEFAULT_SHOTCLOCK_SMALL_LENGTH * 1000)) {
                currentCountdownShotclock = currentCountdown
                myViewModel.setNewShotclock(null, null)
                val shotclockSeconds = MyTimeConverter.getSecondsFromLong(currentCountdownShotclock)
                val shotclockSecondsSmall = MyTimeConverter.getSecondsSmallFromLong(currentCountdownShotclock)
                val shotclockSecondsString = if (shotclockSeconds < 10) "0$shotclockSeconds" else "$shotclockSeconds"
                val color = if (shotclockSeconds < 6) "red" else "default"
                ProcessBT.sendMessageToAllShotclock("shotclock%$shotclockSecondsString%$color%$shotclockSecondsSmall")
            } else {
                currentCountdownShotclock = shotclockShortLength.toLong() * 1000
                setShotclockDefaults(shotclockShortLength)
                val shotclockSeconds = MyTimeConverter.getSecondsFromLong(currentCountdownShotclock)
                ProcessBT.sendMessageToAllShotclock("shotclock%$shotclockSeconds%default%0")
            }
        }

        private fun createTimerCountdown() {
            timerCountdown = object : CountDownTimer(currentCountdown, 100) {
                override fun onTick(millisUntilFinished: Long) {
                    currentCountdownShotclock -= (currentCountdown - millisUntilFinished)
                    currentCountdown = millisUntilFinished
                    setGameTime()
                }

                override fun onFinish() {
                    Log.d(TAG, "Countdown finished")
                    Log.d(TAG, "currentGameSection: $currentGameSection")

                    timeIsRunning = false
                    myViewModel.timeControlAvailable(false)
                    myViewModel.setNewMainTime(0, 0, 0)

                    if (currentGameSection < maxGameSection) {
//                        myViewModel.storeGameEvent(GAMESECTION_EXPIRED, currentCountdown, currentGameSection, "-")
                        currentCountdown = if (currentGameSection == 2)
                            ((DEFAULT_PAUSE_LONG_LENGTH) * 1000).toLong()
                        else ((DEFAULT_PAUSE_SHORT_LENGTH) * 1000).toLong()
                        createTimerPause(currentCountdown)
                        timerPause?.start()
                    } else {
                        myViewModel.setNewShotclock(0, 0)
                        myViewModel.storeGameEvent(END_GAME, currentCountdown, currentGameSection, "-")
                    }
                    playSound(2)
                }
            }
        }

        private fun createTimerPause(pause: Long) {
            timerPause = object : CountDownTimer(pause, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    currentCountdown = millisUntilFinished
                    myViewModel.setPauseTime(
                        MyTimeConverter.getMinutesFromLong(currentCountdown),
                        MyTimeConverter.getSecondsFromLong(currentCountdown)
                    )
                }

                override fun onFinish() {
                    Log.d(TAG, "Pause finished")
                    myViewModel.setCurrentGameSection(++currentGameSection)
                    currentCountdown = gameSectionLength.toLong() * 1000
                    currentCountdownShotclock = (shotclockLongLength * 1000).toLong()
                    setMainTimeDefaults()
                    setShotclockDefaults(shotclockLongLength)
                    createTimerCountdown()
                    playSound(2)
                    timerPause?.cancel()
                    myViewModel.timeControlAvailable(true)
                    val mainMinutes = MyTimeConverter.getMinutesFromLong((gameSectionLength * 1000).toLong())
                    val mainSeconds = MyTimeConverter.getSecondsFromLong((gameSectionLength * 1000).toLong())
                    val mainSecondsString = if (mainSeconds < 10) "0$mainSeconds" else "$mainSeconds"
                    ProcessBT.sendMessageToMainBoard("timeGame%$mainMinutes:$mainSecondsString%default")
                    ProcessBT.sendMessageToAllShotclock("time%$mainMinutes:$mainSecondsString%default")
                    Thread.sleep(1000)
                    ProcessBT.sendMessageToAllShotclock("shotclock%${MyTimeConverter.getSecondsFromLong((shotclockLongLength * 1000).toLong())}%default%0")
                }
            }
        }

        private fun createTimerTimeout(timeout: Long) {
            timerPause = object : CountDownTimer(timeout, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    currentCountdown = millisUntilFinished
                    myViewModel.setPauseTime(
                        MyTimeConverter.getMinutesFromLong(currentCountdown),
                        MyTimeConverter.getSecondsFromLong(currentCountdown)
                    )
                }

                override fun onFinish() {
                    Log.d(TAG, "Timeout finished")
                    currentCountdown = savedCountdown
                    savedCountdown = 0
                    timerPause?.cancel()
                    createTimerCountdown()
                    playSound(2)
                    myViewModel.timeControlAvailable(true)
                    myViewModel.setCurrentGameSection(currentGameSection)
                }
            }
        }

        fun startTimeout() {
            timerCountdown?.cancel()
            playSound(2)
            timeIsRunning = false
            myViewModel.timeControlAvailable(false)
            savedCountdown = currentCountdown
            createTimerTimeout((DEFAULT_TIMEOUT_LENGTH * 1000).toLong())
            timerPause?.start()
        }

        private fun playSound(version: Int) {
            val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
            when (version) {
                1 -> toneGenerator.startTone(ToneGenerator.TONE_CDMA_CALL_SIGNAL_ISDN_SP_PRI) // shotclock // TONE_CDMA_PIP 4
                2 -> toneGenerator.startTone(ToneGenerator.TONE_SUP_PIP) // finish
            }
        }

        fun getcountdownShotclock(): Long {
            return currentCountdownShotclock
        }

        fun getCurrentGameSection(): Int {
            return currentGameSection
        }

        fun getTeamBlueParticipantGuid(): String {
            return participantListBlue[0].guid
        }

        fun getTeamWhiteParticipantGuid(): String {
            return participantListWhite[0].guid
        }

        fun getParticipantByCapNumber(cap: String, number: Int): String {
            return if (cap == BLUE) participantListBlue[number].guid else participantListWhite[number].guid
        }
    }
}