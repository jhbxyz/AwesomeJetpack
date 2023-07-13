package com.jhb.awesomejetpack.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.jhb.awesomejetpack.bean.UserInfoOuterClass.UserInfo
import java.io.InputStream
import java.io.OutputStream

/**
 * @author jianghaibo
 * @date 2023/7/13
 */
object UserInfoSerializer : Serializer<UserInfo> {

    override val defaultValue: UserInfo
        get() = UserInfo.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserInfo = UserInfo.parseFrom(input)

    override suspend fun writeTo(t: UserInfo, output: OutputStream) {
        t.writeTo(output)
    }

}

val Context.userInfoDataStore: DataStore<UserInfo> by dataStore(
        fileName = "user_info.pb",
        serializer = UserInfoSerializer
)

