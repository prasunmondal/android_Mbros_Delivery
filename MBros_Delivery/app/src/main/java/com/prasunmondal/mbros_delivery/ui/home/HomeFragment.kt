package com.prasunmondal.mbros_delivery.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.prasunmondal.mbros_delivery.R
import com.prasunmondal.mbros_delivery.SettlementPage
import com.prasunmondal.mbros_delivery.sessionData.CurrentSession.Singleton.instance as currentSession
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var list_PieceFields: MutableList<EditText> = mutableListOf()
    private var list_KGFields: MutableList<EditText> = mutableListOf()

    private var total_KGs = 0.0
    private var total_Pieces = 0

    lateinit private var GRoot: View



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        GRoot = root
        for(i in 1..5) {
            addTransactionRow(root)
        }

        return root
    }

    fun addTransactionRow(root: View) {
        list_PieceFields.add(addUnitRow(root, root.findViewById(R.id.PieceLayout), InputType.TYPE_CLASS_NUMBER))
        list_KGFields.add(addUnitRow(root, root.findViewById(R.id.KGLayout), InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL))
    }

    fun addUnitRow(root: View, containerLayout: View, inputType: Int): EditText{
        val mRlayout = containerLayout as LinearLayout
        var myEditText = EditText(context)
        myEditText.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        myEditText.inputType= inputType
        mRlayout.addView(myEditText)
        setOnClickListener(myEditText)
        return myEditText
    }

    fun setOnClickListener(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            //
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
                updateLabelOnChange()
            }
        })
    }

    fun updateLabelOnChange() {
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

        var labelPc = GRoot.findViewById<EditText>(R.id.editText8)
        var labelKG = GRoot.findViewById<EditText>(R.id.editText9)

        currentSession.setCurrentCustomer_totalKG(total_KGs.toString())
        currentSession.setCurrentCustomer_totalPCs(total_Pieces.toString())
        labelPc.setText(currentSession.getCurrentCustomer_totalPCs())
        labelKG.setText(currentSession.getCurrentCustomer_totalKG())
    }
}