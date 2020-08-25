package com.jhb.awesomejetpack.util

import android.util.Log

/**
 * @author jhb
 * @date 2020/8/19
 */

fun String.logE() {
    Log.e("jiang", this)
}

fun String.logEwithT(tag: String) {
    Log.e(tag, this)
}

