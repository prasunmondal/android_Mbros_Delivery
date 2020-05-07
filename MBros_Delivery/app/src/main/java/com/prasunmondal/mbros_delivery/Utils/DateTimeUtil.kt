package com.prasunmondal.mbros_delivery.Utils

class DateTimeUtil {

    val FORMAT_DATETIME = "dd-MM-yyyy HH:mm:ss"
    val FORMAT_DATE = "dd-MM-yyyy"
    val FORMAT_TIME = "HH:mm:ss"
    val TIME_3PM = "15:00:10"

    object Singleton {
        var instance = DateTimeUtil()
    }


}