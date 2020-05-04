package com.prasunmondal.mbros_delivery.MailUtils

import android.view.View
import com.google.android.material.snackbar.Snackbar

class SendMailTrigger {

    private lateinit var viewStore: View

    fun sendMessage(recipients: Array<String>, subject: String, body: String, view: View) {
        viewStore = view
        val email =
            SendEmailAsyncTask()
        email.activity = this
        email.m = Mail(
            "prsn.online@gmail.com",
            "pgrgewhikkeocgsx"
        )
        email.m!!.set_from("prsn.online@gmail.com")
        email.m!!.body = body
        email.m!!.set_to(recipients)
        email.m!!.set_subject(subject)
        email.execute()
    }

    fun displayMessage(message: String) {
        Snackbar.make(viewStore, message, Snackbar.LENGTH_INDEFINITE)
            .setAction("Action", null).show()
    }
}