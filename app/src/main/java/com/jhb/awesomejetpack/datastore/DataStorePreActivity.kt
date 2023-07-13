package com.jhb.awesomejetpack.datastore

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.jhb.awesomejetpack.R
import com.jhb.awesomejetpack.util.log

/**
 * @author jianghaibo
 * @date 2023/7/12
 */
private val Context.preDataStore: DataStore<Preferences> by preferencesDataStore(name = "datastore_test")

class DataStorePreActivity : AppCompatActivity() {

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
        preDataStore.edit {
            it[intPreferencesKey(KEY_AGE)] = 18
            it[stringPreferencesKey(KEY_NAME)] = "柯南1"
            it[floatPreferencesKey(KEY_SCORE)] = 699.9f
            "保存成功".log()
        }
    }

    private suspend fun getData() {
        preDataStore.data.collect {
            (it[intPreferencesKey(KEY_AGE)] ?: -1).toString().log()
            (it[stringPreferencesKey(KEY_NAME)] ?: "未存储").log()
            (it[floatPreferencesKey(KEY_SCORE)] ?: -1f).toString().log()
        }
    }

}