package com.prasunmondal.mbros_delivery.locationUtils

import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.annotation.NonNull
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


/**
 * stand alone component for location updates
 */
class LocationUpdatesComponent(var iLocationProvider: ILocationProvider?) {
    private var mLocationRequest: LocationRequest? = null

    /**
     * Provides access to the Fused Location Provider API.
     */
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    /**
     * Callback for changes in location.
     */
    private var mLocationCallback: LocationCallback? = null

    /**
     * The current location.
     */
    private var mLocation: Location? = null

    /**
     * create first time to initialize the location components
     *
     * @param context
     */
    fun onCreate(context: Context?) {
        Log.i(TAG, "created...............")
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                Log.i(
                    TAG,
                    "onCreate...onLocationResult...............loc " + locationResult.getLastLocation()
                )
                onNewLocation(locationResult.getLastLocation())
            }
        }
        // create location request
        createLocationRequest()
        // get last known location
        getLastLocation()
    }

    /**
     * start location updates
     */
    fun onStart() {
        Log.i(TAG, "onStart ")
        //hey request for location updates
        requestLocationUpdates()
    }

    /**
     * remove location updates
     */
    fun onStop() {
        Log.i(TAG, "onStop....")
        removeLocationUpdates()
    }

    /**
     * Makes a request for location updates. Note that in this sample we merely log the
     * [SecurityException].
     */
    fun requestLocationUpdates() {
        Log.i(TAG, "Requesting location updates")
        try {
            mFusedLocationClient!!.requestLocationUpdates(
                mLocationRequest,
                mLocationCallback, Looper.getMainLooper()
            )
        } catch (unlikely: SecurityException) {
            Log.e(
                TAG,
                "Lost location permission. Could not request updates. $unlikely"
            )
        }
    }

    /**
     * Removes location updates. Note that in this sample we merely log the
     * [SecurityException].
     */
    fun removeLocationUpdates() {
        Log.i(TAG, "Removing location updates")
        try {
            mFusedLocationClient!!.removeLocationUpdates(mLocationCallback)
            //            Utils.setRequestingLocationUpdates(this, false);
//            stopSelf();
        } catch (unlikely: SecurityException) {
//            Utils.setRequestingLocationUpdates(this, true);
            Log.e(
                TAG,
                "Lost location permission. Could not remove updates. $unlikely"
            )
        }
    }//                                Toast.makeText(getApplicationContext(), "" + mLocation, Toast.LENGTH_SHORT).show();

    /**
     * get last location
     */
    fun getLastLocation()
    {
        try {
            mFusedLocationClient!!.getLastLocation()
                .addOnCompleteListener(object : OnCompleteListener<Location?> {
                    override fun onComplete(@NonNull task: Task<Location?>) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLocation = task.getResult()
                            Log.i(
                                TAG,
                                "getLastLocation $mLocation"
                            )
                            //                                Toast.makeText(getApplicationContext(), "" + mLocation, Toast.LENGTH_SHORT).show();
                            onNewLocation(mLocation)
                        } else {
                            Log.w(
                                TAG,
                                "Failed to get location."
                            )
                        }
                    }
                })
        } catch (unlikely: SecurityException) {
            Log.e(
                TAG,
                "Lost location permission.$unlikely"
            )
        }
    }

    private fun onNewLocation(location: Location?) {
        Log.i(TAG, "New location: $location")
        //        Toast.makeText(getApplicationContext(), "onNewLocation " + location, Toast.LENGTH_LONG).show();
        mLocation = location
        if (iLocationProvider != null) {
            iLocationProvider!!.onLocationUpdate(mLocation)
        }
    }

    /**
     * Sets the location request parameters.
     */
    private fun createLocationRequest() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS)
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS)
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    }

    /**
     * implements this interface to get call back of location changes
     */
    interface ILocationProvider {
        fun onLocationUpdate(location: Location?)
    }

    companion object {
        private val TAG = LocationUpdatesComponent::class.java.simpleName

        /**
         * The desired interval for location updates. Inexact. Updates may be more or less frequent.
         */
        private const val UPDATE_INTERVAL_IN_MILLISECONDS = 3 * 1000.toLong()

        /**
         * The fastest rate for active location updates. Updates will never be more frequent
         * than this value.
         */
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2
    }

}