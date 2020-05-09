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
import com.prasunmondal.mbros_delivery.sessionData.LocalConfig
import com.prasunmondal.mbros_delivery.utils.mailUtils.SendMailTrigger
import com.prasunmondal.mbros_delivery.utils.fileUtils.FileReadUtils
import com.prasunmondal.mbros_delivery.appData.FileManagerUtil.Singleton.instance as fileManagerUtil
import com.prasunmondal.mbros_delivery.sessionData.CurrentSession.Singleton.instance as currentSession

class SendMail : AppCompatActivity() {

    private var mailBody: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_mail)

        var fab_send = findViewById<FloatingActionButton>(R.id.send_mail)
        fab_send.setRippleColor(Color.parseColor("#000000"))
        setActionbarTextColor()
        Toast.makeText(this,
            "Location: " + currentSession.currentLocationLatitude + ", " + currentSession.currentLocationLongitude,
            Toast.LENGTH_LONG).show()
        mailBody = prepareMailBody()
        getMailsIDs()
    }

    private fun getMailsIDs(): Array<String> {
        return FileReadUtils.Singleton.instance.getListOfValuesForKeys(fileManagerUtil.storageLink_CSV_Settings,
        0, "emailReceipt", 3)!!.toTypedArray()
    }

    private fun prepareMailBody(): String {
        var str: String = ""

        str += "~Name\tPieces\t\tKG\t\tAvg.\tUnitPrice\tPrev. Bal\t\tToday Sale\t\tPaid\t\tNew Bal."



//        str += "\nPieces: " + currentSession.currentCustomer_totalPCs
//        str += "\nWeight: " + currentSession.currentCustomer_totalKG
//        println("kg ----------------" + currentSession.currentCustomer_totalKG)
//        println("pc ----------------" + currentSession.currentCustomer_totalPCs)
//        str += "\nAvg body weight: " + (currentSession.currentCustomer_totalKG.toFloat() / currentSession.currentCustomer_totalPCs.toInt()).toString()
//        str += "\n\nUnit Price: " + currentSession.currentCustomer_todaysUnitPrice
//        str += "\n\nPrevious Pending: " + currentSession.currentCustomer_prevBalance
//        str += "\nToday's Amount: " + currentSession.currentCustomer_todaysBillAmount
//        str += "\nPaid Amount: " + currentSession.currentCustomer_paid
//        str += "\nNew Balance: " + currentSession.currentCustomer_newBalance
//        str += "\n\n\nDate: " + SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
//        str += "\nTime: " + SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
//        str += "\n\n\n Location: " + currentSession.currentLocationLatitude + ", " + currentSession.currentLocationLongitude
//        str += "\nhttps://www.google.com/maps/search/?api=1&query="+currentSession.currentLocationLatitude + "," + currentSession.currentLocationLongitude

        val temp = LocalConfig.Singleton.instance.getValue("mailString")
        if(temp != null)
            str = temp
        println("got string: " + str)
        str += "~"+currentSession.currentCustomer_name + "\t\t" + currentSession.currentCustomer_totalPCs + "\t\t" + currentSession.currentCustomer_totalKG + "\t\t" +
                (currentSession.currentCustomer_totalKG.toFloat() / currentSession.currentCustomer_totalPCs.toInt()).toString() + "\t\t" +
                currentSession.currentCustomer_todaysUnitPrice + "\t\t" + currentSession.currentCustomer_prevBalance + "\t\t\t" +
                currentSession.currentCustomer_todaysBillAmount + "\t\t\t\t" + currentSession.currentCustomer_paid + "\t\t" + currentSession.currentCustomer_newBalance

        Log.d("Mail body:\n", str)

        return str
    }

    fun splitNJoin(str: String): String {
        val t = str.split("~")
        var str = ""
        t.forEach { e -> str+="\n"+e }
        println("Output --------\n\n\n\n\n")
        println(str)
        println("\n\n\n\n\n")
        return str
    }

    private fun getSubject(): String {
        return "Delivered to: " + currentSession.currentCustomer_name
    }

    fun onClickSendMail(view: View) {
        LocalConfig.Singleton.instance.setValue("mailString", mailBody)
        println("Sending Mail to: " + getMailsIDs())
        SendMailTrigger().sendMessage(currentSession.sender_email,
            currentSession.sender_email_key,
            getMailsIDs(),
            getSubject(),
            splitNJoin(mailBody),
            findViewById(R.id.send_mail),
            "Sending Bill...",
            "Bill Sent.")

        if(currentSession.currentCustomer_emailID.isNotEmpty() && currentSession.currentCustomer_emailID.length>5) {
            println("Sending Mail to: " + arrayOf(currentSession.currentCustomer_emailID))
            SendMailTrigger().sendMessage(
                currentSession.sender_email,
                currentSession.sender_email_key,
                arrayOf(currentSession.currentCustomer_emailID),
                getSubject(),
                prepareMailBody(),
                findViewById(R.id.send_mail),
                "Sending Bill...",
                "Bill Sent."
            )
        }
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
