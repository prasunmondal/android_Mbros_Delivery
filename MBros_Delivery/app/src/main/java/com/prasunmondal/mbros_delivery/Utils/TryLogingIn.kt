package com.prasunmondal.mbros_delivery.Utils

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.Login
import com.prasunmondal.mbros_delivery.R
import com.prasunmondal.mbros_delivery.SelectCurrentUser
import com.prasunmondal.mbros_delivery.appData.FileManagerUtil
import com.prasunmondal.mbros_delivery.sessionData.HardData

import kotlinx.android.synthetic.main.activity_try_loging_in.*

class TryLogingIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_try_loging_in)
        setSupportActionBar(toolbar)
        getLoginData()
    }

    private fun getLoginData() {
        // download file
        DownloadUtils(this).enqueueDownload(
            HardData.Singleton.instance.CSV_settings,
            FileManagerUtil.Singleton.instance.storageLink_CSV_Settings.destination,
            ::onComplete_fetchLoginData,
            "MBros: Getting Login Info", "fetching data...")
        // if credential available or pin matches - proceed
    }

    fun onComplete_fetchLoginData() {
        Toast.makeText(this, "Login data fetched", Toast.LENGTH_SHORT).show()
        if(loginEnabled()) {
            goToSelectCustomerPage()
        } else {
            goToLoginPage()
        }
    }

    fun loginEnabled(): Boolean {
        return false
    }

    private fun goToLoginPage() {
        val i = Intent(this@TryLogingIn, Login::class.java)
        startActivity(i)
        finish()
    }

    private fun goToSelectCustomerPage() {
        val i = Intent(this@TryLogingIn, SelectCurrentUser::class.java)
        startActivity(i)
        finish()
    }
}
