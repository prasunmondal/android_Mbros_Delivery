package com.prasunmondal.mbros_delivery

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.prasunmondal.mbros_delivery.MailUtils.Mail
import com.prasunmondal.mbros_delivery.MailUtils.SendEmailAsyncTask

class SendMail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_mail)

        var fab_send = findViewById<FloatingActionButton>(R.id.send_mail)
        fab_send.setRippleColor(Color.parseColor("#000000"))
        setActionbarTextColor()
    }

    private fun sendMessage() {
        val recipients =
            arrayOf<String>("prsn.online@gmail.com")
        val email =
            SendEmailAsyncTask()
        email.activity = this
        email.m = Mail("prsn.online@gmail.com", "7727861666325")
        email.m!!.set_from("prsn.online@gmail.com")
        email.m!!.body ="body"
        email.m!!.set_to(recipients)
        email.m!!.set_subject("subject")
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
