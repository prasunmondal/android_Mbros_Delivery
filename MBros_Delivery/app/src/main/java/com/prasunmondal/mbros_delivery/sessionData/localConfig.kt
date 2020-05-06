package com.prasunmondal.mbros_delivery.sessionData

import java.io.File
import com.prasunmondal.mbros_delivery.Utils.FileReadUtils.Singleton.instance as FileReadUtils
import com.prasunmondal.mbros_delivery.Utils.FileWriteUtils.Singleton.instance as FileWriteUtils
import com.prasunmondal.mbros_delivery.appData.FileManagerUtil.Singleton.instance as FileManagerUtils

class LocalConfig {

    val USERNAME= "username"
    val IS_LOGGED_IN= "isLoggedIn?"

    private var localConfigMap: MutableMap<String, String> = mutableMapOf()

    object Singleton {
        var instance = LocalConfig()
    }

    fun setValue(key: String, value: String) {
        localConfigMap[key] = value

        FileWriteUtils.writeToInternalFile(FileManagerUtils.localConfigurationStorage,
            FileWriteUtils.deseriallizeFromMap(localConfigMap))
    }

    fun getValue(key: String) : String? {
        FileReadUtils.readPairCSVnPopulateMap(localConfigMap,
            FileManagerUtils.localConfigurationStorage)

        return localConfigMap[key]
    }

    fun doesUsernameExists(): Boolean {
        if(FileManagerUtils.doesFileExist(FileManagerUtils.localConfigurationStorage)) {
            println("doesUsernameExists: File Exists!")
            val username = getValue(USERNAME)
            println("Value for username: $username")
            if(username != null && username.isNotEmpty()) {
                println("doesUsernameExists: true")
                return true
            }
        }
        println("doesUsernameExists: false")
        return false
    }

    fun deleteData() {
        var destination = FileManagerUtils.localConfigurationStorage.destination
        val file = File(destination)
        if (file.exists()) file.delete()
    }
}