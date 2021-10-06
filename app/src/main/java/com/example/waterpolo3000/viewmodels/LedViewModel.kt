package com.example.waterpolo3000.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waterpolo3000.data.GameEventRepository
import com.example.waterpolo3000.utilities.ProcessBT
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LedViewModel@Inject internal constructor(
    gameEventRepository: GameEventRepository
): ViewModel() {

    lateinit var btHandler: ProcessBT

    val mainBoardConnected: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val mainBoardPaired: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val shotclock1Connected: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val shotclock1Paired: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val shotclock2Connected: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val shotclock2Paired: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val shotclock3Connected: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val shotclock3Paired: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val shotclock4Connected: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val shotclock4Paired: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun connectMainBoard(){
        btHandler.connectMainBoard()
    }

    fun connectShotclock1(){
        btHandler.connectShotclock1()
    }

    fun connectShotclock2(){
        btHandler.connectShotclock2()
    }
}