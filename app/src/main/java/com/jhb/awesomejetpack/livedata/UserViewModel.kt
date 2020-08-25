package com.jhb.awesomejetpack.livedata

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * @author jhb
 * @date 2020/8/18
 */
data class User(var id: Int, var name: String)

class UserViewModel(application: Application) : AndroidViewModel(application) {


    var m = ObservableField<String>()
    var mUserLiveData = MutableLiveData<User>()

    fun updateUser() {
        mUserLiveData.value = User(9527, "华安2")
    }


}