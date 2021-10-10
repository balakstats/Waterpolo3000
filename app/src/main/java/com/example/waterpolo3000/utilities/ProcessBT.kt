package com.example.waterpolo3000.utilities

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.FragmentActivity
import com.example.waterpolo3000.game.GameControl
import java.io.IOException
import java.util.*

class ProcessBT(val myActivity: FragmentActivity?) {
    companion object {
        var btSocketMainBoard: BluetoothSocket? = null
//        var btSocketShotclock1: BluetoothSocket? = null
//        var btSocketShotclock2: BluetoothSocket? = null
//        var btSocketShotclock3: BluetoothSocket? = null
//        var btSocketShotclock4: BluetoothSocket? = null
        val btSocketsShotclocks = mutableListOf<BluetoothSocket?>(
            null,
            null,
            null,
            null
        )

        var mainBoardConnected = false
        val shotclocksConnected = mutableListOf(
            false,
            false,
            false,
            false
        )

        const val REQUEST_ENABLE_BT = 1

        fun sendMessageToMainBoard(text: String) {
            if (mainBoardConnected) {
                val textTemp = "$text%"
                val check = btSocketMainBoard?.let { sendMessage(textTemp, it) }
                if (!check!!) {
                    mainBoardConnected = false
                }
            }
        }

        fun sendMessageToShotclock(text: String, index: Int) {
            if (shotclocksConnected[index]) {
                Log.d(ContentValues.TAG, "Index: $index")
                Log.d(ContentValues.TAG, "Index: $index")
                val textTemp = "$text%"
                val check = btSocketsShotclocks[index]?.let { sendMessage(textTemp, it) }
                if (check == null || !check) {
                    shotclocksConnected[index] = false
                }
            }
        }

        fun sendMessageToAllShotclock(text: String) {
            shotclocksConnected.forEachIndexed { index, _ ->
                sendMessageToShotclock(text, index)
            }
        }

        private fun sendMessage(text: String, socket: BluetoothSocket): Boolean {
            return try {
                socket.outputStream?.write(text.toByteArray())
                true
            } catch (e: IOException) {
                false
            }
        }
    }

    var mainBoard_myUUID: UUID? = null
    lateinit var myMainBoard: BluetoothDevice
    var remoteMainBoard: BluetoothDevice? = null

    val localShotclocks = mutableListOf<BluetoothDevice?>(
        null,
        null,
        null,
        null
    )
    val remoteShotclocks = mutableListOf<BluetoothDevice?>(
        null,
        null,
        null,
        null
    )
    val shotclock_UUIDs = mutableListOf<UUID?>(
        null,
        null,
        null,
        null
    )

    val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    fun connectMainBoard(): Boolean {
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled) {
                requestBTPermisson()
            }
            if (bluetoothAdapter.isEnabled) {
                if (remoteMainBoard != null) {
                    mainBoard_myUUID?.let { connectToDevice(remoteMainBoard!!, it) }
                    if (mainBoardConnected) {
                        Log.d(ContentValues.TAG, "connected")
                        val textMain = initMainBoardAfterConnection()
                        sendMessageToMainBoard(textMain)
                        Thread.sleep(1000)
                        sendMessageToMainBoard("gameSection%1")
                        return true
                    } else {
                        Log.d(ContentValues.TAG, "mainboard not connected")
                    }
                }
            }
        }
        return false
    }

    fun connectShotclock(index: Int): Boolean {
        Log.d(ContentValues.TAG, "yo0: $index")
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled) {
                requestBTPermisson()
            }
            Log.d(ContentValues.TAG, "yo1: $index")
            Log.d(ContentValues.TAG, remoteShotclocks[index].toString())
            if (bluetoothAdapter.isEnabled && remoteShotclocks[index] != null) {
                Log.d(ContentValues.TAG, "yo2: $index")
                shotclock_UUIDs[index]?.let { connectToDevice(remoteShotclocks[index]!!, it) }
                Log.d(ContentValues.TAG, "yo3: $index")
                if (shotclocksConnected[index]) {
                    Log.d(ContentValues.TAG, "connected")
                    val textMain = initShotclocksAfterConnection("main")
                    val textShot = initShotclocksAfterConnection("shotclock")
                    sendMessageToShotclock(textMain, index)
                    Thread.sleep(1000)
                    sendMessageToShotclock(textShot, index)
                    return true
                } else {
                    Log.d(ContentValues.TAG, "shotclock1 not connected")
                }
            }
        }
        return false
    }

