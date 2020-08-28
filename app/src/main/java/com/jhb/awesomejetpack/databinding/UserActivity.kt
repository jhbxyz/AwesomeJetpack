package com.jhb.awesomejetpack.databinding

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.jhb.awesomejetpack.R
import com.jhb.awesomejetpack.base.BaseActivity

class UserActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[UserViewModel::class.java]
        val binding = DataBindingUtil.setContentView<ActivityUserBinding>(this, R.layout.activity_user)
        //ViewModel和数据绑定布局关联
        binding.vm = userViewModel
        //一定要设置lifecycleOwner,这一行代码是让XML中的LiveData和Observer建立观察链接
        //绑定每一次LiveData的更新
        binding.lifecycleOwner = this

    }


}
