package com.prasunmondal.mbros_delivery

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.appData.FileManagerUtil.Singleton.instance as fileManagerUtil
import com.prasunmondal.mbros_delivery.sessionData.LocalConfig.Singleton.instance as localConfig
import com.prasunmondal.mbros_delivery.sessionData.AppContext.Singleton.instance as appContext
import com.prasunmondal.mbros_delivery.ui.home.SelectCurrentUser

import kotlinx.android.synthetic.main.activity_login_check.*

class LoginCheck : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_check)
        setSupportActionBar(toolbar)
        appContext.setLoginCheckActivity(this)

        if(isLoggedIn()) {
            goToCustomerSelctionPage()
        } else {
            goToLoginPage()
        }
    }

    fun goToCustomerSelctionPage() {
        val i = Intent(this@LoginCheck, SelectCurrentUser::class.java)
        startActivity(i)
        finish()
    }

    fun goToLoginPage() {
        val i = Intent(this@LoginCheck, Login::class.java)
        startActivity(i)
        finish()
    }

    fun isLoggedIn(): Boolean {
        if(fileManagerUtil.doesFileExist(fileManagerUtil.localConfigurationStorage)) {
            var loggedStatus = localConfig.getValue(localConfig.IS_LOGGED_IN)
            return loggedStatus.equals("true")
        }
        return false
    }
}
