package com.prasunmondal.mbros_delivery

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.ui.home.SelectCurrentUser

import kotlinx.android.synthetic.main.activity_login_check.*

class LoginCheck : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_check)
        setSupportActionBar(toolbar)

        if(isLoggedIn()) {
            goToCustomerSelctionPage()
        } else {
            goToLoginPage()
        }
    }

    fun goToCustomerSelctionPage() {
        val i = Intent(this@LoginCheck, SelectCurrentUser::class.java)
        startActivity(i)
        finish()
    }

    fun goToLoginPage() {
        val i = Intent(this@LoginCheck, Login::class.java)
        startActivity(i)
        finish()
    }

    fun isLoggedIn(): Boolean {
        return true
    }
}
