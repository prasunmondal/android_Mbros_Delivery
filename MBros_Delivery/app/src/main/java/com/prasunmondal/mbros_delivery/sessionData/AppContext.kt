package com.prasunmondal.mbros_delivery.sessionData

import android.content.Context

class AppContext {

    object Singleton {
        val instance = AppContext()
    }

    private lateinit var mainActivity: Context
    private lateinit var saveUserActivity: Context

    fun getMainActivity(): Context {
        println("Getting Context: " + this.mainActivity)
        return this.mainActivity
    }

    fun setMainActivity(value: Context) {
        println("Setting Context: "  +value)
        this.mainActivity = value
    }

    fun getSaveUserActivity(): Context {
        return this.saveUserActivity
    }

    fun setSaveUserActivity(value: Context) {
        this.saveUserActivity = value
    }
}