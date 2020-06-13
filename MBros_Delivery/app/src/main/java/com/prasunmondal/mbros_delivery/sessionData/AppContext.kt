package com.prasunmondal.mbros_delivery.sessionData

import android.content.Context

class AppContext {

    object Singleton {
        val instance = AppContext()
    }

    lateinit var initialContext: Context
    private lateinit var mainActivity: Context
    private lateinit var saveUserActivity: Context
    private lateinit var loginCheck: Context

    fun getMainActivity(): Context {
        println("Getting Context: " + this.mainActivity)
        return this.mainActivity
    }

    fun setMainActivity(value: Context) {
        println("Setting Context: "  +value)
        this.mainActivity = value
    }

    fun getCustomerSelectionActivity(): Context {
        return this.saveUserActivity
    }

    fun setCustomerSelectionActivity(value: Context) {
        this.saveUserActivity = value
    }

    fun setLoginCheckActivity(value: Context) {
        this.loginCheck = value
    }

    fun getLoginCheckActivity(): Context {
        return this.loginCheck
    }
}