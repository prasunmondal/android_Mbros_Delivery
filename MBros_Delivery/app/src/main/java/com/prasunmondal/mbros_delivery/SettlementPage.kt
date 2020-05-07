package com.prasunmondal.mbros_delivery

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.sessionData.FetchedRateList.Singleton.instance as fetchedRateList
import com.prasunmondal.mbros_delivery.sessionData.CurrentSession.Singleton.instance as currentSession

import kotlinx.android.synthetic.main.activity_settlement_page.*

class SettlementPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settlement_page)
        setSupportActionBar(toolbar)
        initiallize()
        setActionbarTextColor()
    }

    fun initiallize() {
        var currentUser = currentSession.getCurrentCustomer_name()
        var totalKG = currentSession.getCurrentCustomer_totalKG().toFloat()
        var totalPCs = currentSession.getCurrentCustomer_totalPCs()
        var pricePerKG  = fetchedRateList.getPricePerKg(currentUser).toFloat()
        var todaysPrice = (totalKG * pricePerKG).toInt()
        var prevBalance = fetchedRateList.getPrevBal(currentUser).toInt()
        var toPay = todaysPrice + prevBalance
        var newBalance = toPay - 0

        currentSession.setCurrentCustomer_todaysUnitPrice(pricePerKG.toString())
        currentSession.setCurrentCustomer_prevBalance(fetchedRateList.getPrevBal(currentUser).toInt().toString())
        currentSession.setCurrentCustomer_todaysBillAmount(todaysPrice.toString())

        findViewById<TextView>(R.id.kgView).text = tryNRemoveDecimal(totalKG) + " kg"
        findViewById<TextView>(R.id.pieceView).text = totalPCs
        findViewById<TextView>(R.id.todayPriceView).text = todaysPrice.toString()
        findViewById<TextView>(R.id.prevBalanceView).text = prevBalance.toString()
        findViewById<TextView>(R.id.toPayView).text = toPay.toString()
        findViewById<TextView>(R.id.newBalanceView).text = newBalance.toString()

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

    fun onChangePaidTodayView(toPay: Int) {
        var paidTodayView = findViewById<EditText>(R.id.paidTodayView)
        var paidTodayText = paidTodayView.text.toString()
        var newBalance = 0
        if(paidTodayText.length>0)
            newBalance = toPay - paidTodayText.toInt()
        findViewById<TextView>(R.id.newBalanceView).text = newBalance.toString()
    }

    fun tryNRemoveDecimal(value: Float): String {
        if(value > value.toInt()) {
            return value.toString()
        } else {
            return value.toInt().toString()
        }
    }

    fun goToSendMail(view: View) {
        cacheAllData()
        val i = Intent(this@SettlementPage, SendMail::class.java)
        startActivity(i)
    }

    fun cacheAllData() {
//        currentSession.setCurrentCustomer_totalPCs(findViewById<TextView>(R.id.pi).text.toString())
//        currentSession.setCurrentCustomer_totalKG(findViewById<TextView>(R.id.kgView).text.toString())
//        currentSession.setCurrentCustomer_todaysUnitPrice(findViewById<TextView>(R.id.pieceView).text.toString())
//        currentSession.setCurrentCustomer_todaysBillAmount(findViewById<TextView>(R.id.todayPriceView).text.toString())
//        currentSession.setCurrentCustomer_prevBalance(findViewById<TextView>(R.id.prevBalanceView).text.toString())
        currentSession.setCurrentCustomer_paid(findViewById<EditText>(R.id.paidTodayView).text.toString())
        currentSession.setCurrentCustomer_newBalance(findViewById<TextView>(R.id.newBalanceView).text.toString())
    }

    fun setActionbarTextColor() {
        val title: String = "Bill"
        val spannableTitle: Spannable = SpannableString("")
        spannableTitle.setSpan(
            ForegroundColorSpan(Color.GRAY),
            0,
            spannableTitle.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        supportActionBar!!.title = title
        window.statusBarColor = resources.getColor(R.color.settlement_page_statusBar)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.settlement_page_actionBar)))
    }
}
