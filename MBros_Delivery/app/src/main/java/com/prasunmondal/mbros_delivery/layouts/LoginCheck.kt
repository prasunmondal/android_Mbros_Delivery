package com.prasunmondal.mbros_delivery.layouts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.R
import com.prasunmondal.mbros_delivery.appData.FileManagerUtil.Singleton.instance as fileManagerUtil
import com.prasunmondal.mbros_delivery.utils.DateTimeUtil.Singleton.instance as dateTimeUtil
import com.prasunmondal.mbros_delivery.sessionData.LocalConfig.Singleton.instance as localConfig
import com.prasunmondal.mbros_delivery.sessionData.AppContext.Singleton.instance as appContext

import kotlinx.android.synthetic.main.activity_login_check.*
import java.text.SimpleDateFormat
import java.util.*

class LoginCheck : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_check)
        setSupportActionBar(toolbar)
        appContext.initialContext = this
        println("In Login Check")
        resetDataOnTime()

//        if(resetDataOnTime()) {
//            goToCustomerSelctionPage()
//        } else {
//            println("Downloading Login credentials")
            goToTryLogingInPage()
//        }
    }

    private fun resetDataOnTime(): Boolean {
        try {
            val datetime3pmString = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date()) + " " + dateTimeUtil.TIME_3PM

            val lastLoggedInTimeString = localConfig.getValue(localConfig.LAST_LOGGED_IN_TIME)
            if(lastLoggedInTimeString == null)
                return false
            var lastLogin = SimpleDateFormat(dateTimeUtil.FORMAT_DATETIME).parse(lastLoggedInTimeString)
            val _3pm = SimpleDateFormat(dateTimeUtil.FORMAT_DATETIME).parse(datetime3pmString)
            val now = SimpleDateFormat(dateTimeUtil.FORMAT_DATETIME).parse(SimpleDateFormat(dateTimeUtil.FORMAT_DATETIME).format(Date()))

            val isLastLoginBefore3pm = lastLogin.before(_3pm)
            val isCurrentTimeAfter3pm = _3pm.before(now)

            println(" --------------- lastLogin: $lastLogin")
            println(" ---------------       3pm: $_3pm")
            println(" ---------------       now: $now")
            println(isLastLoginBefore3pm)
            println(isCurrentTimeAfter3pm)

            if(isLastLoginBefore3pm && isCurrentTimeAfter3pm) {
                Log.d("myLog - LoginCheck", "Deleting all data")
                deleteAllData()
                return false
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }

    private fun deleteAllData() {
        fileManagerUtil.deleteAllData()
    }

    private fun goToCustomerSelctionPage() {
        val i = Intent(this@LoginCheck, SelectCurrentUser::class.java)
        startActivity(i)
        finish()
    }

    private fun goToTryLogingInPage() {
        val i = Intent(this@LoginCheck, StartTrip::class.java)
        startActivity(i)
        finish()
    }
}
