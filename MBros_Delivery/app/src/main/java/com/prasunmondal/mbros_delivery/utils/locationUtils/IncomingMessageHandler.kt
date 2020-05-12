package com.prasunmondal.mbros_delivery.utils.locationUtils

import android.location.Location
import android.os.Handler
import android.os.Message
import com.prasunmondal.mbros_delivery.appData.CustomerManager.Singleton.instance as cm

class IncomingMessageHandler : Handler() {
    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        when (msg.what) {
            LocationUpdatesService.LOCATION_MESSAGE -> {
                val obj = msg.obj as Location
                println("""LAT :  ${obj.latitude}   LNG : ${obj.longitude}""")
                cm.current.location_lat = obj.latitude.toString()
                cm.current.location_long = obj.longitude.toString()
            }
        }
    }
}