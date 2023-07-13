package com.jhb.awesomejetpack.livedata

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.jhb.awesomejetpack.R
import com.jhb.awesomejetpack.base.BaseActivity
import com.jhb.awesomejetpack.livedata.mystickbus.Observable
import com.jhb.awesomejetpack.util.log
import kotlinx.android.synthetic.main.activity_live_data.*
import kotlin.concurrent.thread

/**
 * @author jhb
 * @date 2020/8/25
 *
 *
 */
class LiveDataActivity : BaseActivity() {
    private val mContent = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data)

        // thread {
        //     mContent.postValue("Hello World")
        // }
        //
        // btnUpdate.setOnClickListener {
        //     mContent.value = "最新值是:Update"
        // }
        //
        // mContent.observe(this, Observer { content ->
        //     tvContent.text = content
        // })
        // //只要在值发生改变时,就能接收到
        // mContent.observeForever { content ->
        //     tvContent.text = content
        // }
    }

    val observable = Observable<String>()

    /**
     * 送黏性事件，就是在发送事件之后再订阅该事件也能收到该事件
     */
    private var count = 0
    fun btnSendStick(v: View) {
        // mContent.value = "粘性事件"
        count++
        observable.send("aaaa ${count}")
        "aaaa  send  $count ".log()

    }

    fun btnRegisterStick(v: View) {
        // mContent.observe(this){
        //     tvContent.text = it
        // }
        observable.register(true) {
            "aaaa  observe  $it ".log()
            tvContent.text = it
        }
    }

    override fun onResume() {
        super.onResume()
        // mContent.observe(this, Observer { content ->
        //     "onResume  observe   ".log()
        //     tvContent.text = content
        // })
    }
}