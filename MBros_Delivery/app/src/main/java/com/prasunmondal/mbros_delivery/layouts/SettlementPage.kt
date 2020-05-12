package com.prasunmondal.mbros_delivery.layouts

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Messenger
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.R
import com.prasunmondal.mbros_delivery.components.appMails.customer.CalculationUtils.Singleton.instance as calcUtils
import com.prasunmondal.mbros_delivery.appData.CustomerManager.Singleton.instance as cm
import com.prasunmondal.mbros_delivery.utils.NumberUtils.Singleton.instance as numberUtils
import com.prasunmondal.mbros_delivery.utils.locationUtils.IncomingMessageHandler
import com.prasunmondal.mbros_delivery.utils.locationUtils.GetLocationPermission
import com.prasunmondal.mbros_delivery.utils.locationUtils.LocationUpdatesService
import com.prasunmondal.mbros_delivery.sessionData.FetchedRateList.Singleton.instance as fetchedRateList

import kotlinx.android.synthetic.main.activity_settlement_page.*

class SettlementPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settlement_page)
        setSupportActionBar(toolbar)
        getLocation()
        initiallize()
        setActionbarTextColor()
    }

    private fun getLocation() {
        val startServiceIntent = Intent(this@SettlementPage, LocationUpdatesService::class.java)
        val messengerIncoming = Messenger(IncomingMessageHandler())
        startServiceIntent.putExtra(
            GetLocationPermission.MESSENGER_INTENT_KEY,
            messengerIncoming
        )
        startService(startServiceIntent)
    }

    private fun initiallize() {
        val currentUser = cm.current.name
        val totalKG = cm.current.totalKG.toFloat()
        val totalPCs = cm.current.totalPiece
        val pricePerKG  = fetchedRateList.getPricePerKg(currentUser).toFloat()
        val todaysPrice = (totalKG * pricePerKG).toInt()
        val prevBalance = fetchedRateList.getPrevBal(currentUser).toInt()
        val toPay = todaysPrice + prevBalance
        val newBalance = toPay - 0

        cm.current.unitPrice = fetchedRateList.getPricePerKg(currentUser)
        cm.current.totalTodayAmount = calcUtils.getTodaysTotalAmount(cm.current)
        cm.current.prevBalance = fetchedRateList.getPrevBal(cm.current.name)

        findViewById<TextView>(R.id.kgView).text = (numberUtils.tryNRemoveDecimal(totalKG) + " kg")
        findViewById<TextView>(R.id.pieceView).text = totalPCs
        findViewById<TextView>(R.id.todayPriceView).text = todaysPrice.toString()
        findViewById<TextView>(R.id.prevBalanceView).text = prevBalance.toString()
        findViewById<TextView>(R.id.toPayView).text = toPay.toString()
        findViewById<TextView>(R.id.newBalanceView).text = newBalance.toString()

        val paidTodayView = findViewById<EditText>(R.id.paidTodayView)
        paidTodayView.addTextChangedListener(object : TextWatcher {

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
        val paidTodayView = findViewById<EditText>(R.id.paidTodayView)
        val paidTodayText = paidTodayView.text.toString()
        var newBalance = 0
        if(paidTodayText.isNotEmpty())
            newBalance = toPay - paidTodayText.toInt()
        findViewById<TextView>(R.id.newBalanceView).text = newBalance.toString()
    }

    fun goToSendMail(view: View) {
        cm.current.paidAmount = findViewById<EditText>(R.id.paidTodayView).text.toString()
        cm.current.newBalAmount = calcUtils.getNewBalance(cm.current)
        val i = Intent(this@SettlementPage, SendMail::class.java)
        startActivity(i)
    }

    @Suppress("DEPRECATION")
    fun setActionbarTextColor() {
        val title = "Bill"
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
