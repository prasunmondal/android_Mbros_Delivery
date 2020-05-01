package com.prasunmondal.mbros_delivery

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.jaredrummler.materialspinner.MaterialSpinner


class selectCustomer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_customer)
        populateTodaysCustomer()
    }

    fun onClickSaveUsername(view: View) {
        val customerSelector: MaterialSpinner = findViewById(R.id.customerSelector)
        val customerName: String = customerSelector.getItems<String>()[customerSelector.selectedIndex].toString()
        Toast.makeText(this@selectCustomer, "Selected User: " + customerName, Toast.LENGTH_SHORT).show()
    }

    fun populateTodaysCustomer() {
        val geeks = listOf("Fowler", "Beck", "Evans")
        val spinner: MaterialSpinner =
            findViewById<View>(R.id.customerSelector) as MaterialSpinner
        spinner.setItems(geeks)
    }
}
