package com.jhb.awesomejetpack.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jhb.awesomejetpack.lifecycle.ActivityLifecycleObserver

/**
 * @author jhb
 * @date 2020/8/19
 */
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(ActivityLifecycleObserver())
    }


}