package com.prasunmondal.mbros_delivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.Toast

class selectCustomer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_customer)
    }

    fun onClickSaveUsername(view: View) {
        val customerSelector: Spinner = findViewById(R.id.customerSelector)
        val customerName: String = customerSelector.getSelectedItem().toString()

//        localConfigs.setValue("username", username)
//
//        if(isValidUserName(username)) {
//            goToMainPage()
//        }
        Toast.makeText(this@selectCustomer, "Selected User: " + customerName, Toast.LENGTH_SHORT).show()
    }
}
