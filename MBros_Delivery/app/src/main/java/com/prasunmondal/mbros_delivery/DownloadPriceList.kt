package com.prasunmondal.mbros_delivery

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.Utils.DownloadRateList
import com.prasunmondal.mbros_delivery.appData.FileManagerUtil.Singleton.instance as fileManagerUtils
import com.prasunmondal.mbros_delivery.sessionData.HardData


class DownloadPriceList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downlaod_price_list)
        setActionbarTextColor()
        startDownloads()
    }

    private fun startDownloads() {
        val downloadRateList = DownloadRateList(this)
        downloadRateList.enqueueDownload(
            HardData.Singleton.instance.detailCSV,
            fileManagerUtils.storageLink_RateList.destination,
            ::goToSelectCustomerPage,
            "MBros: Downloading", "Downloading Data")
    }

    private fun goToSelectCustomerPage() {
        val i = Intent(this@DownloadPriceList, SelectCurrentUser::class.java)
        startActivity(i)
        finish()
    }

    override fun onBackPressed() {
    }

    private fun setActionbarTextColor() {
        val title = "Downloading Data"
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
