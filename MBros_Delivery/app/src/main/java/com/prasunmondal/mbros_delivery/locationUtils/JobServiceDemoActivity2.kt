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
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.navigation.NavigationView
import com.prasunmondal.mbros_delivery.R

import kotlinx.android.synthetic.main.activity_job_service_demo2.*
import java.text.DateFormat
import java.util.*

class JobServiceDemoActivity2 : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    private var locationMsg: TextView? = null

    val MESSENGER_INTENT_KEY = "msg-intent-key"

    // as google doc says
    // Handler for incoming messages from the service.
    private var mHandler: IncomingMessageHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_service_demo2)
        setSupportActionBar(toolbar)
//        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
//        val toggle = ActionBarDrawerToggle(
//            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
//        )
//        drawer.addDrawerListener(toggle)
//        toggle.syncState()
//        val navigationView: NavigationView = findViewById(R.id.nav_view)
//        navigationView.setNavigationItemSelectedListener(this)
//        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        locationMsg = findViewById(R.id.location)
        mHandler = IncomingMessageHandler()
        requestPermissions()
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(
                    TAG,
                    "User interaction was cancelled."
                )
                finish()
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("JobServiceDemoActivity: ", "Permission grnated")
                // can be schedule in this way also
                //  Utils.scheduleJob(this, LocationUpdatesService.class);
                //doing this way to communicate via messenger
                // Start service and provide it a way to communicate with this class.
                val startServiceIntent = Intent(applicationContext, LocationUpdatesService::class.java)
                Log.e("JobServiceDemoActivity: ", mHandler.toString())
                val messengerIncoming = Messenger(mHandler)
                startServiceIntent.putExtra(
                    MESSENGER_INTENT_KEY,
                    messengerIncoming
                )
                Log.e("JobServiceDemoActivity: ", "about to start service")
                val p = startService(startServiceIntent)
                Log.e("JobServiceDemoActivity: ", "after line of start service")
                Log.e("JobServiceDemoActivity: p= ", p.toString())
            } else {
                Log.e("JobServiceDemoActivity: ", "Permission Denied")
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

//    override fun onBackPressed() {
////        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
////        if (drawer.isDrawerOpen(GravityCompat.START)) {
////            drawer.closeDrawer(GravityCompat.START)
////        } else {
////            super.onBackPressed()
////        }
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.job_service_demo, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        // Handle navigation view item clicks here.
//        val id = item.itemId
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//        } else if (id == R.id.nav_slideshow) {
//        } else if (id == R.id.nav_manage) {
//        } else if (id == R.id.nav_share) {
//        } else if (id == R.id.nav_send) {
//        }
//        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
//        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    internal inner class IncomingMessageHandler : Handler() {
        override fun handleMessage(msg: Message) {
            println("prasun mondal ----- ------- -------- ------- IncomingMessageHandler")
            Log.i(
                TAG,
                "handleMessage...$msg"
            )
            super.handleMessage(msg)
            when (msg.what) {
                LocationUpdatesService.LOCATION_MESSAGE -> {
                    println("here here here here here here here here here here here here here here here here here here here here")
                    val obj = msg.obj as Location
                    val currentDateTimeString =
                        DateFormat.getDateTimeInstance().format(Date())
                    locationMsg!!.text = """LAT :  ${obj.latitude}
LNG : ${obj.longitude}

$obj 


Last updated- $currentDateTimeString"""
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
                TAG,
                "Displaying permission rationale to provide additional context."
            )
            // Request permission
            ActivityCompat.requestPermissions(
                this@JobServiceDemoActivity2,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        } else {
            Log.i(TAG, "Requesting permission")
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(
                this@JobServiceDemoActivity2,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    companion object {
        private val TAG = JobServiceDemoActivity2::class.java.simpleName

        /**
         * Code used in requesting runtime permissions.
         */
        private const val REQUEST_PERMISSIONS_REQUEST_CODE = 34

    }
}
