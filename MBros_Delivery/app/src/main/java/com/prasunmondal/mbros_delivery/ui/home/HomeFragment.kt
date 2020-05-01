package com.prasunmondal.mbros_delivery.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.prasunmondal.mbros_delivery.R


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)


//        val pricePerKgLabel = root.findViewById<EditText>(R.id.pricePerKg)
//        pricePerKgLabel.setText(fetchedRateList.getPricePerKg(currentSession.getCurrentCustomer()))

//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(this, Observer {
//            textView.text = it
//        })

//        for (i in 1..50) {
//            addTextBox(root)
//        }

        addTransactionRow(root)
//        val mRlayout = root.findViewById(R.id.KGLayout) as LinearLayout
//        var myEditText = EditText(context)
//        myEditText.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
//        mRlayout.addView(myEditText)

        return root
    }

//    private fun addTextBox(root: View) {
//        val linearLayout = root.findViewById<LinearLayout>(R.id.ll_example)
//        println("ll1 ------ 1 -------" + linearLayout)
//        val llh = LinearLayout(context)
//        llh.orientation = LinearLayout.HORIZONTAL
//
//        val inputKg = EditText(context)
//        inputKg.inputType= InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
//        inputKg.height=120
//        inputKg.width=507
//
//        inputKg.layoutParams = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT
//        )
//
//        inputKg.addTextChangedListener(object : TextWatcher {
//
//            override fun afterTextChanged(s: Editable) {}
//
//            override fun beforeTextChanged(
//                s: CharSequence, start: Int,
//                count: Int, after: Int
//            ) {
//            }
//
//            override fun onTextChanged(
//                s: CharSequence, start: Int,
//                before: Int, count: Int
//            ) {
////                onChangeIndividualKGs()
//            }
//        })
//
//        inputKg.setPadding(20, 20, 20, 0)// in pixels (left, top, right, bottom)
//
//        val inputPiece = EditText(context)
//        inputPiece.inputType= InputType.TYPE_CLASS_NUMBER
//        inputPiece.height=120
//        inputPiece.width=207
//
////        this.listKG.add(inputKg)
////        this.listPiece.add(inputPiece)
//
//        inputPiece.layoutParams = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT
//        )
//
//        inputPiece.setPadding(20, 20, 20, 0)// in pixels (left, top, right, bottom)
//
//        inputPiece.addTextChangedListener(object : TextWatcher {
//
//            override fun afterTextChanged(s: Editable) {}
//
//            override fun beforeTextChanged(
//                s: CharSequence, start: Int,
//                count: Int, after: Int
//            ) {
//            }
//
//            override fun onTextChanged(
//                s: CharSequence, start: Int,
//                before: Int, count: Int
//            ) {
////                onChangeIndividualPieces()
//            }
//        })
//
//        llh.addView(inputPiece)
//        llh.addView(inputKg)
//
//        println("linear lay ----" + linearLayout)
////        linearLayout.addView(llh)
//    }

    fun addTransactionRow(root: View) {
        addPieceRow(root)
        addKGRow(root)
    }

    fun addKGRow(root: View) {
        val mRlayout = root.findViewById(R.id.KGLayout) as LinearLayout
        var myEditText = EditText(context)
        myEditText.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        mRlayout.addView(myEditText)
    }

    fun addPieceRow(root: View) {
        val mRlayout = root.findViewById(R.id.PieceLayout) as LinearLayout
        var myEditText = EditText(context)
        myEditText.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        mRlayout.addView(myEditText)
    }

}