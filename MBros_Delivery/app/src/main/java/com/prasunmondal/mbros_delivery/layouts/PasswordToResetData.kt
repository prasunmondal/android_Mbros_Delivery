package com.prasunmondal.mbros_delivery.layouts

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
import com.prasunmondal.mbros_delivery.R
import com.prasunmondal.mbros_delivery.sessionData.AppContext
import com.prasunmondal.mbros_delivery.appData.FileManagerUtil.Singleton.instance as fileManagerUtil

import kotlinx.android.synthetic.main.activity_password_to_reset_data.*
import java.text.SimpleDateFormat
import java.util.*

class PasswordToResetData : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_to_reset_data)
        setSupportActionBar(toolbar)
        setActionbarTextColor()
    }

    fun onClickNext(view: View) {
        if(isKeyValid()) {
            Toast.makeText(this, "Reset Complete", Toast.LENGTH_SHORT).show()
            fileManagerUtil.deleteAllData()
            goToTryLogingInPage()
        }
        else
            Toast.makeText(this, "Enter valid password", Toast.LENGTH_SHORT).show()
    }

    fun onClickBack(view: View) {
        goToSelectCurrentUser()
    }

    private fun goToTryLogingInPage() {
        val i = Intent(this@PasswordToResetData, TryLogingIn::class.java)
        startActivity(i)
        finish()
    }

    private fun goToSelectCurrentUser() {
        Toast.makeText(this, "Download Complete", Toast.LENGTH_SHORT).show()
        val i = Intent(this@PasswordToResetData, SelectCurrentUser::class.java)
        startActivity(i)
        finish()
    }

    fun isKeyValid(): Boolean {
        val editText = findViewById<EditText>(R.id.passwordToReset)
        val date = SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(Date())
        return editText.text.toString().equals(date)
    }

    fun setActionbarTextColor() {
        val title: String = "Reset Data"
        val spannableTitle: Spannable = SpannableString("")
        spannableTitle.setSpan(
            ForegroundColorSpan(Color.GRAY),
            0,
            spannableTitle.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        supportActionBar!!.title = title
        window.statusBarColor = resources.getColor(R.color.main_activity_statusBar)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.main_activity_actionBar)))
    }
}
