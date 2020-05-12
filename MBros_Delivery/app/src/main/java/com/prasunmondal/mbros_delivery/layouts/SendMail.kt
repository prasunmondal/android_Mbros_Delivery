package com.prasunmondal.mbros_delivery.layouts

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
import com.prasunmondal.mbros_delivery.R
import com.prasunmondal.mbros_delivery.appData.CustomerManager.Singleton.instance as cm
import com.prasunmondal.mbros_delivery.components.appMails.AdminMail
import com.prasunmondal.mbros_delivery.sessionData.LocalConfig
import com.prasunmondal.mbros_delivery.utils.mailUtils.SendMailTrigger
import com.prasunmondal.mbros_delivery.utils.fileUtils.FileReadUtils
import com.prasunmondal.mbros_delivery.appData.FileManagerUtil.Singleton.instance as fileManagerUtil
import com.prasunmondal.mbros_delivery.sessionData.CurrentSession.Singleton.instance as currentSession

class SendMail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_mail)

        var fab_send = findViewById<FloatingActionButton>(R.id.send_mail)
        fab_send.setRippleColor(Color.parseColor("#000000"))
        setActionbarTextColor()
        try {
            Toast.makeText(
                this,
                "Location: " + cm.current.location_lat + ", " + cm.current.location_long,
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Log.e("Show Location: ", "failed")
            e.printStackTrace()
        }
        getMailsIDs()
    }

    private fun getMailsIDs(): Array<String> {
        return FileReadUtils.Singleton.instance.getListOfValuesForKeys(fileManagerUtil.storageLink_CSV_Settings,
        0, "emailReceipt", 3)!!.toTypedArray()
    }

    fun onClickSendMail(view: View) {
        println("Sending Mail to: " + getMailsIDs())

        val adminMail =
            AdminMail()
        SendMailTrigger().sendMessage(currentSession.sender_email,
            currentSession.sender_email_key,
            getMailsIDs(),
            adminMail.getSubject(),
            adminMail.getMailContent(),
            findViewById(R.id.send_mail),
            "Sending Bill...",
            "Bill Sent.",
        true)

//        val customerMail =
//            CustomerMail()
//        if(currentSession.currentCustomer_emailID.isNotEmpty() && currentSession.currentCustomer_emailID.length>5) {
//            println("Sending Mail to: " + arrayOf(currentSession.currentCustomer_emailID))
//            SendMailTrigger().sendMessage(
//                currentSession.sender_email,
//                currentSession.sender_email_key,
//                arrayOf(currentSession.currentCustomer_emailID),
//                customerMail.getSubject(),
//                customerMail.getBody(),
//                findViewById(R.id.send_mail),
//                "Sending Bill...",
//                "Bill Sent.",
//                true
//            )
//        }
        LocalConfig.Singleton.instance.setValue("mailString", adminMail.getDetails())
        println("\n\n\nSaving mail details: " + adminMail.getDetails() + "\n\n\n\n")
        cm.save()
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
