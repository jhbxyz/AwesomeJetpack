package com.jhb.awesomejetpack.viewmodel

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jhb.awesomejetpack.R
import com.jhb.awesomejetpack.util.log

/**
 * @author jhb
 * @date 2020/8/31
 */
class ViewModelActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model)
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, FirstFragment())
            .add(R.id.flContainer, SecondFragment())
            .commit()
    }
}