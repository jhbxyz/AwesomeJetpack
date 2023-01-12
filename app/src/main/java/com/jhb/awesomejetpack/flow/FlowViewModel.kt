package com.jhb.awesomejetpack.flow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.util.*

/**
 * @author jianghaibo
 * @date 2023/1/10
 */
class FlowViewModel : ViewModel() {

    val liveData = MutableLiveData<CharSequence>()


    val timeFlow = flow {
        var time = 0
        while (true) {
            time++
            println("jjj emit $time")
            emit(time)
            delay(1000)
        }
    }


    private val _sta = MutableStateFlow(0)
    val stateFlow = _sta

    val timeFlow2StateIn = timeFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun startTime() {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                _sta.value += 1
            }
        }, 0, 1000)

    }

    private val clickStateFlow = MutableStateFlow(0)
    val clickCountFlow = clickStateFlow.asStateFlow()



}