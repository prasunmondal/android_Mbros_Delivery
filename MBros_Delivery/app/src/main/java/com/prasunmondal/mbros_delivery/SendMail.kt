package com.prasunmondal.mbros_delivery

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Toast
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
        Toast.makeText(this,
            "Location: " + currentSession.currentLocationLatitude + ", " + currentSession.currentLocationLongitude,
            Toast.LENGTH_LONG).show()
    }

    private fun getMailBody(): String {
        var str: String = "Delivered to: " + currentSession.currentCustomer_name
        str += "\nPieces: " + currentSession.currentCustomer_totalPCs
        str += "\nWeight: " + currentSession.currentCustomer_totalKG
        println("kg ----------------" + currentSession.currentCustomer_totalKG)
        println("pc ----------------" + currentSession.currentCustomer_totalPCs)
        str += "\nAvg body weight: " + (currentSession.currentCustomer_totalKG.toFloat() / currentSession.currentCustomer_totalPCs.toInt()).toString()
        str += "\n\nUnit Price: " + currentSession.currentCustomer_todaysUnitPrice
        str += "\n\nPrevious Pending: " + currentSession.currentCustomer_prevBalance
        str += "\nToday's Amount: " + currentSession.currentCustomer_todaysBillAmount
        str += "\nPaid Amount: " + currentSession.currentCustomer_paid
        str += "\nNew Balance: " + currentSession.currentCustomer_newBalance
        str += "\n\n\nDate: " + SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        str += "\nTime: " + SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        str += "\n\n\n Location: " + currentSession.currentLocationLatitude + ", " + currentSession.currentLocationLongitude

        Log.d("Mail body:\n", str)
        return str
    }

    private fun getSubject(): String {
        return "Delivered to: " + currentSession.currentCustomer_name
    }

    fun onClickSendMail(view: View) {
        val recipients =
            arrayOf<String>("prsn.online@gmail.com")
        SendMailTrigger().sendMessage(currentSession.sender_email, currentSession.sender_email_key, recipients, getSubject(), getMailBody(), findViewById(R.id.send_mail), "Sending Bill...", "Bill Sent.")
    }

    @Suppress("DEPRECATION")
    private fun setActionbarTextColor() {
        val title = "Pranab Mondal"
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
