package com.jhb.awesomejetpack.datastore

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.jhb.awesomejetpack.R
import com.jhb.awesomejetpack.util.log
import kotlinx.coroutines.flow.map

/**
 * @author jianghaibo
 * @date 2023/7/12
 */
class DataStoreProtoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datastore)



        findViewById<View>(R.id.btnSave).setOnClickListener {
            lifecycleScope.launchWhenResumed {
                saveData()

            }
        }


        findViewById<View>(R.id.btnGet).setOnClickListener {
            lifecycleScope.launchWhenCreated {
                getData()

            }
        }

    }

    private suspend fun saveData() {
        userInfoDataStore.updateData {
            it.toBuilder()
                .setName("柯南1")
                .setAge(18)
                .setEmail("conan1@qq.com")
                .build()
        }
    }

    private suspend fun getData() {
        userInfoDataStore.data.collect {
            it.name.log()
            "${it.age}".log()
            it.email.log()
        }
    }

}