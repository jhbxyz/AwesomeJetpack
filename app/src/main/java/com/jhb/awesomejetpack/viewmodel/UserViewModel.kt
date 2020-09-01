package com.jhb.awesomejetpack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

/**
 * @author jhb
 * @date 2020/8/31
 */
//自定义 User 数据类
data class User(var userId: String = UUID.randomUUID().toString(), var userName: String)

class UserViewModel : ViewModel() {

    private val userBean = User(userName = "刀锋之影")
    // 私有的 user LiveData
    private val _user = MutableLiveData<User>().apply {
        value = userBean
    }
    // 对外暴露的,不可更改 value 值的LiveData
    var userLiveData: LiveData<User> = _user

    //更新 User 信息
    fun updateUser() {
        //重新给 _user 赋值
        _user.value = userBean.apply {
            userId = UUID.randomUUID().toString()
            userName = "更新后: userName = 泰隆"
        }
    }
}