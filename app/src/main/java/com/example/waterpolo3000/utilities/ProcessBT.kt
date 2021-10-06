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
import com.example.waterpolo3000.viewmodels.LedViewModel
import kotlinx.coroutines.delay
import java.io.IOException
import java.lang.reflect.Method
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

        lateinit var viewModel: LedViewModel

        const val REQUEST_ENABLE_BT = 1

        fun sendMessageToMainBoard(text: String) {
            if (mainBoardConnected) {
                val textTemp = "$text%"
                Log.d("Bluetooth Activity", "send message to mainBoard: $textTemp")
                val check = btSocketMainBoard?.let { sendMessage(textTemp, it) }
                if (!check!!) {
                    mainBoardConnected = false
                    viewModel.mainBoardConnected.value = false
                }
            }
        }

        fun sendMessageToShotclock1(text: String) {
            if (shotclock1Connected) {
                val textTemp = "$text%"
                val check = btSocketShotclock1?.let { sendMessage(textTemp, it) }
                if (!check!!) {
                    shotclock1Connected = false
                    viewModel.shotclock1Connected.value = false
                }
            }
        }

        fun sendMessageToShotclock2(text: String) {
            if (shotclock2Connected) {
                val textTemp = "$text%"
                val check = btSocketShotclock2?.let { sendMessage(textTemp, it) }
                if (!check!!) {
                    shotclock2Connected = false
                    viewModel.shotclock2Connected.value = false
                }
            }
        }

        fun sendMessageToShotclock3(text: String) {
            if (shotclock3Connected) {
                val textTemp = "$text%"
                val check = btSocketShotclock3?.let { sendMessage(textTemp, it) }
                if (!check!!) {
                    shotclock3Connected = false
                    viewModel.shotclock3Connected.value = false
                }
            }
        }

        fun sendMessageToShotclock4(text: String) {
            if (shotclock4Connected) {
                val textTemp = "$text%"
                val check = btSocketShotclock4?.let { sendMessage(textTemp, it) }
                if (!check!!) {
                    shotclock4Connected = false
                    viewModel.shotclock4Connected.value = false
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

    fun connectMainBoard() {
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled) {
                requestBTPermisson()
            }

            if (bluetoothAdapter.isEnabled) {
                if (remoteMainBoard != null) {
                    connectToDevice(remoteMainBoard!!, mainBoard_myUUID)
                    val textMain = initMainBoardAfterConnection()
                    sendMessageToMainBoard(textMain)
                    Thread.sleep(1000)
                    sendMessageToMainBoard("gameSection%1")
                }
            }
        }
    }

    fun connectShotclock1() {
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled) {
                requestBTPermisson()
            }

            if (bluetoothAdapter.isEnabled) {
                if (remoteShotclock1 != null) {
                    connectToDevice(remoteShotclock1!!, shotclock1_myUUID)
                    val textMain = initShotclocksAfterConnection("main")
                    val textShot = initShotclocksAfterConnection("shotclock")
                    sendMessageToShotclock1(textMain)
                    Thread.sleep(1000)
                    sendMessageToShotclock1(textShot)
                }
            }
        }
    }

    fun connectShotclock2() {
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled) {
                requestBTPermisson()
            }

            if (bluetoothAdapter.isEnabled) {
                if (remoteShotclock2 != null) {
                    connectToDevice(remoteShotclock2!!, shotclock2_myUUID)
                    val textMain = initShotclocksAfterConnection("main")
                    val textShot = initShotclocksAfterConnection("shotclock")
                    sendMessageToShotclock2(textMain)
                    Thread.sleep(1000)
                    sendMessageToShotclock2(textShot)
                }
            }
        }
    }

    fun connectShotclock3() {
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled) {
                requestBTPermisson()
            }

            if (bluetoothAdapter.isEnabled) {
                if (remoteShotclock3 != null) {
                    connectToDevice(remoteShotclock3!!, shotclock3_myUUID)
                    val textMain = initShotclocksAfterConnection("main")
                    val textShot = initShotclocksAfterConnection("shotclock")
                    sendMessageToShotclock3(textMain)
                    Thread.sleep(1000)
                    sendMessageToShotclock3(textShot)
                }
            }
        }
    }

    fun connectShotclock4() {
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled) {
                requestBTPermisson()
            }

            if (bluetoothAdapter.isEnabled) {
                if (remoteShotclock4 != null) {
                    connectToDevice(remoteShotclock4!!, shotclock4_myUUID)
                    val textMain = initShotclocksAfterConnection("main")
                    val textShot = initShotclocksAfterConnection("shotclock")
                    sendMessageToShotclock4(textMain)
                    Thread.sleep(1000)
                    sendMessageToShotclock4(textShot)
                }
            }
        }
    }

    private fun initMainBoardAfterConnection(): String {
        val currCountdown = GameControl.currentCountdown
        val minutes = MyTimeConverter.getMinutesFromLong(currCountdown)
        val seconds = MyTimeConverter.getSecondsFromLong(currCountdown)
        val secondsString = if (seconds < 10) "0$seconds" else "$seconds"
        val colorMainTime = if (minutes < 1) "red" else "default"
        Log.d("Bluetooth Activity", "send main")
        return "timeGame%$minutes:$secondsString%$colorMainTime"
    }

    private fun initShotclocksAfterConnection(mode: String): String {
        if (mode == "main") {
            val currCountdown = GameControl.currentCountdown
            val minutes = MyTimeConverter.getMinutesFromLong(currCountdown)
            val seconds = MyTimeConverter.getSecondsFromLong(currCountdown)
            val secondsString = if (seconds < 10) "0$seconds" else "$seconds"
            val colorMainTime = if (minutes < 1) "red" else "default"
            Log.d("Bluetooth Activity", "send first")
            return "time%$minutes:$secondsString%$colorMainTime"
        } else {
            val currShotCountdown = GameControl.getcountdownShotclock()
            Log.d("Bluetooth Activity", "currShotCountdown: $currShotCountdown")
            val secondsShot = MyTimeConverter.getSecondsFromLong(currShotCountdown)
            val secondsShotString = if (secondsShot < 10) "0$secondsShot" else "$secondsShot"
            val secondsShotSmall = MyTimeConverter.getSecondsSmallFromLong(currShotCountdown)
            val colorShotclock = if (secondsShot < 6) "red" else "default"
            Log.d("Bluetooth Activity", "send second")
            return "shotclock%$secondsShotString%$colorShotclock%$secondsShotSmall"
        }
    }

    fun manageMyConnectedSocket(socket: BluetoothSocket, mode: String) {
        if (mode == "mainBoard") {
            Log.d("Bluetooth Activity", "create mainBoard socket")
            btSocketMainBoard = socket
        } else if (mode == "shotclock1") {
            Log.d("Bluetooth Activity", "create shotclock1 socket")
            btSocketShotclock1 = socket
        } else if (mode == "shotclock2") {
            Log.d("Bluetooth Activity", "create shotclock2 socket")
            btSocketShotclock2 = socket
        } else if (mode == "shotclock3") {
            Log.d("Bluetooth Activity", "create shotclock3 socket")
            btSocketShotclock3 = socket
        } else if (mode == "shotclock4") {
            Log.d("Bluetooth Activity", "create shotclock4 socket")
            btSocketShotclock4 = socket
        }
    }

    private fun connectToDevice(device: BluetoothDevice, uuid: UUID) {
        val temp = ConnectThread(device, uuid)
        temp.start()
        temp.join()
        temp.cancel()
        if (device.name == MAIN_BOARD_BT_NAME) {
            Log.d("Bluetooth Activity", "mainBoardConnected")
            viewModel.mainBoardConnected.value = true
        }
        if (device.name == SHOTCLOCK_1_BT_NAME) {
            Log.d("Bluetooth Activity", "shotclock1Connected")
            viewModel.shotclock1Connected.value = true
        }
        if (device.name == SHOTCLOCK_2_BT_NAME) {
            Log.d("Bluetooth Activity", "shotclock2Connected")
            viewModel.shotclock2Connected.value = true
        }
        if (device.name == SHOTCLOCK_3_BT_NAME) {
            Log.d("Bluetooth Activity", "shotclock3Connected")
            viewModel.shotclock3Connected.value = true
        }
        if (device.name == SHOTCLOCK_4_BT_NAME) {
            Log.d("Bluetooth Activity", "shotclock4Connected")
            viewModel.shotclock4Connected.value = true
        }
    }

    private inner class ConnectThread(val device: BluetoothDevice, uuid: UUID) : Thread() {
        private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            device.createRfcommSocketToServiceRecord(uuid)
        }
