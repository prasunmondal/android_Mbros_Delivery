package com.prasunmondal.mbros_delivery.MailUtils

import android.os.AsyncTask
import android.util.Log
import com.prasunmondal.mbros_delivery.MainActivity
import javax.mail.AuthenticationFailedException
import javax.mail.MessagingException

internal class SendEmailAsyncTask :
    AsyncTask<Void?, Void?, Boolean>() {
    var m: Mail? = null
    var activity: MainActivity? = null

    override fun doInBackground(vararg params: Void?): Boolean {
        return try {
            if (m!!.send()) {
//                activity.displayMessage("Email sent.")
            } else {
//                activity.displayMessage("Email failed to send.")
            }
            true
        } catch (e: AuthenticationFailedException) {
            Log.e(SendEmailAsyncTask::class.java.name, "Bad account details")
            e.printStackTrace()
//            activity.displayMessage("Authentication failed.")
            false
        } catch (e: MessagingException) {
            Log.e(SendEmailAsyncTask::class.java.name, "Email failed")
            e.printStackTrace()
//            activity.displayMessage("Email failed to send.")
            false
        } catch (e: Exception) {
            e.printStackTrace()
//            activity.displayMessage("Unexpected error occured.")
            false
        }
    }
}
