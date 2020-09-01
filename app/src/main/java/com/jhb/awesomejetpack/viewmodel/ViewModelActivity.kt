package com.jhb.awesomejetpack.viewmodel

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jhb.awesomejetpack.util.log

/**
 * @author jhb
 * @date 2020/8/31
 */
class ViewModelActivity : AppCompatActivity() {

    //初始化 UserViewModel 通过 ViewModelProvider
//    private val userViewModel by lazy { ViewModelProvider(this)[UserViewModel::class.java] }
    private val userViewModel by lazy { ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))[UserViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val button = Button(this)
        setContentView(button)

        //观察 User 数据,并打印
        userViewModel.userLiveData.observe(this, Observer { user ->
            "User = $user".log()
        })

        //点击按钮更新 User 信息
        button.setOnClickListener {
            userViewModel.updateUser()
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return super.onRetainCustomNonConfigurationInstance()
    }
}