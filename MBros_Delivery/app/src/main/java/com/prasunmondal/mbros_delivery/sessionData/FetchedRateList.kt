package com.prasunmondal.mbros_delivery.sessionData

import com.prasunmondal.mbros_delivery.Utils.FileReadUtils.Singleton.instance as fileReadUtils
import com.prasunmondal.mbros_delivery.appData.FileManagerUtil.Singleton.instance as fileManagers

class FetchedRateList {

    val APP_DOWNLOAD_LINK= "app_download_link"
    val APP_DOWNLOAD_VERSION= "app_versCode"
    val PAYMENT_UPI_PAY_UPIID= "upi_paymentID"
    val PAYMENT_UPI_PAY_NAME = "upi_paymentReceivePersonName"
    val PAYMENT_UPI_PAY_DESCRIPTION= "upi_paymentDescription"

    val TAG_CURRENT_OUTSTANDING = "currentOutstanding_"
    val TAG_PENDING_BILL = "pendingBill_"

    val RateListColIndex_Name = 0
    val RateListColIndex_PhoneNo = 1
    val RateListColIndex_prevBal = 2
    val RateListColIndex_todaysPrice = 3
    val RateListColIndex_orderedKG = 5


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