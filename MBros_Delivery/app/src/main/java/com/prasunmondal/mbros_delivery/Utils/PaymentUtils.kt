package com.prasunmondal.mbros_delivery.Utils

import com.prasunmondal.mbros_delivery.sessionData.FetchedRateList.Singleton.instance as fetchedRateLists
import com.prasunmondal.mbros_delivery.sessionData.LocalConfig.Singleton.instance as localConfigs

class PaymentUtils {

    object Singleton {
        var instance = PaymentUtils()
    }

    fun isPayOptionEnabled(): Boolean {
        if (localConfigs.doesUsernameExists() && fetchedRateLists.isDataFetched()) {
            val currentUser = localConfigs.getValue(localConfigs.USERNAME)!!.toLowerCase()
            val payBill =
                fetchedRateLists.getValueByLabel(fetchedRateLists.TAG_PENDING_BILL, currentUser)
            return payBill.isNotEmpty() && payBill.toInt() > 0
        }
        return false
    }

    fun isAmountButtonVisible(): Boolean {
        return localConfigs.doesUsernameExists()
    }

    fun getOutstandingAmount(currentUser: String): Int? {
        val outstandingBal = fetchedRateLists.getValueByLabel(fetchedRateLists.TAG_CURRENT_OUTSTANDING, currentUser)
        if(outstandingBal.isNotEmpty())
            return outstandingBal.toInt()
        return null
    }

    fun getPendingBill(currentUser: String): Int? {
        val payBill = fetchedRateLists.getValueByLabel(fetchedRateLists.TAG_PENDING_BILL, currentUser)
        if(payBill.isNotEmpty())
            return payBill.toInt()
        return null
    }

    fun isDisplayButtonEnabled(): Boolean {
        if(localConfigs.doesUsernameExists() && fetchedRateLists.isDataFetched()) {
            val currentUser = localConfigs.getValue(localConfigs.USERNAME)!!
            return (localConfigs.doesUsernameExists()
                    && (getOutstandingAmount(currentUser) != null
                    || getPendingBill(currentUser) != null))
        }
        return false
    }
}