package com.prasunmondal.mbros_delivery.locationUtils


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.Messenger
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.prasunmondal.mbros_delivery.R
import kotlinx.android.synthetic.main.activity_job_service_demo.*
import java.text.DateFormat
import java.util.*

class JobServiceDemoActivity : AppCompatActivity() {

    // as google doc says
    // Handler for incoming messages from the service.
    private var mHandler: JobServiceDemoActivity.IncomingMessageHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_service_demo)
        println("something .....................................")
        setSupportActionBar(toolbar)
        requestPermissions()
        println("something .....................................")
//        locationMsg = findViewById(R.id.location)
        mHandler = JobServiceDemoActivity().IncomingMessageHandler()
        println("something ....................................." + mHandler)

    }

    /**
     * Callback received when a permissions request has been completed.
     */
    override  fun onRequestPermissionsResult(
        requestCode: Int, @NonNull permissions: Array<String?>,
        @NonNull grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == JobServiceDemoActivity.Companion.REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(
                    TAG, "User interaction was cancelled."
                )
                finish()
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                println("......................... permission granted")
                println("......................... permission granted = value " + mHandler)
                // can be schedule in this way also
                //  Utils.scheduleJob(this, LocationUpdatesService.class);
                //doing this way to communicate via messenger
                // Start service and provide it a way to communicate with this class.
                val startServiceIntent = Intent(this@JobServiceDemoActivity, LocationUpdatesService::class.java)
                val messengerIncoming = Messenger(mHandler)
                startServiceIntent.putExtra(
                    MESSENGER_INTENT_KEY,
                    messengerIncoming
                )
//                startActivity(startServiceIntent)
                startService(startServiceIntent)
                println("......................... done")
            } else {
                println("......................... permission denied")
                // Permission denied.
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler = null
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.job_service_demo, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item)
    }

    internal inner class IncomingMessageHandler : Handler() {
        override fun handleMessage(msg: Message) {
            Log.i(
                JobServiceDemoActivity.Companion.TAG,
                "handleMessage...$msg"
            )
            super.handleMessage(msg)
            when (msg.what) {
                LocationUpdatesService.LOCATION_MESSAGE -> {
                    val obj = msg.obj as Location
                    val currentDateTimeString =
                        DateFormat.getDateTimeInstance().format(Date())
                    println("""LAT :  ${obj.latitude}   LNG : ${obj.longitude}""")
                }
            }
        }
    }

    private fun requestPermissions() {
        val shouldProvideRationale: Boolean =
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(
                TAG, "Displaying permission rationale to provide additional context."
            )
            // Request permission
            ActivityCompat.requestPermissions(
                this@JobServiceDemoActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        } else {
            Log.i(JobServiceDemoActivity.Companion.TAG, "Requesting permission")
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(
                this@JobServiceDemoActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    companion object {
        private val TAG = JobServiceDemoActivity::class.java.simpleName

        /**
         * Code used in requesting runtime permissions.
         */
        private const val REQUEST_PERMISSIONS_REQUEST_CODE = 34
        const val MESSENGER_INTENT_KEY = "msg-intent-key"
    }
}

