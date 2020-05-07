package com.prasunmondal.mbros_delivery.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.Login
import com.prasunmondal.mbros_delivery.R
import com.prasunmondal.mbros_delivery.SelectCurrentUser
import com.prasunmondal.mbros_delivery.appData.FileManagerUtil
import com.prasunmondal.mbros_delivery.sessionData.AppContext
import com.prasunmondal.mbros_delivery.sessionData.HardData

import kotlinx.android.synthetic.main.activity_try_loging_in.*
import java.util.*

class TryLogingIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_try_loging_in)
        setSupportActionBar(toolbar)
        getLoginData()
    }

    private fun getLoginData() {

        DownloadUtils(this).enqueueDownload(
            HardData.Singleton.instance.CSV_settings,
            FileManagerUtil.Singleton.instance.storageLink_CSV_Settings.destination,
            ::onComplete_fetchLoginData,
            "MBros: Getting Login Info", "fetching data...")
    }

    fun onComplete_fetchLoginData() {
        Toast.makeText(this, "Login data fetched", Toast.LENGTH_SHORT).show()
        if(isLoginEnabled()) {
            goToSelectCustomerPage()
        } else {
            goToLoginPage()
        }
    }

    private fun isLoginEnabled(): Boolean {
        var isActiveToday = FileReadUtils.Singleton.instance.getValue_forKey(
            FileManagerUtil.Singleton.instance.storageLink_CSV_Settings,
            3,
            generateDeviceId(), 2)
        println("got the key value: " + isActiveToday)
        return !isActiveToday.isNullOrEmpty() && isActiveToday!!.toLowerCase() == "true"
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

    @SuppressLint("HardwareIds")
    fun generateDeviceId(): String {
        val macAddr: String
        val wifiMan =
            AppContext.Singleton.instance.getLoginCheckActivity().getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInf = wifiMan.connectionInfo
        macAddr = wifiInf.macAddress
        val androidId: String = "" + Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
        val deviceUuid = UUID(androidId.hashCode().toLong(), macAddr.hashCode().toLong())
        return deviceUuid.toString()
    }
}
