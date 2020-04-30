package com.prasunmondal.mbros_delivery.sessionData

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.Button
import android.widget.Toast
import com.prasunmondal.mbros_delivery.R
import com.prasunmondal.mbros_delivery.Utils.FileReadUtil.Singleton.instance as FileReadUtils
import com.prasunmondal.mbros_delivery.Utils.PaymentUtil.Singleton.instance as PaymentUtils
import com.prasunmondal.mbros_delivery.appData.FileManagerUtil.Singleton.instance as FileManagers
import com.prasunmondal.mbros_delivery.sessionData.AppContext.Singleton.instance as AppContexts
import com.prasunmondal.mbros_delivery.sessionData.LocalConfig.Singleton.instance as localConfigs

class FetchedMetaData {

    val APP_DOWNLOAD_LINK= "app_download_link"
    val APP_DOWNLOAD_VERSION= "app_versCode"
    val PAYMENT_UPI_PAY_UPIID= "upi_paymentID"
    val PAYMENT_UPI_PAY_NAME = "upi_paymentReceivePersonName"
    val PAYMENT_UPI_PAY_DESCRIPTION= "upi_paymentDescription"

    val TAG_CURRENT_OUTSTANDING = "currentOutstanding_"
    val TAG_PENDING_BILL = "pendingBill_"

    private var fetchedDataMap: MutableMap<String, String> = mutableMapOf()
    object Singleton {
        var instance = FetchedMetaData()
    }

    fun getValue(key: String): String? {
        FileReadUtils.readPairCSVnPopulateMap(
            fetchedDataMap,
            FileManagers.downloadLink_Metadata)
        return fetchedDataMap[key]
    }

    fun getValueByLabel(pre: String, post: String): String {
        return getValue((pre + post.toLowerCase()))!!
    }

    fun isDataFetched(): Boolean {
        return FileManagers.doesFileExist(FileManagers.downloadLink_Metadata)
    }

    fun onDownload_UpdateInfoView() {

    }

//    @SuppressLint("DefaultLocale")

//    fun updateButtonData(isRefresh: Boolean) {
//        if(isRefresh)
//            Toast.makeText(AppContexts.getMainActivity(), "Refresh Complete", Toast.LENGTH_SHORT).show()
//        val payBillBtn =
//            (AppContexts.getMainActivity() as Activity).findViewById(R.id.pay_bill_btn) as Button
//        var showString: String
//        if(PaymentUtils.isAmountButtonVisible()) {
//            val currentUser = localConfigs.getValue(localConfigs.USERNAME)!!.toLowerCase()
//            val payBill = PaymentUtils.getPendingBill(currentUser)
//            val outstandingBal = PaymentUtils.getOutstandingAmount(currentUser)
//
//            println("Pay Bill: $payBill")
//            println("Outstanding Bal: $outstandingBal")
//
//            if (payBill!=null) {
//                if (payBill.toInt() > 0) {
//                    showString = "You Pay: Rs $payBill"
//                    showString += "\n(click to pay)"
//                    payBillBtn.backgroundTintList =
//                        ColorStateList.valueOf(Color.rgb(204, 0, 0))
//                    payBillBtn.setTextColor(Color.rgb(255, 255, 255))
//                } else {
//                    showString = "You Get\nRs " + (-1 * payBill.toInt()).toString()
//                    payBillBtn.backgroundTintList =
//                        ColorStateList.valueOf(Color.rgb(39, 78, 19))
//                    payBillBtn.setTextColor(Color.rgb(255, 255, 255))
//                }
//            } else if (outstandingBal!=null) {
//                showString = "Outstanding Bal\nRs $outstandingBal"
//                if (outstandingBal.toInt() > 0) {
//                    payBillBtn.backgroundTintList =
//                        ColorStateList.valueOf(Color.rgb(244, 204, 204))
//                    payBillBtn.setTextColor(Color.rgb(153, 0, 0))
//                } else {
//                    payBillBtn.backgroundTintList =
//                        ColorStateList.valueOf(Color.rgb(183, 225, 205))
//                    payBillBtn.setTextColor(Color.rgb(19, 79, 92))
//                }
//            } else {
//                showString = "Couldn't fetch data..."
//            }
//        } else {
//            showString = "No User Configured..."
//        }
//        payBillBtn.text = showString
//    }
}