package com.prasunmondal.mbros_delivery.locationUtils

import android.location.Location
import android.os.Handler
import android.os.Message
import com.prasunmondal.mbros_delivery.sessionData.CurrentSession

class IncomingMessageHandler : Handler() {
    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        when (msg.what) {
            LocationUpdatesService.LOCATION_MESSAGE -> {
                val obj = msg.obj as Location
                println("""LAT :  ${obj.latitude}   LNG : ${obj.longitude}""")
                CurrentSession.Singleton.instance.currentLocationLatitude = obj.latitude.toString()
                CurrentSession.Singleton.instance.currentLocationLongitude = obj.longitude.toString()
            }
        }
    }
}