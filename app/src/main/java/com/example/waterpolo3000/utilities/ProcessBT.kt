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
        var btSocketShotclock1: BluetoothSocket? = null
        var btSocketShotclock2: BluetoothSocket? = null
        var btSocketShotclock3: BluetoothSocket? = null
        var btSocketShotclock4: BluetoothSocket? = null

        var mainBoardConnected = false
        var shotclock1Connected = false
        var shotclock2Connected = false
        var shotclock3Connected = false
        var shotclock4Connected = false

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

        fun sendMessageToShotclock1(text: String) {
            if (shotclock1Connected) {
                val textTemp = "$text%"
                val check = btSocketShotclock1?.let { sendMessage(textTemp, it) }
                if (!check!!) {
                    shotclock1Connected = false
                }
            }
        }

        fun sendMessageToShotclock2(text: String) {
            if (shotclock2Connected) {
                val textTemp = "$text%"
                val check = btSocketShotclock2?.let { sendMessage(textTemp, it) }
                if (!check!!) {
                    shotclock2Connected = false
                }
            }
        }

        fun sendMessageToShotclock3(text: String) {
            if (shotclock3Connected) {
                val textTemp = "$text%"
                val check = btSocketShotclock3?.let { sendMessage(textTemp, it) }
                if (!check!!) {
                    shotclock3Connected = false
                }
            }
        }

        fun sendMessageToShotclock4(text: String) {
            if (shotclock4Connected) {
                val textTemp = "$text%"
                val check = btSocketShotclock4?.let { sendMessage(textTemp, it) }
                if (!check!!) {
                    shotclock4Connected = false
                }
            }
        }

        fun sendMessageToAllShotclock(text: String) {
            sendMessageToShotclock1(text)
            sendMessageToShotclock2(text)
            sendMessageToShotclock3(text)
            sendMessageToShotclock4(text)
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

    lateinit var mainBoard_myUUID: UUID
    lateinit var myMainBoard: BluetoothDevice
    var remoteMainBoard: BluetoothDevice? = null

    lateinit var shotclock1_myUUID: UUID
    lateinit var shotclock1: BluetoothDevice
    var remoteShotclock1: BluetoothDevice? = null

    lateinit var shotclock2_myUUID: UUID
    lateinit var shotclock2: BluetoothDevice
    var remoteShotclock2: BluetoothDevice? = null

    lateinit var shotclock3_myUUID: UUID
    lateinit var shotclock3: BluetoothDevice
    var remoteShotclock3: BluetoothDevice? = null

    lateinit var shotclock4_myUUID: UUID
    lateinit var shotclock4: BluetoothDevice
    var remoteShotclock4: BluetoothDevice? = null

    val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    fun connectMainBoard():Boolean {
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled) {
                requestBTPermisson()
            }
            if (bluetoothAdapter.isEnabled) {
                if (remoteMainBoard != null) {
                    connectToDevice(remoteMainBoard!!, mainBoard_myUUID)
                    if(mainBoardConnected) {
                        Log.d(ContentValues.TAG, "connected")
                        val textMain = initMainBoardAfterConnection()
                        sendMessageToMainBoard(textMain)
                        Thread.sleep(1000)
                        sendMessageToMainBoard("gameSection%1")
                        return true
                    }else{
                        Log.d(ContentValues.TAG, "mainboard not connected")
                    }
                }
            }
        }
        return false
    }

    fun connectShotclock1():Boolean {
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled) {
                requestBTPermisson()
            }
            if (bluetoothAdapter.isEnabled) {
                if (remoteShotclock1 != null) {
                    connectToDevice(remoteShotclock1!!, shotclock1_myUUID)
                    if(shotclock1Connected) {
                        Log.d(ContentValues.TAG, "connected")
                        val textMain = initShotclocksAfterConnection("main")
                        val textShot = initShotclocksAfterConnection("shotclock")
                        sendMessageToShotclock1(textMain)
                        Thread.sleep(1000)
                        sendMessageToShotclock1(textShot)
                        return true
                    }else{
                        Log.d(ContentValues.TAG, "shotclock1 not connected")
                    }
                }
            }
        }
        return false
    }

    fun connectShotclock2():Boolean {
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled) {
                requestBTPermisson()
            }
            if (bluetoothAdapter.isEnabled) {
                if (remoteShotclock2 != null) {
                    connectToDevice(remoteShotclock2!!, shotclock2_myUUID)
                    if(shotclock2Connected) {
                        val textMain = initShotclocksAfterConnection("main")
                        val textShot = initShotclocksAfterConnection("shotclock")
                        sendMessageToShotclock2(textMain)
                        Thread.sleep(1000)
                        sendMessageToShotclock2(textShot)
                        return true
                    } else{
                        Log.d(ContentValues.TAG, "shotclock2 not connected")
                    }
                }
            }
        }
        return false
    }

    fun connectShotclock3():Boolean {
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled) {
                requestBTPermisson()
            }
            if (bluetoothAdapter.isEnabled) {
                if (remoteShotclock3 != null) {
                    connectToDevice(remoteShotclock3!!, shotclock3_myUUID)
                    if(shotclock3Connected) {
                        val textMain = initShotclocksAfterConnection("main")
                        val textShot = initShotclocksAfterConnection("shotclock")
                        sendMessageToShotclock3(textMain)
                        Thread.sleep(1000)
                        sendMessageToShotclock3(textShot)
                        return true
                    } else{
                        Log.d(ContentValues.TAG, "shotclock3 not connected")
                    }
                }
            }
        }
        return false
    }

    fun connectShotclock4():Boolean {
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled) {
                requestBTPermisson()
            }
            if (bluetoothAdapter.isEnabled) {
                if (remoteShotclock4 != null) {
                    connectToDevice(remoteShotclock4!!, shotclock4_myUUID)
                    if(shotclock4Connected) {
                        val textMain = initShotclocksAfterConnection("main")
                        val textShot = initShotclocksAfterConnection("shotclock")
                        sendMessageToShotclock4(textMain)
                        Thread.sleep(1000)
                        sendMessageToShotclock4(textShot)
                        return true
                    } else{
                        Log.d(ContentValues.TAG, "shotclock4 not connected")
                    }
                }
            }
        }
        return false
    }

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
        when(mode){
            MAIN_BOARD_BT_NAME -> btSocketMainBoard = socket
            SHOTCLOCK_1_BT_NAME -> btSocketShotclock1 = socket
            SHOTCLOCK_2_BT_NAME -> btSocketShotclock2 = socket
            SHOTCLOCK_3_BT_NAME -> btSocketShotclock3 = socket
            SHOTCLOCK_4_BT_NAME -> btSocketShotclock4 = socket
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
                        } else if (!shotclock1Connected && (device == remoteShotclock1)) {
                            shotclock1Connected = true
                            manageMyConnectedSocket(newSocket, device.name)
                        } else if (!shotclock2Connected && (device == remoteShotclock2)) {
                            shotclock2Connected = true
                            manageMyConnectedSocket(newSocket, device.name)
                        } else if (!shotclock3Connected && (device == remoteShotclock3)) {
                            shotclock3Connected = true
                            manageMyConnectedSocket(newSocket, device.name)
                        } else if (!shotclock4Connected && (device == remoteShotclock4)) {
                            shotclock4Connected = true
                            manageMyConnectedSocket(newSocket, device.name)
                        }
                    }
                } catch (e1: IOException) {
                    Log.d(ContentValues.TAG, "Fallback failed. Cancelling: ", e1)
                    when(device.name){
                        MAIN_BOARD_BT_NAME -> mainBoardConnected = false
                        SHOTCLOCK_1_BT_NAME -> shotclock1Connected = false
                        SHOTCLOCK_2_BT_NAME -> shotclock2Connected = false
                        SHOTCLOCK_3_BT_NAME -> shotclock3Connected = false
                        SHOTCLOCK_4_BT_NAME -> shotclock4Connected = false
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

                        if (device.name == SHOTCLOCK_1_BT_NAME) {
                            Log.d(ContentValues.TAG, "paired device: shotclock1")
                            shotclock1 = device
                            remoteShotclock1 = bluetoothAdapter.getRemoteDevice(shotclock1.address)
                            shotclock1_myUUID = shotclock1.uuids[0].uuid
                        }

                        if (device.name == SHOTCLOCK_2_BT_NAME) {
                            Log.d(ContentValues.TAG, "paired device: shotclock2")
                            shotclock2 = device
                            remoteShotclock2 = bluetoothAdapter.getRemoteDevice(shotclock2.address)
                            shotclock2_myUUID = shotclock2.uuids[0].uuid
                        }

                        if (device.name == SHOTCLOCK_3_BT_NAME) {
                            Log.d(ContentValues.TAG, "paired device: shotclock3")
                            shotclock3 = device
                            remoteShotclock3 = bluetoothAdapter.getRemoteDevice(shotclock3.address)
                            shotclock3_myUUID = shotclock3.uuids[0].uuid
                        }

                        if (device.name == SHOTCLOCK_4_BT_NAME) {
                            Log.d(ContentValues.TAG, "paired device: shotclock4")
                            shotclock4 = device
                            remoteShotclock4 = bluetoothAdapter.getRemoteDevice(shotclock4.address)
                            shotclock4_myUUID = shotclock4.uuids[0].uuid
                        }
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
            Toast.makeText(myActivity, "this device does not support bluetooth", Toast.LENGTH_LONG)
                .show()
        }
    }
}