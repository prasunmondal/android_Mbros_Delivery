package com.prasunmondal.mbros_delivery.utils.locationUtils

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.location.Location
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.util.Log


/**
 * location update service continues to running and getting location information
 */
class LocationUpdatesService : JobService(), LocationUpdatesComponent.ILocationProvider {
    private val MESSENGER_INTENT_KEY = "msg-intent-key"
    private var mActivityMessenger: Messenger? = null
    private var locationUpdatesComponent: LocationUpdatesComponent? = null
    override fun onStartJob(params: JobParameters): Boolean {
        Log.i(TAG, "onStartJob....")
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        Log.i(TAG, "onStopJob....")
        locationUpdatesComponent!!.onStop()
        return false
    }

    override fun onCreate() {
        Log.i(TAG, "created...............")
        locationUpdatesComponent = LocationUpdatesComponent(this)
        locationUpdatesComponent!!.onCreate(this)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand Service started")
        mActivityMessenger = intent.getParcelableExtra(MESSENGER_INTENT_KEY)
        //hey request for location updates
        locationUpdatesComponent!!.onStart()
        return Service.START_STICKY
    }

    override fun onRebind(intent: Intent) {
        // Called when a client (MainActivity in case of this sample) returns to the foreground
        // and binds once again with this service. The service should cease to be a foreground
        // service when that happens.
        Log.i(TAG, "in onRebind()")
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.i(TAG, "Last client unbound from service")
        return true // Ensures onRebind() is called when a client re-binds.
    }

    /**
     * send message by using messenger
     *
     * @param messageID
     */
    private fun sendMessage(messageID: Int, location: Location) {
        // If this service is launched by the JobScheduler, there's no callback Messenger. It
        // only exists when the MainActivity calls startService() with the callback in the Intent.
        if (mActivityMessenger == null) {
            Log.d(TAG, "Service is bound, not started. There's no callback to send a message to.")
            return
        }
        val m = Message.obtain()
        m.what = messageID
        m.obj = location
        try {
            mActivityMessenger!!.send(m)
        } catch (e: RemoteException) {
            Log.e(TAG, "Error passing service object back to activity.")
        }
    }

    companion object {
        private val TAG = LocationUpdatesService::class.java.simpleName
        const val LOCATION_MESSAGE = 9999
    }

    override fun onLocationUpdate(location: Location?) {
        sendMessage(LOCATION_MESSAGE, location!!)
    }
}