//        private val mainBoard = device.name == "raspi1"
//        private val myIndex = index

        override fun run() {
            // Cancel discovery because it otherwise slows down the connection.
            bluetoothAdapter?.cancelDiscovery()

            try {
                mmSocket?.let { socket ->
                    // Connect to the remote device through the socket. This call blocks
                    // until it succeeds or throws an exception.
//                    if (socket.isConnected) socket.close() else socket.connect()
                    socket.connect()
                    Log.d("Bluetooth Activity", "Socket is connected: ${socket.isConnected}")

                    // The connection attempt succeeded. Perform work associated with
                    // the connection in a separate thread.
                    var mode = ""
                    if (device.name == MAIN_BOARD_BT_NAME) {
                        mode = "mainBoard"
                    } else if (device.name == SHOTCLOCK_1_BT_NAME) {
                        mode = "shotclock1"
                    } else if (device.name == SHOTCLOCK_2_BT_NAME) {
                        mode = "shotclock2"
                    } else if (device.name == SHOTCLOCK_3_BT_NAME) {
                        mode = "shotclock3"
                    } else if (device.name == SHOTCLOCK_4_BT_NAME) {
                        mode = "shotclock4"
                    }
                    manageMyConnectedSocket(socket, mode)
                }
            } catch (ex: Exception) {
                Log.e("Bluetooth Activity failed: ", ex.toString())
                try {
                    Log.e("Bluetooth Activity", "try again")
                    val newSocket: BluetoothSocket =
                        device.javaClass.getMethod(
                            "createRfcommSocket",
                            Int::class.javaPrimitiveType
                        )
                            .invoke(device, 1) as BluetoothSocket
                    newSocket.let { socket ->
                        socket.connect()
                        var mode = ""
                        if (device.name == MAIN_BOARD_BT_NAME) {
                            mode = "mainBoard"
                        } else if (device.name == SHOTCLOCK_1_BT_NAME) {
                            mode = "shotclock1"
                        } else if (device.name == SHOTCLOCK_2_BT_NAME) {
                            mode = "shotclock2"
                        } else if (device.name == SHOTCLOCK_3_BT_NAME) {
                            mode = "shotclock3"
                        } else if (device.name == SHOTCLOCK_4_BT_NAME) {
                            mode = "shotclock4"
                        }
                        if (!mainBoardConnected && (device == remoteMainBoard)) {
                            mainBoardConnected = true
                            manageMyConnectedSocket(newSocket, mode)
                        } else if (!shotclock1Connected && (device == remoteShotclock1)) {
                            shotclock1Connected = true
                            manageMyConnectedSocket(newSocket, mode)
                        } else if (!shotclock2Connected && (device == remoteShotclock2)) {
                            shotclock2Connected = true
                            manageMyConnectedSocket(newSocket, mode)
                        } else if (!shotclock3Connected && (device == remoteShotclock3)) {
                            shotclock3Connected = true
                            manageMyConnectedSocket(newSocket, mode)
                        } else if (!shotclock4Connected && (device == remoteShotclock4)) {
                            shotclock4Connected = true
                            manageMyConnectedSocket(newSocket, mode)
                        }
                    }
                } catch (e1: IOException) {
                    Log.e("BT", "Fallback failed. Cancelling.", e1)
//                        mainBoardIsConnected = false
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
                        Log.d(ContentValues.TAG, "paired device loop")
                        if (device.name == MAIN_BOARD_BT_NAME) {
                            Log.d(ContentValues.TAG, "mainboard found")
                            myMainBoard = device
                            viewModel.mainBoardPaired.value = true
                            remoteMainBoard = bluetoothAdapter.getRemoteDevice(myMainBoard.address)
                            mainBoard_myUUID = myMainBoard.uuids[0].uuid
                        }

                        if (device.name == SHOTCLOCK_1_BT_NAME) {
                            Log.d(ContentValues.TAG, "shotclock1 found")
                            shotclock1 = device
                            viewModel.shotclock1Paired.value = true
                            remoteShotclock1 = bluetoothAdapter.getRemoteDevice(shotclock1.address)
                            shotclock1_myUUID = shotclock1.uuids[0].uuid
                        }

                        if (device.name == SHOTCLOCK_2_BT_NAME) {
                            Log.d(ContentValues.TAG, "shotclock2 found")
                            shotclock2 = device
                            viewModel.shotclock2Paired.value = true
                            remoteShotclock2 = bluetoothAdapter.getRemoteDevice(shotclock2.address)
                            shotclock2_myUUID = shotclock2.uuids[0].uuid
                        }

                        if (device.name == SHOTCLOCK_3_BT_NAME) {
                            Log.d(ContentValues.TAG, "shotclock3 found")
                            shotclock3 = device
                            viewModel.shotclock3Paired.value = true
                            remoteShotclock3 = bluetoothAdapter.getRemoteDevice(shotclock3.address)
                            shotclock3_myUUID = shotclock3.uuids[0].uuid
                        }

                        if (device.name == SHOTCLOCK_4_BT_NAME) {
                            Log.d(ContentValues.TAG, "shotclock4 found")
                            shotclock4 = device
                            viewModel.shotclock4Paired.value = true
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
            Toast.makeText(myActivity, "device does not support bluetooth", Toast.LENGTH_LONG)
                .show()
        }
    }
}