package com.prasunmondal.mbros_delivery

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Window
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.Utils.DownloadRateList
import com.prasunmondal.mbros_delivery.sessionData.HardData
import com.prasunmondal.mbros_delivery.Utils.ToolbarUtils.Singleton.instance as toolbarUtils


class DownloadPriceList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downlaod_price_list)
        setActionbarTextColor()
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

    fun setActionbarTextColor() {
        val title: String = "Downloading Data"
        val spannableTitle: Spannable = SpannableString("")
        spannableTitle.setSpan(
            ForegroundColorSpan(Color.GRAY),
            0,
            spannableTitle.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        supportActionBar!!.title = title
        window.statusBarColor = resources.getColor(R.color.download_price_statusBar)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.download_price_actionBar)))
    }
}
