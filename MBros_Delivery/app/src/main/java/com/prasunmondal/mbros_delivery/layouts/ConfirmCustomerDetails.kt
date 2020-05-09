package com.prasunmondal.mbros_delivery.layouts

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.R
import com.prasunmondal.mbros_delivery.utils.fileUtils.FileReadUtils
import com.prasunmondal.mbros_delivery.appData.FileManagerUtil
import com.prasunmondal.mbros_delivery.sessionData.FetchedRateList.Singleton.instance as fetchedRateList
import com.prasunmondal.mbros_delivery.sessionData.CurrentSession.Singleton.instance as currentSession

import kotlinx.android.synthetic.main.activity_confirm_customer_details.*

class ConfirmCustomerDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_customer_details)
        setSupportActionBar(toolbar)
        setActionbarTextColor()
        updateLabels()
    }

    private fun updateLabels() {
        val nameLabel = findViewById<TextView>(R.id.label_confirm_name)
        nameLabel.text = currentSession.currentCustomer_name
        val kgLabel = findViewById<TextView>(R.id.label_confirm_KG)
        currentSession.currentCustomer_orderedQty =
            FileReadUtils.Singleton.instance.getValue_forKey(
                FileManagerUtil.Singleton.instance.storageLink_RateList,
                fetchedRateList.RateListColIndex_Name,
                currentSession.currentCustomer_name,
                fetchedRateList.RateListColIndex_orderedKG)!!
        kgLabel.text = (currentSession.currentCustomer_orderedQty + " kg")
    }

    private fun goToWeighingPage() {
        val i = Intent(this@ConfirmCustomerDetails, WeighingPage::class.java)
        startActivity(i)
    }

    fun onClickNextButton(view: View) {
        goToWeighingPage()
    }

    @Suppress("DEPRECATION")
    private fun setActionbarTextColor() {
        val title = "Confirm Details"
        val spannableTitle: Spannable = SpannableString("")
        spannableTitle.setSpan(
            ForegroundColorSpan(Color.GRAY),
            0,
            spannableTitle.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        supportActionBar!!.title = title
        window.statusBarColor = resources.getColor(R.color.confirm_customer_statusBar)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.confirm_custome_actionBar)))
    }
}
