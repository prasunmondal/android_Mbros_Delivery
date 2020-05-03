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
import com.google.android.material.snackbar.Snackbar
import com.prasunmondal.mbros_delivery.MailUtils.Mail
import com.prasunmondal.mbros_delivery.MailUtils.SendEmailAsyncTask
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
        Log.d("Mail body:\n", str)
        return str
    }

    private fun getSubject(): String {
        return "Delivered to: " + currentSession.getCurrentCustomer_name()
    }

    private fun sendMessage() {
        val recipients =
            arrayOf<String>("prsn.online@gmail.com")
        val email =
            SendEmailAsyncTask()
        email.activity = this
        email.m = Mail("prsn.online@gmail.com", "7727861666325")
        email.m!!.set_from("prsn.online@gmail.com")
        email.m!!.body = getMailBody()
        email.m!!.set_to(recipients)
        email.m!!.set_subject(getSubject())
        email.execute()
    }

    fun onClickSendMail(view: View) {
        sendMessage()
    }

    fun displayMessage(message: String) {
        Snackbar.make(findViewById(R.id.send_mail), message, Snackbar.LENGTH_INDEFINITE)
            .setAction("Action", null).show()
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
