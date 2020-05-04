package com.prasunmondal.mbros_delivery

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.prasunmondal.mbros_delivery.MailUtils.SendMailTrigger
import java.text.SimpleDateFormat
import java.util.*
import com.prasunmondal.mbros_delivery.sessionData.CurrentSession.Singleton.instance as currentSession

class SendMail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_mail)

        var fab_send = findViewById<FloatingActionButton>(R.id.send_mail)
        fab_send.setRippleColor(Color.parseColor("#000000"))
        setActionbarTextColor()
    }

    private fun getMailBody(): String {
        var str: String = "Delivered to: " + currentSession.getCurrentCustomer_name()
        str += "\nPieces: " + currentSession.getCurrentCustomer_totalPCs()
        str += "\nWeight: " + currentSession.getCurrentCustomer_totalKG()
        str += "\nAvg body weight: " + (currentSession.getCurrentCustomer_totalKG().toFloat() / currentSession.getCurrentCustomer_totalPCs().toInt()).toString()
        str += "\n\nUnit Price: " + currentSession.getCurrentCustomer_todaysUnitPrice()
        str += "\n\nPrevious Pending: " + currentSession.getCurrentCustomer_prevBalance()
        str += "\nToday's Amount: " + currentSession.getCurrentCustomer_todaysBillAmount()
        str += "\nPaid Amount: " + currentSession.getCurrentCustomer_paid()
        str += "\nNew Balance: " + currentSession.getCurrentCustomer_newBalance()
        str += "\n\n\nDate: " + SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        str += "\nTime: " + SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            .format(Date())

        Log.d("Mail body:\n", str)
        return str
    }

    private fun getSubject(): String {
        return "Delivered to: " + currentSession.getCurrentCustomer_name()
    }

    fun onClickSendMail(view: View) {
        val recipients =
            arrayOf<String>("prsn.online@gmail.com")
        SendMailTrigger().sendMessage(recipients, getSubject(), getMailBody(), findViewById(R.id.send_mail), "Sending Bill...", "Bill Sent.")
    }

    fun setActionbarTextColor() {
        val title: String = "Pranab Mondal"
        val spannableTitle: Spannable = SpannableString("")
        spannableTitle.setSpan(
            ForegroundColorSpan(Color.GRAY),
            0,
            spannableTitle.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        supportActionBar!!.title = title
        window.statusBarColor = resources.getColor(R.color.sendMail_statusBar)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.sendMail_actionBar)))
    }
}
