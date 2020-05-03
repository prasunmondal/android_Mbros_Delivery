package com.prasunmondal.mbros_delivery.ui.home

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.DownloadPriceList
import com.prasunmondal.mbros_delivery.R
import com.prasunmondal.mbros_delivery.WeighingPage
import com.prasunmondal.mbros_delivery.sessionData.AppContext
import com.prasunmondal.mbros_delivery.sessionData.CurrentSession
import com.prasunmondal.mbros_delivery.sessionData.FetchedRateList

import kotlinx.android.synthetic.main.activity_select_current_user.*

class SelectCurrentUser : AppCompatActivity() {

    var text_NoCustomerToSelect = "Select Customer"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_current_user)
        setSupportActionBar(toolbar)

        AppContext.Singleton.instance.setCustomerSelectionActivity(this)
        populateCustomerListSpinner()
        setActionbarTextColor()
    }

    fun onClickSaveUsername(view: View) {
        val customerSelector = findViewById<Spinner>(R.id.customerSelector)
        val customerName: String = customerSelector.selectedItem.toString()
        CurrentSession.Singleton.instance.setCurrentCustomer_name(customerName)
        goToCalculatingPage()
    }

    fun goToCalculatingPage() {
        if(CurrentSession.Singleton.instance.getCurrentCustomer_name().equals(text_NoCustomerToSelect)) {
            Toast.makeText(this, "Select valid customer", Toast.LENGTH_LONG).show()
        } else {
            val i = Intent(this@SelectCurrentUser, WeighingPage::class.java)
            startActivity(i)
        }
    }

    fun onClickDownloadData(view: View) {
        goToDownloadPriceList()
    }

    fun goToDownloadPriceList() {
        val i = Intent(this@SelectCurrentUser, DownloadPriceList::class.java)
        startActivity(i)
        finish()
    }

    fun populateCustomerListSpinner() {
        val spinner = findViewById<View>(R.id.customerSelector) as Spinner

        var geeks = mutableListOf<String>(text_NoCustomerToSelect)
        if(FetchedRateList.Singleton.instance.isDataFetched())
            geeks.addAll(FetchedRateList.Singleton.instance.getAllUserName())
        val dataAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, geeks)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = dataAdapter
    }

    fun setActionbarTextColor() {
        val title: String = "Mondal Bros."
        val spannableTitle: Spannable = SpannableString("")
        spannableTitle.setSpan(
            ForegroundColorSpan(Color.GRAY),
            0,
            spannableTitle.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        supportActionBar!!.title = title
        window.statusBarColor = resources.getColor(R.color.selectCustomer_statusBar)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.selectCustomer_actionBar)))
    }
}
