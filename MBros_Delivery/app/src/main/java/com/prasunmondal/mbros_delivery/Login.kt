package com.prasunmondal.mbros_delivery

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.wifi.WifiManager
import android.os.Bundle
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.MailUtils.SendMailTrigger
import com.prasunmondal.mbros_delivery.sessionData.AppContext
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)
        setActionbarTextColor()
    }

    fun onClickLogin(view: View) {
        if(isValidName()) {
            val recipients =
                arrayOf("prsn.online@gmail.com")
            SendMailTrigger().sendMessage(recipients, getSubject(), getMailBody(), view, "Sending Request...", "Request Sent.")
        } else {
            Toast.makeText(this, "Enter a Valid Name", Toast.LENGTH_LONG).show()
        }
    }

    private fun isValidName(): Boolean {
        val password = findViewById<EditText>(R.id.loginPassword).text.toString()
        return password != ""
    }

    private fun setActionbarTextColor() {
        val title = "Login"
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

    @SuppressLint("HardwareIds")
    fun generateDeviceId(): String {
        val macAddr: String
        val wifiMan =
            AppContext.Singleton.instance.getLoginCheckActivity().getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInf = wifiMan.connectionInfo
        macAddr = wifiInf.macAddress
        val androidId: String = "" + Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
        val deviceUuid = UUID(androidId.hashCode().toLong(), macAddr.hashCode().toLong())
        return deviceUuid.toString()
    }

    private fun getMailBody(): String {
        return findViewById<EditText>(R.id.loginPassword).text.toString() + ": "+ generateDeviceId()
    }


    private fun getSubject(): String {
        return "MBros: Device Registration"
    }
}
