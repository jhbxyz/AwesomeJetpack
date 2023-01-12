package com.jhb.awesomejetpack.flow

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.jhb.awesomejetpack.R
import com.jhb.awesomejetpack.base.BaseActivity
import kotlinx.android.synthetic.main.activity_flow.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * @author jianghaibo
 * @date 2023/1/10
 */
class FlowActivity : BaseActivity() {

    private val flowVM by viewModels<FlowViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow)
        flowVM.liveData.observe(this){

        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flowVM.stateFlow.collect {
                    tvContent.text = "$it"
                    println("jjjj $it")
                }
            }
        }
        liveData.observe(this){
            println("jjjj observe  = $it")
        }
    }

    fun start(v: View) {
        // launch()
        // launchWhenStarted()
        // repeatOnLifecycle()

        flowVM.startTime()
    }


    private val liveData = MutableLiveData<Int>()

    fun testLiveData(v: View) {
        lifecycleScope.launch {
            delay(3000)
            liveData.value = 1
            liveData.value = 2
        }

    }

    private fun repeatOnLifecycle() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flowVM.timeFlow.collect {
                    tvContent.text = "$it"
                    println("jjjj $it")
                }
            }
        }
    }

    private fun launchWhenStarted() {
        lifecycleScope.launchWhenStarted {

            flowVM.timeFlow.collect {
                tvContent.text = "$it"
                println("jjjj $it")
            }

        }
    }

    private fun launch() {
        lifecycleScope.launch {
            launch {
                // collect 触发冷流的 emit
                flowVM.timeFlow.collect {
                    tvContent.text = "$it"
                    println("jjjj $it")

                }
            }
            launch {
                // collect 触发冷流的 emit
                flowVM.timeFlow.collectLatest {
                    tvContent.text = "$it"
                    println("jjjj222 $it")
                    delay(3000)

                }
            }
            println("jjjj log")
        }
    }

}