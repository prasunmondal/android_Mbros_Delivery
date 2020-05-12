package com.prasunmondal.mbros_delivery.blueprints

class Customer {
    lateinit var name: String
    lateinit var orderedKG: String
    lateinit var totalPiece: String
    lateinit var totalKG: String
    lateinit var avgWeight: String
    lateinit var unitPrice: String
    lateinit var totalTodayAmount: String
    lateinit var prevBalance: String
    lateinit var paidAmount: String
    lateinit var newBalAmount: String
    lateinit var location_lat: String
    lateinit var location_long: String
    lateinit var deliverTime: String
    lateinit var email: String
    lateinit var phoneNumber: String

    var weighingBreakdown:MutableList<String> = mutableListOf()
    var pieceBreakdown:MutableList<String> = mutableListOf()
}