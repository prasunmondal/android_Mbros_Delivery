package com.prasunmondal.mbros_delivery

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.jaredrummler.materialspinner.MaterialSpinner
import com.prasunmondal.mbros_delivery.Utils.DownloadRateList
import com.prasunmondal.mbros_delivery.sessionData.AppContext
import com.prasunmondal.mbros_delivery.sessionData.CurrentSession.Singleton.instance as currentSession
import com.prasunmondal.mbros_delivery.sessionData.FetchedRateList.Singleton.instance as fetchedRateList
import com.prasunmondal.mbros_delivery.sessionData.AppContext.Singleton.instance as appContexts
import java.util.ArrayList
import com.prasunmondal.mbros_delivery.sessionData.HardData.Singleton.instance as HardDatas

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        AppContext.Singleton.instance.setMainActivity(this)


        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

//    private fun loadPage(url: String) {
//        val webView: WebView = findViewById(R.id.formView)
//        webView.webViewClient = object : WebViewClient() {
//
//            override fun onReceivedError(
//                view: WebView,
//                errorCode: Int,
//                description: String,
//                failingUrl: String
//            ) {
//                Toast.makeText(this@MainActivity, "Error:$description", Toast.LENGTH_SHORT).show()
//            }
//        }
//        webView.loadUrl(url)
//    }

    private fun downloadAndUpdateInfo(isRefresh: Boolean) {
        println("onCreate")
        appContexts.setMainActivity(this)
        downloadRateList = DownloadRateList(this, HardDatas.detailCSV)
        downloadRateList.enqueueDownload(findViewById(R.id.nav_view), isRefresh)
//        downloadUpdateMetadataInfo.downloadAndUpdate()
//        downloadUpdateMetadataInfo.enqueDownload2()
    }

//    fun loadAddForm(view: View) {
//        val myWebView: WebView = findViewById(R.id.formView)
//        myWebView.loadUrl(HardDatas.submitFormURL)
////        showNotification("E203","A new record has been added 1!")
//    }

//    fun loadDetails(view: View) {
//        loadPage(HardDatas.detailsFormViewPage)
//        Toast.makeText(this, "Fetching Data. Please Wait...", Toast.LENGTH_SHORT).show()
////        showNotification("E203","A new record has been added 2!")
//    }

//    @SuppressLint("DefaultLocale")
//    fun onClickPayButton(view: View) {
//        if(PaymentUtils.isPayOptionEnabled()) {
//            goToPaymentOptionsPage()
//            val currentUser =
//                localConfigs.getValue(localConfigs.USERNAME)!!.toLowerCase()
//            val amount =
//                fetchedMetaDatas.getValue(fetchedMetaDatas.TAG_PENDING_BILL + currentUser)!!
//            val note = fetchedMetaDatas.getValue(fetchedMetaDatas.PAYMENT_UPI_PAY_DESCRIPTION)
//            val name = fetchedMetaDatas.getValue(fetchedMetaDatas.PAYMENT_UPI_PAY_NAME)
//            val upiId = fetchedMetaDatas.getValue(fetchedMetaDatas.PAYMENT_UPI_PAY_UPIID)
//            println("Pay button clicked...")
//            payUsingUpi(amount, upiId!!, name!!, note!!)
//        } else if (PaymentUtils.isDisplayButtonEnabled()){
//            Toast.makeText(this, "No Payment Due!", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    fun loadEditPage(view: View) {
//        loadPage(HardDatas.editPage)
//        Toast.makeText(this, "Fetching Data. Please Wait...", Toast.LENGTH_SHORT).show()
////        showNotification(this,"E203","A new record has been added 3!")
//    }

    private lateinit var downloadRateList: DownloadRateList


    private val UPI_PAYMENT = 0
    private fun payUsingUpi(amount: String, upiId: String, name: String, note: String) {

        val uri = Uri.parse("upi://pay").buildUpon()
            .appendQueryParameter("pa", upiId)
            .appendQueryParameter("pn", name)
            .appendQueryParameter("tn", note)
            .appendQueryParameter("am", amount)
            .appendQueryParameter("cu", "INR")
            .build()


        val upiPayIntent = Intent(Intent.ACTION_VIEW)
        upiPayIntent.data = uri

        // will always show a dialog to user to choose an app
        val chooser = Intent.createChooser(upiPayIntent, "Pay with")

        // check if intent resolves
        if (null != chooser.resolveActivity(packageManager)) {
            startActivityForResult(chooser, UPI_PAYMENT)
        } else {
            Toast.makeText(this@MainActivity, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            UPI_PAYMENT -> if (Activity.RESULT_OK == resultCode || resultCode == 11) {
                if (data != null) {
                    val trxt = data.getStringExtra("response")
                    Log.d("UPI", "onActivityResult: $trxt")
                    val dataList = ArrayList<String>()
                    dataList.add(trxt)
                    upiPaymentDataOperation(dataList)
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null")
                    val dataList = ArrayList<String>()
                    dataList.add("nothing")
                    upiPaymentDataOperation(dataList)
                }
            } else {
                Log.d("UPI", "onActivityResult: " + "Return data is null") //when user simply back without payment
                val dataList = ArrayList<String>()
                dataList.add("nothing")
                upiPaymentDataOperation(dataList)
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun upiPaymentDataOperation(data: ArrayList<String>) {
        if (isConnectionAvailable(this@MainActivity)) {
            var str: String? = data[0]
            Log.d("UPIPAY", "upiPaymentDataOperation: " + str!!)
            var paymentCancel = ""
            if (str == null) str = "discard"
            var status = ""
            var approvalRefNo = ""
            val response = str.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in response.indices) {
                val equalStr = response[i].split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (equalStr.size >= 2) {
                    if (equalStr[0].toLowerCase() == "Status".toLowerCase()) {
                        status = equalStr[1].toLowerCase()
                    } else if (equalStr[0].toLowerCase() == "ApprovalRefNo".toLowerCase() || equalStr[0].toLowerCase() == "txnRef".toLowerCase()) {
                        approvalRefNo = equalStr[1]
                    }
                } else {
                    paymentCancel = "Payment cancelled by user."
                }
            }

            if (status == "success") {
                //Code to handle successful transaction here.
//                Toast.makeText(this@MainActivity, "Transaction successful.", Toast.LENGTH_SHORT).show()
                Log.d("UPI", "responseStr: $approvalRefNo")
            } else if ("Payment cancelled by user." == paymentCancel) {
//                Toast.makeText(this@MainActivity, "Payment cancelled by user.", Toast.LENGTH_SHORT).show()
            } else {
//                Toast.makeText(this@MainActivity, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show()
            }
        } else {
//            Toast.makeText(this@MainActivity, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        @Suppress("DEPRECATION")
        fun isConnectionAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val netInfo = connectivityManager.activeNetworkInfo
                if (netInfo != null && netInfo.isConnected
                    && netInfo.isConnectedOrConnecting
                    && netInfo.isAvailable) {
                    return true
                }
            }
            return false
        }
    }

    fun goToSettlementPage(view: View) {
        val i = Intent(this@MainActivity, SettlementPage::class.java)
        startActivity(i)
    }
}
