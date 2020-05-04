package com.prasunmondal.mbros_delivery

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.sessionData.LocalConfig.Singleton.instance as localConfig

import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)
        setActionbarTextColor()
    }

    fun onClickLogin(view: View) {
        if(isCorrectCredential()) {
            localConfig.setValue(localConfig.IS_LOGGED_IN, "true")
            goToCustomerSelctionPage()
        } else {
            Toast.makeText(this, "Wrong Password", Toast.LENGTH_LONG).show()
        }
    }

    private fun isCorrectCredential(): Boolean {
        var password = findViewById<EditText>(R.id.loginPassword).text.toString()
        return password.equals("Prasun")
    }

    fun goToCustomerSelctionPage() {
        val i = Intent(this@Login, SelectCurrentUser::class.java)
        startActivity(i)
        finish()
    }

    fun setActionbarTextColor() {
        val title: String = "Login"
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
