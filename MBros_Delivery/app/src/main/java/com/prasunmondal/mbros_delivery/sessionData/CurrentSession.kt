package com.prasunmondal.mbros_delivery.sessionData

class CurrentSession {

    object Singleton {
        var instance = CurrentSession()
    }

    private lateinit var currentCustomer: String

    fun setCurrentCustomer(value: String) {
        currentCustomer = value
    }

    fun getCurrentCustomer(): String {
        return currentCustomer
    }
}