package com.prasunmondal.mbros_delivery.layouts

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.R
import com.prasunmondal.mbros_delivery.appData.FileManagerUtil
import com.prasunmondal.mbros_delivery.sessionData.AppContext
import com.prasunmondal.mbros_delivery.sessionData.HardData
import com.prasunmondal.mbros_delivery.sessionData.LocalConfig
import com.prasunmondal.mbros_delivery.utils.DownloadUtils
import com.prasunmondal.mbros_delivery.utils.fileUtils.FileReadUtils
import com.prasunmondal.mbros_delivery.utils.mailUtils.SendMailTrigger
import kotlinx.android.synthetic.main.activity_start_trip.*
import java.util.*


class StartTrip : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_trip)
        setSupportActionBar(toolbar)
    }

    fun onClickRegisterDevice(view: View) {
        SendMailTrigger().sendMessage("prsn.online@gmail.com", "pgrgewhikkeocgsx" ,arrayOf("prsn.online@gmail.com"), "MBros - device registration", generateDeviceId(), view, "Sending Request...", "Request Sent.", false)
    }

    fun onClickStartTrip(view: View) {
        // login
        // delete all data
        FileManagerUtil.Singleton.instance.deleteAllData()
        println("After deleting - 1234567890 ------------------------")
        println(LocalConfig.Singleton.instance.getValue("mailString"))
//        (AppContext.Singleton.instance.initialContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).clearApplicationUserData()



        // download data - metadata
        DownloadUtils(this).enqueueDownload(
            HardData.Singleton.instance.CSV_settings,
            FileManagerUtil.Singleton.instance.storageLink_CSV_Settings.destination,
            ::afterMetadataDownload,
            "MBros: Getting Login Info", "fetching data...")
    }

    private fun afterMetadataDownload() {
        if(isLoginEnabled()) {
            val downloadRecord = DownloadUtils(this)
            downloadRecord.enqueueDownload(
                HardData.Singleton.instance.detailCSV,
                FileManagerUtil.Singleton.instance.storageLink_RateList.destination,
                ::goToCustomerSelctionPage,
                "MBros: Downloading", "Downloading Data"
            )
        } else {
            Toast.makeText(this, "Device not registered", Toast.LENGTH_LONG).show()
        }
    }

    private fun isLoginEnabled(): Boolean {
        val isActiveToday: String? = FileReadUtils.Singleton.instance.getValue_forKey(
            FileManagerUtil.Singleton.instance.storageLink_CSV_Settings,
            3,
            generateDeviceId(), 2)
        return !isActiveToday.isNullOrEmpty() && isActiveToday.toLowerCase() == "true"
    }

    private fun goToCustomerSelctionPage() {
        val i = Intent(this@StartTrip, SelectCurrentUser::class.java)
        startActivity(i)
        finish()
    }

    @SuppressLint("HardwareIds")
    fun generateDeviceId(): String {
        val macAddr: String
        val wifiMan =
            AppContext.Singleton.instance.initialContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
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
