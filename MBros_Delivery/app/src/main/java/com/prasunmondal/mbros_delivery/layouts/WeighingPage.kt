package com.prasunmondal.mbros_delivery.layouts

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.*
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.R
import com.prasunmondal.mbros_delivery.utils.fileUtils.FileReadUtils
import com.prasunmondal.mbros_delivery.appData.FileManagerUtil
import com.prasunmondal.mbros_delivery.sessionData.FetchedRateList.Singleton.instance as fetchedRateList
import com.prasunmondal.mbros_delivery.sessionData.CurrentSession.Singleton.instance as currentSession

import kotlinx.android.synthetic.main.activity_weighing_page.*

class WeighingPage : AppCompatActivity() {

    private var list_PieceFields: MutableList<EditText> = mutableListOf()
    private var list_KGFields: MutableList<EditText> = mutableListOf()

    private var total_KGs = 0.0
    private var total_Pieces = 0

    private var unitRowHeight = 130

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weighing_page)
        setSupportActionBar(toolbar)
        setActionbarTextColor()

//        setActionbarTextColor()
        for(i in 1..10) {
            addTransactionRow()
        }
        currentSession.currentCustomer_totalKG = "0"
        currentSession.currentCustomer_totalPCs = "0"
        updateLabels()
        initiallizeCurrentSessionDetails()
    }

    private fun initiallizeCurrentSessionDetails() {
        currentSession.sender_email = FileReadUtils.Singleton.instance.getValue_forKey(
            FileManagerUtil.Singleton.instance.storageLink_CSV_Settings, 0, "emailSender", 3)!!
        currentSession.sender_email_key = FileReadUtils.Singleton.instance.getValue_forKey(
            FileManagerUtil.Singleton.instance.storageLink_CSV_Settings, 0, "emailSenderKey", 3)!!

        currentSession.currentCustomer_emailID = FileReadUtils.Singleton.instance.getValue_forKey(
            FileManagerUtil.Singleton.instance.storageLink_RateList,
            fetchedRateList.RateListColIndex_Name,
            currentSession.currentCustomer_name,
            fetchedRateList.RateListColIndex_EmailID)!!
        println("--------------   Current Details: " + currentSession.sender_email)
        println("--------------   Current Customer email: " + currentSession.currentCustomer_emailID)
    }

    fun addTransactionRow() {
        list_PieceFields.add(addUnitRow(findViewById(R.id.PieceLayout), InputType.TYPE_CLASS_NUMBER))
        list_KGFields.add(addUnitRow(findViewById(R.id.KGLayout), InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL))
        addSerialNo(findViewById<TextView>(R.id.SerialNo))
    }

    fun addUnitRow(containerLayout: View, inputType: Int): EditText {
        val mRlayout = containerLayout as LinearLayout
        var myEditText = EditText(applicationContext)
        myEditText.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        myEditText.inputType= inputType
        myEditText.height = unitRowHeight
        mRlayout.addView(myEditText)
        setOnChangeListener(myEditText)
        return myEditText
    }
    fun addSerialNo(containerLayout: View) {
        val mRlayout = containerLayout as LinearLayout
//        val mRlayout = containerLayout as LinearLayout
        var myTextView = TextView(applicationContext)
        myTextView.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        myTextView.text = list_KGFields.size.toString() + "."
//        myTextView.setText("prasun")
        myTextView.height = unitRowHeight
        myTextView.gravity = Gravity.CENTER
        myTextView.textSize = 22.0F
        mRlayout.addView(myTextView)
//        setOnChangeListener(myTextView)
    }

    fun setOnChangeListener(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                updateLabels()
            }
        })
    }

    fun updateLabels() {
        total_KGs = 0.0
        total_Pieces = 0
        var iterator = list_KGFields.listIterator()
        for (kgs in iterator) {
            if (kgs.text.toString().length > 0) {
                total_KGs += kgs.text.toString().toDouble()
            }
        }

        iterator = list_PieceFields.listIterator()
        for (pieces in iterator) {
            if (pieces.text.toString().length > 0) {
                total_Pieces += pieces.text.toString().toInt()
            }
        }

        var labelPc = findViewById<TextView>(R.id.editText8)
        var labelKG = findViewById<TextView>(R.id.editText9)

        currentSession.currentCustomer_totalKG = total_KGs.toString()
        currentSession.currentCustomer_totalPCs = total_Pieces.toString()
        labelPc.setText(currentSession.currentCustomer_totalPCs)
        labelKG.setText(currentSession.currentCustomer_totalKG)


        var per = (total_KGs / currentSession.currentCustomer_orderedQty.toFloat() * 100)
        var pb = findViewById<ProgressBar>(R.id.weighProgress)
        pb.progress = per.toInt()
    }

    fun goToSettlementPage(view: View) {
        val i = Intent(this@WeighingPage, SettlementPage::class.java)
        startActivity(i)
    }

    fun setActionbarTextColor() {
        val title: String = currentSession.currentCustomer_name
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
