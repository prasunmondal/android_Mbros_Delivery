package com.prasunmondal.mbros_delivery.LocationService

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.Messenger
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.prasunmondal.mbros_delivery.R
import java.text.DateFormat
import java.util.*

class JobServiceDemo : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    private val locationMsg: TextView? = null

    // as google doc says
    // Handler for incoming messages from the service.
    private var mHandler: IncomingMessageHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_service_demo2)
        println("onCreate -================= ------------ =========== ---------")
        //        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//
//        locationMsg = findViewById(R.id.location);
//
        mHandler = IncomingMessageHandler()
        requestPermissions()
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i(
            TAG,
            "onRequestPermissionResult"
        )
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
                // can be schedule in this way also
                //  Utils.scheduleJob(this, LocationUpdatesService.class);
                //doing this way to communicate via messenger
                // Start service and provide it a way to communicate with this class.
                val startServiceIntent = Intent(this, LocationUpdatesService::class.java)
                val messengerIncoming = Messenger(mHandler)
                startServiceIntent.putExtra(
                    MESSENGER_INTENT_KEY,
                    messengerIncoming
                )
                startService(startServiceIntent)
            } else {
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
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
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

    internal inner class IncomingMessageHandler : Handler() {
        override fun handleMessage(msg: Message) {
            Log.i(
                TAG,
                "handleMessage...$msg"
            )
            super.handleMessage(msg)
            when (msg.what) {
                LocationUpdatesService.LOCATION_MESSAGE -> {
                    val obj = msg.obj as Location
                    val currentDateTimeString =
                        DateFormat.getDateTimeInstance().format(Date())
                    println(
                        """LAT :  ${obj.latitude}
LNG : ${obj.longitude}

$obj 


Last updated- $currentDateTimeString"""
                    )
                    locationMsg!!.text = """LAT :  ${obj.latitude}
LNG : ${obj.longitude}

$obj 


Last updated- $currentDateTimeString"""
                }
            }
        }
    }

    private fun requestPermissions() {
        val shouldProvideRationale =
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
                this@JobServiceDemo,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        } else {
            Log.i(
                TAG,
                "Requesting permission"
            )
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(
                this@JobServiceDemo,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    companion object {
        private val TAG =
            JobServiceDemoActivity::class.java.simpleName

        /**
         * Code used in requesting runtime permissions.
         */
        private const val REQUEST_PERMISSIONS_REQUEST_CODE = 34
        const val MESSENGER_INTENT_KEY = "msg-intent-key"
    }
}