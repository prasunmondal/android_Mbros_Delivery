package com.prasunmondal.mbros_delivery.sessionData


class CurrentSession {

    object Singleton {
        var instance = CurrentSession()
    }

    private lateinit var currentCustomer_name: String
    private lateinit var currentCustomer_totalPCs: String
    private lateinit var currentCustomer_totalKG: String
    private lateinit var currentCustomer_todaysUnitPrice: String
    private lateinit var currentCustomer_todaysBillAmount: String
    private lateinit var currentCustomer_prevBalance: String
    private lateinit var currentCustomer_paid: String

    private lateinit var currentCustomer_newBalance: String


    fun setCurrentCustomer_name(value: String) {
        currentCustomer_name = value
    }

    fun getCurrentCustomer_name(): String {
        return currentCustomer_name
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

    fun setCurrentCustomer_todaysUnitPrice(value: String) {
        currentCustomer_todaysUnitPrice = value
    }

    fun getCurrentCustomer_todaysUnitPrice(): String {
        return currentCustomer_todaysUnitPrice
    }

    fun setCurrentCustomer_todaysBillAmount(value: String) {
        currentCustomer_todaysBillAmount = value
    }

    fun getCurrentCustomer_todaysBillAmount(): String {
        return currentCustomer_todaysBillAmount
    }

    fun setCurrentCustomer_prevBalance(value: String) {
        currentCustomer_prevBalance = value
    }

    fun getCurrentCustomer_prevBalance(): String {
        return currentCustomer_prevBalance
    }

    fun setCurrentCustomer_paid(value: String) {
        currentCustomer_paid = value
    }

    fun getCurrentCustomer_paid(): String {
        return currentCustomer_paid
    }

    fun setCurrentCustomer_newBalance(value: String) {
        currentCustomer_newBalance = value
    }

    fun getCurrentCustomer_newBalance(): String {
        return currentCustomer_newBalance
    }

}