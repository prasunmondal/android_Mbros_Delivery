package com.prasunmondal.mbros_delivery.components.appMails.customer

import com.prasunmondal.mbros_delivery.blueprints.Customer

class CalculationUtils {

    object Singleton {
        var instance = CalculationUtils()
    }

    fun getTodaysTotalAmount(customer: Customer): String {
        if(customer.totalKG=="")
            customer.totalKG = "0"
        if(customer.unitPrice=="")
            customer.unitPrice = "0"

        return (customer.totalKG.toFloat() * customer.unitPrice.toFloat()).toInt().toString()
    }

    fun getNewBalance(customer: Customer): String {
        if(customer.prevBalance=="")
            customer.prevBalance = "0"
        if(customer.totalTodayAmount=="")
            customer.totalTodayAmount = "0"
        if(customer.paidAmount=="")
            customer.paidAmount = "0"

        return (customer.prevBalance.toInt() + customer.totalTodayAmount.toInt() - customer.paidAmount.toInt()).toString()
    }

    fun getAvgWeight(customer: Customer): String {
        if(customer.totalPiece=="")
            customer.totalPiece = "0"
        if(customer.totalKG=="")
            customer.totalKG = "0"

        return (customer.totalKG.toFloat() / customer.totalPiece.toFloat()).toString()
    }
}