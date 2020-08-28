package com.jhb.awesomejetpack.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.jhb.awesomejetpack.base.ILifecycleObserver
import com.jhb.awesomejetpack.util.logWithTag

/**
 * @author jhb
 * @date 2020/8/19
 */
class ActivityLifecycleObserver : ILifecycleObserver {

    private var mTag = javaClass.simpleName
    override fun onCreate(owner: LifecycleOwner) {
        "onCreate ".logWithTag(mTag)
    }

    override fun onStart(owner: LifecycleOwner) {
        "onStart ".logWithTag(mTag)
    }

    override fun onResume(owner: LifecycleOwner) {
        "onResume ".logWithTag(mTag)
    }

    override fun onPause(owner: LifecycleOwner) {
        "onPause ".logWithTag(mTag)
    }

    override fun onStop(owner: LifecycleOwner) {
        "onStop ".logWithTag(mTag)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        "onDestroy ".logWithTag(mTag)
    }

    override fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event) {
        "onLifecycleChanged  owner = $owner     event = $event".logWithTag(mTag)
    }

}