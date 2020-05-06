package com.prasunmondal.mbros_delivery

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

    fun updateLabels() {
        var nameLabel = findViewById<TextView>(R.id.label_confirm_name)
        nameLabel.text = currentSession.getCurrentCustomer_name()
        var KGLabel = findViewById<TextView>(R.id.label_confirm_KG)
        KGLabel.text = "500 Kg"
    }

    fun goToWeighingPage() {
        val i = Intent(this@ConfirmCustomerDetails, WeighingPage::class.java)
        startActivity(i)
    }

    fun onClickNextButton(view: View) {
        goToWeighingPage()
    }

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
