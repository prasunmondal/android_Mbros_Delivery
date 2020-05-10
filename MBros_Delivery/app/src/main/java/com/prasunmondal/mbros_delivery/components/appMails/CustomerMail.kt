package com.prasunmondal.mbros_delivery.components.appMails

import android.util.Log
import com.prasunmondal.mbros_delivery.sessionData.CurrentSession
import com.prasunmondal.mbros_delivery.sessionData.LocalConfig
import java.text.SimpleDateFormat
import java.util.*

class CustomerMail:
    AppMails {
    override fun getBody(): String {
        var str = ""

        str += "<tr>" +
                "<td>Name         </td>" +
                "<td>Pieces       </td>" +
                "<td>Weight (KG)    </td>" +
                "<td>Avg Weight (kG)    </td>" +
                "<td>Unit Price       </td>" +
                "<td>Prev. Bal    </td>" +
                "<td>Today Sale     </td>" +
                "<td>Paid     </td>" +
                "<td>New Bal.    </td>" +
                "</tr>"

        val temp = LocalConfig.Singleton.instance.getValue("mailString")
        if(temp != null)
            str = temp
        println("got string: " + str)
        str += "<tr>" +
                "<td>"+ CurrentSession.Singleton.instance.currentCustomer_name + "</td>" +
                "<td>" + CurrentSession.Singleton.instance.currentCustomer_totalPCs + "</td>" +
                "<td>" + CurrentSession.Singleton.instance.currentCustomer_totalKG + "</td><td>" +
                (CurrentSession.Singleton.instance.currentCustomer_totalKG.toFloat()
                        / CurrentSession.Singleton.instance.currentCustomer_totalPCs.toInt()).toString() + "</td>" +
                "<td>" + CurrentSession.Singleton.instance.currentCustomer_todaysUnitPrice + "</td>" +
                "<td>" + CurrentSession.Singleton.instance.currentCustomer_prevBalance + "</td><" +
                "td>" + CurrentSession.Singleton.instance.currentCustomer_todaysBillAmount + "</td>" +
                "<td>" + CurrentSession.Singleton.instance.currentCustomer_paid + "</td>" +
                "<td>" + CurrentSession.Singleton.instance.currentCustomer_newBalance + "</td>" +
                "</tr>"

        Log.d("Mail body:\n", str)

        return "<table>$str</table>"
    }

    override fun getSubject(): String {
        return "Mondal Bros. Invoice (" + SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date()) + ")"
    }
}