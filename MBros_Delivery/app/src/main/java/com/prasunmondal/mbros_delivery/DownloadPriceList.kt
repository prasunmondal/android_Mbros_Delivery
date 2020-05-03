package com.prasunmondal.mbros_delivery

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.Utils.DownloadRateList
import com.prasunmondal.mbros_delivery.sessionData.HardData

class DownloadPriceList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downlaod_price_list)
        startDownloads()
    }

    fun startDownloads() {
        var downloadRateList = DownloadRateList(this, HardData.Singleton.instance.detailCSV)
        downloadRateList.enqueueDownload(findViewById(R.id.label_DownloadingData), false, ::goToSelectCustomerPage)
    }

    fun goToSelectCustomerPage() {
        val i = Intent(this@DownloadPriceList, selectCustomer::class.java)
        startActivity(i)
        finish()
    }

    override fun onBackPressed() {
    }
}
