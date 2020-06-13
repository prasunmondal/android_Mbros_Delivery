package com.prasunmondal.mbros_delivery.blueprints

import java.io.Serializable

class Customer: Serializable {
    
    constructor() {
        name = ""
        orderedKG = ""
        totalPiece = ""
        totalKG = ""
        avgWeight = ""
        unitPrice = ""
        totalTodayAmount = ""
        prevBalance = ""
        paidAmount = ""
        newBalAmount = ""
        location_lat = ""
        location_long = ""
        deliverTime = ""
        email = ""
        phoneNumber = ""
    }
    
    var name: String
    var orderedKG: String
    var totalPiece: String
    var totalKG: String
    var avgWeight: String
    var unitPrice: String
    var totalTodayAmount: String
    var prevBalance: String
    var paidAmount: String
    var newBalAmount: String
    var location_lat: String
    var location_long: String
    var deliverTime: String
    var email: String
    var phoneNumber: String
}