//    fun connectShotclock2():Boolean {
//        if (bluetoothAdapter != null) {
//            if (!bluetoothAdapter.isEnabled) {
//                requestBTPermisson()
//            }
//            if (bluetoothAdapter.isEnabled) {
//                if (remoteShotclock2 != null) {
//                    connectToDevice(remoteShotclock2!!, shotclock2_myUUID)
//                    if(shotclock2Connected) {
//                        val textMain = initShotclocksAfterConnection("main")
//                        val textShot = initShotclocksAfterConnection("shotclock")
//                        sendMessageToShotclock2(textMain)
//                        Thread.sleep(1000)
//                        sendMessageToShotclock2(textShot)
//                        return true
//                    } else{
//                        Log.d(ContentValues.TAG, "shotclock2 not connected")
//                    }
//                }
//            }
//        }
//        return false
//    }
//
//    fun connectShotclock3():Boolean {
//        if (bluetoothAdapter != null) {
//            if (!bluetoothAdapter.isEnabled) {
//                requestBTPermisson()
//            }
//            if (bluetoothAdapter.isEnabled) {
//                if (remoteShotclock3 != null) {
//                    connectToDevice(remoteShotclock3!!, shotclock3_myUUID)
//                    if(shotclock3Connected) {
//                        val textMain = initShotclocksAfterConnection("main")
//                        val textShot = initShotclocksAfterConnection("shotclock")
//                        sendMessageToShotclock3(textMain)
//                        Thread.sleep(1000)
//                        sendMessageToShotclock3(textShot)
//                        return true
//                    } else{
//                        Log.d(ContentValues.TAG, "shotclock3 not connected")
//                    }
//                }
//            }
//        }
//        return false
//    }
//
//    fun connectShotclock4():Boolean {
//        if (bluetoothAdapter != null) {
//            if (!bluetoothAdapter.isEnabled) {
//                requestBTPermisson()
//            }
//            if (bluetoothAdapter.isEnabled) {
//                if (remoteShotclock4 != null) {
//                    connectToDevice(remoteShotclock4!!, shotclock4_myUUID)
//                    if(shotclock4Connected) {
//                        val textMain = initShotclocksAfterConnection("main")
//                        val textShot = initShotclocksAfterConnection("shotclock")
//                        sendMessageToShotclock4(textMain)
//                        Thread.sleep(1000)
//                        sendMessageToShotclock4(textShot)
//                        return true
//                    } else{
//                        Log.d(ContentValues.TAG, "shotclock4 not connected")
//                    }
//                }
//            }
//        }
//        return false
//    }

    private fun initMainBoardAfterConnection(): String {
        val currCountdown = GameControl.currentCountdown
        val minutes = MyTimeConverter.getMinutesFromLong(currCountdown)
        val seconds = MyTimeConverter.getSecondsFromLong(currCountdown)
        val secondsString = if (seconds < 10) "0$seconds" else "$seconds"
        val colorMainTime = if (minutes < 1) "red" else "default"
        Log.d(ContentValues.TAG, "send main")
        return "timeGame%$minutes:$secondsString%$colorMainTime"
    }

    private fun initShotclocksAfterConnection(mode: String): String {
        if (mode == "main") {
            val currCountdown = GameControl.currentCountdown
            val minutes = MyTimeConverter.getMinutesFromLong(currCountdown)
            val seconds = MyTimeConverter.getSecondsFromLong(currCountdown)
            val secondsString = if (seconds < 10) "0$seconds" else "$seconds"
            val colorMainTime = if (minutes < 1) "red" else "default"
            Log.d(ContentValues.TAG, "send first")
            return "time%$minutes:$secondsString%$colorMainTime"
        } else {
            val currShotCountdown = GameControl.getcountdownShotclock()
            Log.d(ContentValues.TAG, "currShotCountdown: $currShotCountdown")
            val secondsShot = MyTimeConverter.getSecondsFromLong(currShotCountdown)
            val secondsShotString = if (secondsShot < 10) "0$secondsShot" else "$secondsShot"
            val secondsShotSmall = MyTimeConverter.getSecondsSmallFromLong(currShotCountdown)
            val colorShotclock = if (secondsShot < 6) "red" else "default"
            Log.d(ContentValues.TAG, "send second")
            return "shotclock%$secondsShotString%$colorShotclock%$secondsShotSmall"
        }
    }

    fun manageMyConnectedSocket(socket: BluetoothSocket, mode: String) {
        when (mode) {
            MAIN_BOARD_BT_NAME -> btSocketMainBoard = socket
            SHOTCLOCK_1_BT_NAME -> btSocketsShotclocks[0] = socket
            SHOTCLOCK_2_BT_NAME -> btSocketsShotclocks[1] = socket
            SHOTCLOCK_3_BT_NAME -> btSocketsShotclocks[2] = socket
            SHOTCLOCK_4_BT_NAME -> btSocketsShotclocks[3] = socket
        }
    }

    private fun connectToDevice(device: BluetoothDevice, uuid: UUID) {
        val temp = ConnectThread(device, uuid)
        temp.start()
        temp.join()
        temp.cancel()
    }

    private inner class ConnectThread(val device: BluetoothDevice, uuid: UUID) : Thread() {
        private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            device.createRfcommSocketToServiceRecord(uuid)
        }

        override fun run() {
            // Cancel discovery because it otherwise slows down the connection.
            bluetoothAdapter?.cancelDiscovery()
//            var mode = ""
            Log.d(ContentValues.TAG, "Try to connect: ${device.name}")
            try {
                mmSocket?.let { socket ->
                    // Connect to the remote device through the socket. This call blocks
                    // until it succeeds or throws an exception.
                    socket.connect()
                    Log.d(ContentValues.TAG, "Socket is connected: ${socket.isConnected}")
                    manageMyConnectedSocket(socket, device.name)
                }
            } catch (ex: Exception) {
                Log.d(ContentValues.TAG, ex.toString())
                try {
                    Log.d(ContentValues.TAG, "try again: ${device.name}")
                    val newSocket: BluetoothSocket =
                        device.javaClass.getMethod(
                            "createRfcommSocket",
                            Int::class.javaPrimitiveType
                        )
                            .invoke(device, 1) as BluetoothSocket
                    newSocket.let { socket ->
                        socket.connect()
                        if (!mainBoardConnected && (device == remoteMainBoard)) {
                            mainBoardConnected = true
                            manageMyConnectedSocket(newSocket, device.name)
                        } else if (!shotclocksConnected[0] && (device == remoteShotclocks[0])) {
                            shotclocksConnected[0] = true
                            manageMyConnectedSocket(newSocket, device.name)
                        } else if (!shotclocksConnected[1] && (device == remoteShotclocks[1])) {
                            shotclocksConnected[1] = true
                            manageMyConnectedSocket(newSocket, device.name)
                        } else if (!shotclocksConnected[2] && (device == remoteShotclocks[2])) {
                            shotclocksConnected[2] = true
                            manageMyConnectedSocket(newSocket, device.name)
                        } else if (!shotclocksConnected[3] && (device == remoteShotclocks[3])) {
                            shotclocksConnected[3] = true
                            manageMyConnectedSocket(newSocket, device.name)
                        }
                    }
                } catch (e1: IOException) {
                    Log.d(ContentValues.TAG, "Fallback failed. Cancelling: ", e1)
                    when (device.name) {
                        MAIN_BOARD_BT_NAME -> mainBoardConnected = false
                        SHOTCLOCK_1_BT_NAME -> shotclocksConnected[0] = false
                        SHOTCLOCK_2_BT_NAME -> shotclocksConnected[1] = false
                        SHOTCLOCK_3_BT_NAME -> shotclocksConnected[2] = false
                        SHOTCLOCK_4_BT_NAME -> shotclocksConnected[3] = false
                    }
                }
            }
        }

        // Closes the client socket and causes the thread to finish.
        fun cancel() {
//            try {
////                mmSocket?.close()
//            } catch (e: IOException) {
//                Log.e(TAG, "Could not close the client socket", e)
//            }
        }
    }

    fun searchAllDevice() {
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled) {
                requestBTPermisson()
            }

            if (bluetoothAdapter.isEnabled) {
                val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter.bondedDevices

                if (pairedDevices != null && pairedDevices.isNotEmpty()) {
                    pairedDevices.forEach { device ->
                        if (device.name == MAIN_BOARD_BT_NAME) {
                            Log.d(ContentValues.TAG, "paired device: mainboard")
                            myMainBoard = device
                            remoteMainBoard = bluetoothAdapter.getRemoteDevice(myMainBoard.address)
                            mainBoard_myUUID = myMainBoard.uuids[0].uuid
                        }
                        val nameArray = arrayOf(SHOTCLOCK_1_BT_NAME,SHOTCLOCK_2_BT_NAME,SHOTCLOCK_3_BT_NAME,SHOTCLOCK_4_BT_NAME)
                        nameArray.forEachIndexed { index, s ->
                            if (device.name == s) {
                                Log.d(ContentValues.TAG, "paired device: shotclock ${index+1}")
                                localShotclocks[index] = device
                                remoteShotclocks[index] = bluetoothAdapter.getRemoteDevice(
                                    localShotclocks[index]?.address
                                )
                                shotclock_UUIDs[index] = localShotclocks[index]?.uuids?.get(0)?.uuid
                            }
                        }
//                        if (device.name == SHOTCLOCK_1_BT_NAME) {
//                            val index = 0
//                            Log.d(ContentValues.TAG, "paired device: shotclock1")
//                            shotclock1 = device
//                            remoteShotclocks[index] = bluetoothAdapter.getRemoteDevice(shotclock1.address)
//                            shotclock_UUIDs[index] = shotclock1.uuids[0].uuid
//                        }
//
//                        if (device.name == SHOTCLOCK_2_BT_NAME) {
//                            val index = 1
//                            Log.d(ContentValues.TAG, "paired device: shotclock2")
//                            shotclock2 = device
//                            remoteShotclocks[index] = bluetoothAdapter.getRemoteDevice(shotclock2.address)
//                            shotclock_UUIDs[index] = shotclock2.uuids[0].uuid
//                        }
//
//                        if (device.name == SHOTCLOCK_3_BT_NAME) {
//                            val index = 2
//                            Log.d(ContentValues.TAG, "paired device: shotclock3")
//                            shotclock3 = device
//                            remoteShotclocks[index] = bluetoothAdapter.getRemoteDevice(shotclock3.address)
//                            shotclock_UUIDs[index] = shotclock3.uuids[0].uuid
//                        }
//
//                        if (device.name == SHOTCLOCK_4_BT_NAME) {
//                            val index = 3
//                            Log.d(ContentValues.TAG, "paired device: shotclock4")
//                            shotclock4 = device
//                            remoteShotclocks[index] = bluetoothAdapter.getRemoteDevice(shotclock4.address)
//                            shotclock_UUIDs[index] = shotclock4.uuids[0].uuid
//                        }
                    }
                }
            }
        }
    }

    private fun requestBTPermisson() {
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                if (myActivity != null) {
                    startActivityForResult(myActivity, enableBtIntent, REQUEST_ENABLE_BT, null)
                }
            }
        } else {
            Toast.makeText(myActivity, "This device does not support bluetooth", Toast.LENGTH_LONG)
                .show()
        }
    }
}