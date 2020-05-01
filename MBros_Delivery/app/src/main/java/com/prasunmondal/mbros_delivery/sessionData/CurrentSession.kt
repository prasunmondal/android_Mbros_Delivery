package com.prasunmondal.mbros_delivery.sessionData

class CurrentSession {

    object Singleton {
        var instance = CurrentSession()
    }

    private lateinit var currentCustomer: String
    private lateinit var currentCustomer_totalKG: String
    private lateinit var currentCustomer_totalPCs: String


    fun setCurrentCustomer(value: String) {
        currentCustomer = value
    }

    fun getCurrentCustomer(): String {
        return currentCustomer
    }

    fun setCurrentCustomer_totalKG(value: String) {
        currentCustomer_totalKG = value
    }

    fun getCurrentCustomer_totalKG(): String {
        return currentCustomer_totalKG
    }

    fun setCurrentCustomer_totalPCs(value: String) {
        currentCustomer_totalPCs = value
    }

    fun getCurrentCustomer_totalPCs(): String {
        return currentCustomer_totalPCs
    }


}