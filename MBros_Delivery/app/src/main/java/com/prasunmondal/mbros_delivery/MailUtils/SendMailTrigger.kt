package com.prasunmondal.mbros_delivery.MailUtils

import android.view.View
import com.google.android.material.snackbar.Snackbar

class SendMailTrigger {

    private lateinit var viewStore: View
    private var initialMessage: String = "Sending Mail..."
    private var finalMessage: String = "Mail Sent."

    fun sendMessage(recipients: Array<String>, subject: String, body: String, view: View, initialMessage: String, finalMessage: String) {
        this.viewStore = view
        this.initialMessage = initialMessage
        this.finalMessage = finalMessage
        displayInitialMessage()
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
        var finalDisplay = message
        if(message.equals("Mail Sent."))
            finalDisplay = finalMessage
        Snackbar.make(viewStore, finalDisplay, Snackbar.LENGTH_SHORT)
            .setAction("Action", null).show()
    }

    fun displayInitialMessage() {
        Snackbar.make(viewStore, initialMessage, Snackbar.LENGTH_INDEFINITE)
            .setAction("Action", null).show()
    }
}