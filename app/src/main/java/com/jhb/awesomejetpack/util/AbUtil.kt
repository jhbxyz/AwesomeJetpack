package com.jhb.awesomejetpack.util

import android.util.Log
import com.jhb.awesomejetpack.BuildConfig
import com.jhb.awesomejetpack.bean.LogEnum

/**
 * @author jhb
 * @date 2020/8/19
 */
fun String.log(logEnum: LogEnum = LogEnum.ERROR) {
    if (BuildConfig.DEBUG) {
        when (logEnum) {
            LogEnum.VERBOSE -> Log.v("jhb", this)
            LogEnum.DEBUG -> Log.d("jhb", this)
            LogEnum.INFO -> Log.i("jhb", this)
            LogEnum.WARN -> Log.w("jhb", this)
            LogEnum.ERROR -> Log.e("jhb", this)
        }
    }
}

fun String.logWithTag(tag: String, logEnum: LogEnum = LogEnum.ERROR) {
    if (BuildConfig.DEBUG) {
        when (logEnum) {
            LogEnum.VERBOSE -> Log.v(tag, this)
            LogEnum.DEBUG -> Log.d(tag, this)
            LogEnum.INFO -> Log.i(tag, this)
            LogEnum.WARN -> Log.w(tag, this)
            LogEnum.ERROR -> Log.e(tag, this)
        }
    }
}




