package com.prasunmondal.mbros_delivery.components.appMails

import com.prasunmondal.mbros_delivery.sessionData.CurrentSession
import com.prasunmondal.mbros_delivery.sessionData.LocalConfig
import java.text.SimpleDateFormat
import java.util.*

class CustomerMail:
    AppMails {

    private fun getHeader(): String {
        return "<tr>" +
                "<th>Name</th>" +
                "<th>Pieces</th>" +
                "<th>Weight (KG)</th>" +
                "<th>Avg Weight (kG)</th>" +
                "<th>Unit Price</th>" +
                "<th>Prev. Bal</th>" +
                "<th>Today Sale</th>" +
                "<th>Paid</th>" +
                "<th>New Bal.</th>" +
                "</tr>"
    }

    fun getDetails(): String {
        return "<tr>" +
                "<td>"+ CurrentSession.Singleton.instance.currentCustomer_name + "</td>" +
                "<td>" + CurrentSession.Singleton.instance.currentCustomer_totalPCs + "</td>" +
                "<td>" + CurrentSession.Singleton.instance.currentCustomer_totalKG + "</td><td>" +
                (CurrentSession.Singleton.instance.currentCustomer_totalKG.toFloat()
                        / CurrentSession.Singleton.instance.currentCustomer_totalPCs.toInt()).toString() + "</td>" +
                "<td>" + CurrentSession.Singleton.instance.currentCustomer_todaysUnitPrice + "</td>" +
                "<td>" + CurrentSession.Singleton.instance.currentCustomer_prevBalance + "</td>" +
                "<td>" + CurrentSession.Singleton.instance.currentCustomer_todaysBillAmount + "</td>" +
                "<td>" + CurrentSession.Singleton.instance.currentCustomer_paid + "</td>" +
                "<td>" + CurrentSession.Singleton.instance.currentCustomer_newBalance + "</td>" +
                "</tr>"
    }

    override fun getMailContent(): String {
        var str = getHeader()

        val temp = LocalConfig.Singleton.instance.getValue("mailString")
        if(temp != null)
            str = temp

        str += getStyles() + "<table>" + getDetails() + "</table>"
        println("got string: " + str)
        return str
    }

    override fun getSubject(): String {
        return "Mondal Bros. Invoice (" + SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date()) + ")"
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
}