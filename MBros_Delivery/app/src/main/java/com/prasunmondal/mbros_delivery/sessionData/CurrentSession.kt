package com.prasunmondal.mbros_delivery.sessionData

class CurrentSession {

    object Singleton {
        var instance = CurrentSession()
    }

    lateinit var currentCustomer_name: String
    lateinit var currentCustomer_orderedQty: String
    lateinit var currentCustomer_totalPCs: String
    lateinit var currentCustomer_totalKG: String
    lateinit var currentCustomer_todaysUnitPrice: String
    lateinit var currentCustomer_todaysBillAmount: String
    lateinit var currentCustomer_prevBalance: String
    lateinit var currentCustomer_paid: String
    lateinit var currentLocationLatitude: String
    lateinit var currentLocationLongitude: String
    lateinit var currentCustomer_newBalance: String
    lateinit var sender_email: String
    lateinit var sender_email_key: String
    lateinit var receipt_email: String
    lateinit var currentCustomer_emailID: String
}