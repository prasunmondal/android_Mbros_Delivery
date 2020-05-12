package com.prasunmondal.mbros_delivery.components.appMails

import com.prasunmondal.mbros_delivery.blueprints.Customer
import com.prasunmondal.mbros_delivery.components.appMails.customer.CalculationUtils.Singleton.instance as calcUtils
import com.prasunmondal.mbros_delivery.appData.CustomerManager.Singleton.instance as cm
import com.prasunmondal.mbros_delivery.sessionData.LocalConfig
import java.text.SimpleDateFormat
import java.util.*

class AdminMail: AppMails {
    private fun getHeader(): String {
        return "<tr>" +
                "<th>Name</th>" +
                "<th>Pieces</th>" +
                "<th>Weight (KG)</th>" +
                "<th>Avg Weight (KG)</th>" +
                "<th>Unit Price</th>" +
                "<th>Prev. Bal</th>" +
                "<th>Today Sale</th>" +
                "<th>Paid</th>" +
                "<th>New Bal.</th>" +
                "</tr>"
    }

    fun getRow(customer: Customer): String {
        return "<tr>" +
                "<td>" + customer.name + "</td>" +
                "<td>" + customer.totalPiece + "</td>" +
                "<td>" + customer.totalKG + "</td>" +
                "<td>" + customer.avgWeight + "</td>" +
                "<td>" + customer.unitPrice + "</td>" +
                "<td>" + customer.prevBalance + "</td>" +
                "<td>" + customer.totalTodayAmount + "</td>" +
                "<td>" + customer.paidAmount + "</td>" +
                "<td>" + customer.newBalAmount + "</td>" +
                "</tr>"
    }

    fun getAllRows(): String {
        var str = ""
        cm.customerMap.forEach {
                k, v ->
            str += getRow(v)
        }
        return str+getRow(getTotalDetails())
    }

    fun getTotalDetails(): Customer {
        var total = Customer()
        total.name = "TOTAL"
        total.totalKG = "0"
        total.totalPiece = "0"
        total.avgWeight = "0"
        total.orderedKG = "0"
        total.prevBalance = "0"
        total.totalTodayAmount = "0"
        total.paidAmount = "0"
        total.newBalAmount = "0"

        cm.customerMap.forEach {
                k, v ->
            total.totalKG = (total.totalKG.toFloat() + v.totalKG.toFloat()).toString()
            total.totalPiece = (total.totalPiece.toInt() + v.totalPiece.toInt()).toString()
            total.avgWeight = calcUtils.getAvgWeight(total)
            total.orderedKG = (total.orderedKG.toFloat() + v.orderedKG.toFloat()).toString()
            total.prevBalance = (total.prevBalance.toInt() + v.prevBalance.toInt()).toString()
            total.totalTodayAmount = (total.totalTodayAmount.toInt() + v.totalTodayAmount.toInt()).toString()
            total.paidAmount = (total.paidAmount.toInt() + v.paidAmount.toInt()).toString()
            total.newBalAmount = (total.newBalAmount.toInt() + v.newBalAmount.toInt()).toString()

            total.unitPrice = ""
        }
        return total
    }

    fun getDetails(): String {
        return getAllRows()
    }

    override fun getMailContent(): String {


        var str = getStyles() + "<table>" + getHeader() + getDetails() + "</table>"
        println("got string: " + str)
        return str
    }

    private fun getStyles(): String {
        return "<head>\n" +
                "<style>\n" +
                "table {\n" +
                "  font-family: arial, sans-serif;\n" +
                "  border-collapse: collapse;\n" +
                "  width: 100%;\n" +
                "}\n" +
                "\n" +
                "td, th {\n" +
                "  border: 1px solid #dddddd;\n" +
                "  text-align: left;\n" +
                "  padding: 8px;\n" +
                "}\n" +
                "th {\n" +
                "  background-color: #d2bebe\n" +
                "}\n" +
                "\n" +
                "tr:nth-child(even) {\n" +
                "  background-color: #f3f1f1;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n"
    }

    override fun getSubject(): String {
        return "Delivery Report (Date - " + SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date()) + ")"
    }
}