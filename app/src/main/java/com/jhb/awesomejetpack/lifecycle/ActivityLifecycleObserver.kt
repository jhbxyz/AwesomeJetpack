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

    var mTag = javaClass.simpleName
    override fun onCreate() {
        "onCreate ".logWithTag(mTag)
    }

    override fun onStart() {
        "onStart ".logWithTag(mTag)
    }

    override fun onResume() {
        "onResume ".logWithTag(mTag)
    }

    override fun onPause() {
        "onPause ".logWithTag(mTag)
    }

    override fun onStop() {
        "onStop ".logWithTag(mTag)
    }

    override fun onDestroy() {
        "onDestroy ".logWithTag(mTag)
    }

    override fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event) {
        "onLifecycleChanged  owner = $owner     event = $event".logWithTag(mTag)
    }

}