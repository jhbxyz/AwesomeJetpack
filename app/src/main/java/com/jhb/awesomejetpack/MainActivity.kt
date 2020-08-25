package com.jhb.awesomejetpack

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jhb.awesomejetpack.base.BaseActivity
import com.jhb.awesomejetpack.livedata.UserViewModel

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[UserViewModel::class.java]
        userViewModel.mUserLiveData.observe(this, Observer {

        })

    }
}
