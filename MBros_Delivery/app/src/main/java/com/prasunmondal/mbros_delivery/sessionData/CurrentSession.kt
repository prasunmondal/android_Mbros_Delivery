package com.prasunmondal.mbros_delivery.sessionData

class CurrentSession {

    object Singleton {
        var instance = CurrentSession()
    }

    lateinit var sender_email: String
    lateinit var sender_email_key: String
}