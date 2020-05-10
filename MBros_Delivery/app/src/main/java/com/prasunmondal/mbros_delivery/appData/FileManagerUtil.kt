package com.prasunmondal.mbros_delivery.appData

import android.os.Environment
import java.io.File
import com.prasunmondal.mbros_delivery.sessionData.AppContext.Singleton.instance as appContext

class FilePaths(var rootDir: String, var childDir: String, var fileName: String) {
    var destination: String = "$rootDir/$childDir/$fileName"
}

class FileManagerUtil {

    object Singleton {
        var instance = FileManagerUtil()
    }
    var rootFromContext = appContext.getLoginCheckActivity().filesDir.absolutePath

    var localConfigurationStorage = FilePaths(rootFromContext, "AppData", "AppConfigurationData")

    var downloadLink_UpdateAPK = FilePaths(
        appContext.getLoginCheckActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString(), "", "SampleDownloadApp.apk")

    var storageLink_RateList = FilePaths(
        appContext.getLoginCheckActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString(), "", "details.csv")

    var storageLink_CSV_Settings = FilePaths(
        appContext.getLoginCheckActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString(), "", "settings")

    fun doesFileExist(filename: FilePaths): Boolean {
        val file = File(filename.destination)
        return file.exists()
    }

    fun deleteFile(filename: FilePaths) {
        val file = File(filename.destination)
        if (file.exists()) file.delete()
    }

    fun deleteAllData() {
        deleteFile(Singleton.instance.localConfigurationStorage)
        deleteFile(Singleton.instance.storageLink_RateList)
    }
}