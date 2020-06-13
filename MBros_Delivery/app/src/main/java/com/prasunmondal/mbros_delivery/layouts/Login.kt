package com.prasunmondal.mbros_delivery.layouts

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.prasunmondal.mbros_delivery.R
import com.prasunmondal.mbros_delivery.utils.mailUtils.SendMailTrigger
import com.prasunmondal.mbros_delivery.sessionData.AppContext.Singleton.instance as appContext
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class Login : AppCompatActivity() {

    val OTP_LENGTH = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)
        setActionbarTextColor()
    }

    fun onClickLogin(view: View) {
        Toast.makeText(this, "Details sent for device registration", Toast.LENGTH_LONG)
        SendMailTrigger().sendMessage("prsn.online@gmail.com", "pgrgewhikkeocgsx" ,arrayOf("prsn.online@gmail.com"), getSubject(), getMailBody(), view, "Sending Request...", "Request Sent.", false)
        if(isValidName()) {
            goToSelectCustomerPage()
        } else {
            Toast.makeText(this, "Enter a Valid Name", Toast.LENGTH_LONG).show()
        }
    }

    private fun isValidName(): Boolean {
        val password = findViewById<EditText>(R.id.loginPassword).text.toString()
        if(password.length == OTP_LENGTH)
            return decrypt(password.toInt())
        return false
    }

    private fun decrypt(value: Int): Boolean {
        val array = arrayOf(2,3,5,7)
        var temp = value
        var index = 0
        while(temp!=0 && index<array.size) {
            if(temp%array[index]==0) {
                temp /= array[index]
                index = 0
            }
            else
                index++
        }
        return temp==1
    }

    @SuppressLint("HardwareIds")
    fun generateDeviceId(): String {
        val macAddr: String
        val wifiMan =
            this.getSystemService(Context.WIFI_SERVICE) as WifiManager
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

    private fun goToSelectCustomerPage() {
        val i = Intent(this@Login, SelectCurrentUser::class.java)
        startActivity(i)
        finish()
    }
}
