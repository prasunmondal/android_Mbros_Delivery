package com.prasunmondal.mbros_delivery.utils

class NumberUtils {
    object Singleton {
        var instance = NumberUtils()
    }

    fun tryNRemoveDecimal(value: Float): String {
        if(value > value.toInt()) {
            return value.toString()
        }
        return value.toInt().toString()
    }
}