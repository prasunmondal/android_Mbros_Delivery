package com.prasunmondal.mbros_delivery.appData

import android.os.Environment
import java.io.File
import com.prasunmondal.mbros_delivery.sessionData.AppContext.Singleton.instance as AppContexts

class FilePaths(var rootDir: String, var childDir: String, var fileName: String) {
    var destination: String = "$rootDir/$childDir/$fileName"
}

class FileManagerUtil {

    object Singleton {
        var instance = FileManagerUtil()
    }
    var rootFromContext = AppContexts.getCustomerSelectionActivity().filesDir.absolutePath

    var localConfigurationStorage = FilePaths(rootFromContext, "AppData", "AppConfigurationData")

    var storageLink_RateList = FilePaths(
        AppContexts.getCustomerSelectionActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString(), "", "details.csv")

    var downloadLink_UpdateAPK = FilePaths(
        AppContexts.getCustomerSelectionActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString(), "", "SampleDownloadApp.apk")

    fun doesFileExist(filename: FilePaths): Boolean {
        val file = File(filename.destination)
        return file.exists()
    }
}