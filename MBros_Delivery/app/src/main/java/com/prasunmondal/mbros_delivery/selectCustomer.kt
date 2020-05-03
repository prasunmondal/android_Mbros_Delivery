package com.prasunmondal.mbros_delivery

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jaredrummler.materialspinner.MaterialSpinner
import com.prasunmondal.mbros_delivery.Utils.DownloadRateList
import com.prasunmondal.mbros_delivery.sessionData.HardData
import com.prasunmondal.mbros_delivery.sessionData.AppContext.Singleton.instance as appContext
import com.prasunmondal.mbros_delivery.sessionData.CurrentSession.Singleton.instance as currentSession
import com.prasunmondal.mbros_delivery.sessionData.FetchedRateList.Singleton.instance as fetchedRateList


class selectCustomer : AppCompatActivity() {

    var text_NoCustomerToSelect = "No Customer to Select"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_customer)
        appContext.setCustomerSelectionActivity(this)
        populateTodaysCustomer()
        populateCustomerListSpinner()
    }

    fun populateTodaysCustomer() {
        println("Spinner Updated")
        var geeks = mutableListOf<String>(text_NoCustomerToSelect)
        if(fetchedRateList.isDataFetched())
            geeks = fetchedRateList.getAllUserName()
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
        if(currentSession.getCurrentCustomer().equals(text_NoCustomerToSelect)) {
            Toast.makeText(this, "Select valid customer", Toast.LENGTH_LONG).show()
        } else {
            val i = Intent(this@selectCustomer, MainActivity::class.java)
            startActivity(i)
        }
    }

    private fun downloadAndUpdateInfo(isRefresh: Boolean, method: () -> Unit) {
        var downloadRateList = DownloadRateList(this, HardData.Singleton.instance.detailCSV)
        downloadRateList.enqueueDownload(findViewById(R.id.customerSelector), isRefresh, method)
    }

    fun onClickDownloadData(view: View) {
        goToDownloadPriceList()
    }

    protected fun sendEmail() {
        Log.i("Send email", "")
        val TO = arrayOf("prasun.mondal02@gmail.com")
        val CC = arrayOf("prsn.online@gmail.com")
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO)
        emailIntent.putExtra(Intent.EXTRA_CC, CC)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here")
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."))
            finish()
            Log.i("Finished sending email...", "")
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                this@selectCustomer,
                "There is no email client installed.", Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun goToDownloadPriceList() {
        val i = Intent(this@selectCustomer, DownloadPriceList::class.java)
        startActivity(i)
    }

    fun populateCustomerListSpinner() {
        val spinner = findViewById<View>(R.id.spinner) as Spinner

        var geeks = mutableListOf<String>(text_NoCustomerToSelect)
        if(fetchedRateList.isDataFetched())
            geeks = fetchedRateList.getAllUserName()

        val dataAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, geeks)

        // Drop down layout style - list view with radio button

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // attaching data adapter to spinner

        // attaching data adapter to spinner
        spinner.adapter = dataAdapter
    }
}