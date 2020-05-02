package com.prasunmondal.mbros_delivery

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.sessionData.FetchedRateList.Singleton.instance as fetchedRateList
import com.prasunmondal.mbros_delivery.sessionData.CurrentSession.Singleton.instance as currentSession

import kotlinx.android.synthetic.main.activity_settlement_page.*

class SettlementPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settlement_page)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        initiallize()
    }

    fun initiallize() {
        var currentUser = currentSession.getCurrentCustomer()
        var totalKG = currentSession.getCurrentCustomer_totalKG().toFloat()
        var pricePerKG  = fetchedRateList.getPricePerKg(currentUser).toFloat()
        var todaysPrice = totalKG.toFloat() * pricePerKG.toFloat()
        var prevBalance = fetchedRateList.getPrevBal(currentUser).toFloat()
        var toPay = todaysPrice + prevBalance
        var newBalance = toPay - 0

        findViewById<TextView>(R.id.kgView).text = totalKG.toString()
        findViewById<TextView>(R.id.priceView).text = pricePerKG.toString()
        findViewById<TextView>(R.id.todayPriceView).text = todaysPrice.toInt().toString()
        findViewById<TextView>(R.id.prevBalanceView).text = prevBalance.toInt().toString()
        findViewById<TextView>(R.id.toPayView).text = toPay.toInt().toString()
        findViewById<TextView>(R.id.newBalanceView).text = newBalance.toInt().toString()

        var paidTodayView = findViewById<EditText>(R.id.paidTodayView)
        paidTodayView.addTextChangedListener(object : TextWatcher {
            //
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                onChangePaidTodayView(toPay)
            }
        })
    }

    fun onChangePaidTodayView(toPay: Float) {
        var paidTodayView = findViewById<EditText>(R.id.paidTodayView)
        var paidTodayText = paidTodayView.text.toString()
        var newBalance = 0.0F
        if(paidTodayText.length>0)
            newBalance = toPay - paidTodayText.toFloat()
        findViewById<TextView>(R.id.newBalanceView).text = newBalance.toInt().toString()
    }
}
