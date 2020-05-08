package com.prasunmondal.mbros_delivery

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.wifi.WifiManager
import android.os.Bundle
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.Utils.DownloadUtils
import com.prasunmondal.mbros_delivery.Utils.FileReadUtils
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
        setActionbarTextColor()
        goToSelectCustomerPage()
//        getLoginData()
    }

    private fun getLoginData() {

        DownloadUtils(this).enqueueDownload(
            HardData.Singleton.instance.CSV_settings,
            FileManagerUtil.Singleton.instance.storageLink_CSV_Settings.destination,
            ::onComplete_fetchLoginData,
            "MBros: Getting Login Info", "fetching data...")
    }

    fun onComplete_fetchLoginData() {
        if(isLoginEnabled()) {
            goToSelectCustomerPage()
        } else {
            goToLoginPage()
        }
    }

    private fun isLoginEnabled(): Boolean {
        val isActiveToday: String? = FileReadUtils.Singleton.instance.getValue_forKey(
            FileManagerUtil.Singleton.instance.storageLink_CSV_Settings,
            3,
            generateDeviceId(), 2)
        return !isActiveToday.isNullOrEmpty() && isActiveToday.toLowerCase() == "true"
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

    private fun setActionbarTextColor() {
        val title = "Trying Automatic Login"
        val spannableTitle: Spannable = SpannableString("")
        spannableTitle.setSpan(
            ForegroundColorSpan(Color.GRAY),
            0,
            spannableTitle.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        supportActionBar!!.title = title
        window.statusBarColor = resources.getColor(R.color.sendMail_statusBar)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.sendMail_actionBar)))
    }
}
