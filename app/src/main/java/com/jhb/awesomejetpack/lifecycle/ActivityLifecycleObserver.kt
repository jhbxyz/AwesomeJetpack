package com.jhb.awesomejetpack.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.jhb.awesomejetpack.util.logEwithT

/**
 * @author jhb
 * @date 2020/8/19
 */
class ActivityLifecycleObserver : LifecycleObserver {

    var mTag = javaClass.simpleName
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        "onCreate ".logEwithT(mTag)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        "onDestroy ".logEwithT(mTag)

    }
}