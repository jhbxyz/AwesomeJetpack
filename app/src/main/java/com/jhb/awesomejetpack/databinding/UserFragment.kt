package com.jhb.awesomejetpack.databinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jhb.awesomejetpack.R

class UserFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val userViewModel = ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application))[UserViewModel::class.java]
        val binding = DataBindingUtil.inflate<ActivityUserBinding>(inflater, R.layout.activity_user, null, false)
        //ViewModel和数据绑定布局关联
        binding.vm = userViewModel
        //一定要设置lifecycleOwner,这一行代码是让XML中的LiveData和Observer建立观察链接
        //绑定每一次LiveData的更新
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

}
