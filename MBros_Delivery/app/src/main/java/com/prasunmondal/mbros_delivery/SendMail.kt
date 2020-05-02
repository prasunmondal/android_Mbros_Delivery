package com.prasunmondal.mbros_delivery

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.prasunmondal.mbros_delivery.MailUtils.Mail
import com.prasunmondal.mbros_delivery.MailUtils.SendEmailAsyncTask

class SendMail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_mail)
    }

    private fun sendMessage() {
        val recipients =
            arrayOf<String>("prsn.online@gmail.com")
        val email =
            SendEmailAsyncTask()
        email.activity = this
        email.m = Mail(
            "prsn.online@gmail.com", "7727861666325"
        )
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
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
//        Snackbar.make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG)
//            .setAction("Action", null).show()
    }
}
