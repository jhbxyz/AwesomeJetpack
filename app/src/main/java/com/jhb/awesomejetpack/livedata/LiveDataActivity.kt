package com.jhb.awesomejetpack.livedata

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jhb.awesomejetpack.R
import com.jhb.awesomejetpack.base.BaseActivity
import kotlinx.android.synthetic.main.activity_live_data.*
import kotlin.concurrent.thread

/**
 * @author jhb
 * @date 2020/8/25
 */
class LiveDataActivity : BaseActivity() {
    private val mContent = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data)

        thread {
            mContent.postValue("Hello World")
        }

        btnUpdate.setOnClickListener {
            mContent.value = "最新值是:Update"
        }

        mContent.observe(this, Observer { content ->
            tvContent.text = content
        })
        //只要在值发生改变时,就能接收到
        mContent.observeForever { content ->
            tvContent.text = content
        }
    }
}