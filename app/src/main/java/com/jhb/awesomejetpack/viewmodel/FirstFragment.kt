package com.jhb.awesomejetpack.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.jhb.awesomejetpack.util.logWithTag

/**
 * @author jhb
 * @date 2020/9/1
 */
class FirstFragment : Fragment() {
    private val TAG = javaClass.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val userViewModel = ViewModelProvider.NewInstanceFactory().create(UserViewModel::class.java)
        "userViewModel = $userViewModel".logWithTag(TAG)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}