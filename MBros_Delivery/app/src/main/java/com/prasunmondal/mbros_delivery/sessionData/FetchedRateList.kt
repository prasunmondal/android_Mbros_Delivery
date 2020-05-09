package com.prasunmondal.mbros_delivery.sessionData

import android.annotation.SuppressLint
import com.prasunmondal.mbros_delivery.utils.fileUtils.FileReadUtils.Singleton.instance as fileReadUtils
import com.prasunmondal.mbros_delivery.appData.FileManagerUtil.Singleton.instance as fileManagers

class FetchedRateList {

    val RateListColIndex_Name = 0
    val RateListColIndex_prevBal = 1
    val RateListColIndex_todaysPrice = 2
    val RateListColIndex_orderedKG = 3
    val RateListColIndex_PhoneNo = 4
    val RateListColIndex_EmailID = 5

    private var fetchedDataMap: MutableMap<String, String> = mutableMapOf()
    object Singleton {
        var instance = FetchedRateList()
    }

    fun getValue(key: String): String? {
        fileReadUtils.readPairCSVnPopulateMap(
            fetchedDataMap,
            fileManagers.storageLink_RateList)
        return fetchedDataMap[key]
    }

    @SuppressLint("DefaultLocale")
    fun getValueByLabel(pre: String, post: String): String {
        return getValue((pre + post.toLowerCase()))!!
    }

    fun isDataFetched(): Boolean {
        return fileManagers.doesFileExist(fileManagers.storageLink_RateList)
    }

    fun getAllUserName(): MutableList<String> {
        return fileReadUtils.getListOfValues(fileManagers.storageLink_RateList, RateListColIndex_Name)!!
    }

    fun getPricePerKg(user: String): String {
        return fileReadUtils.getValue_forKey(fileManagers.storageLink_RateList,
            RateListColIndex_Name,
            user,
            RateListColIndex_todaysPrice)!!
    }

    fun getPrevBal(user: String): String {
        return fileReadUtils.getValue_forKey(fileManagers.storageLink_RateList,
            RateListColIndex_Name,
            user,
            RateListColIndex_prevBal)!!
    }
}