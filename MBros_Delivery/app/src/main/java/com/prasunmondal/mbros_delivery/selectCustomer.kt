package com.prasunmondal.mbros_delivery

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.jaredrummler.materialspinner.MaterialSpinner
import com.prasunmondal.mbros_delivery.Utils.DownloadRateList
import com.prasunmondal.mbros_delivery.sessionData.CurrentSession.Singleton.instance as currentSession
import com.prasunmondal.mbros_delivery.sessionData.AppContext.Singleton.instance as appContext
import com.prasunmondal.mbros_delivery.sessionData.FetchedRateList
import com.prasunmondal.mbros_delivery.sessionData.HardData


class selectCustomer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_customer)
        appContext.setCustomerSelectionActivity(this)
        populateTodaysCustomer()
        downloadAndUpdateInfo(false)
    }

    fun populateTodaysCustomer() {
        val geeks = FetchedRateList.Singleton.instance.getAllUserName()
        val spinner: MaterialSpinner =
            findViewById<View>(R.id.customerSelector) as MaterialSpinner
        spinner.setItems(geeks)
    }

    fun onClickSaveUsername(view: View) {
        val customerSelector: MaterialSpinner = findViewById(R.id.customerSelector)
        val customerName: String = customerSelector.getItems<String>()[customerSelector.selectedIndex].toString()
        Toast.makeText(this@selectCustomer, "Selected User: " + customerName, Toast.LENGTH_SHORT).show()
        currentSession.setCurrentCustomer(customerName)
        goToCalculatingPage()
    }

    fun goToCalculatingPage() {
        val i = Intent(this@selectCustomer, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun downloadAndUpdateInfo(isRefresh: Boolean) {
        var downloadRateList = DownloadRateList(this, HardData.Singleton.instance.detailCSV)
        downloadRateList.enqueueDownload(findViewById(R.id.customerSelector), isRefresh)
    }
}
