package com.prasunmondal.mbros_delivery.appData

import com.prasunmondal.mbros_delivery.blueprints.Customer
import com.prasunmondal.mbros_delivery.sessionData.AppContext.Singleton.instance as appContext
import com.prasunmondal.mbros_delivery.utils.serializeUtils.SerializeUtil

class CustomerManager {

    object Singleton {
        var instance = CustomerManager()
    }

    var customerMap: MutableMap<String, Customer> = mutableMapOf()
    lateinit var current: Customer

    fun save() {
        SerializeUtil().saveSerializable(appContext.initialContext, customerMap, "customerMap")
    }

    fun read() {
        customerMap = SerializeUtil().readSerializable(appContext.initialContext, "customerMap") as MutableMap<String, Customer>
    }

    fun find(name: String): Boolean {
        return customerMap.contains(name)
    }

    fun createCustomerIfAbsent(name: String) {
        var newCustomer = Customer()
        newCustomer.name = name
        newCustomer.paidAmount = ""
        newCustomer.unitPrice = "0"
        customerMap.putIfAbsent(name, newCustomer)
    }

    fun getCustomer(customerName: String): Customer {
        return customerMap.get(customerName)!!
    }

    fun resetData() {
        customerMap = mutableMapOf()
        customerMap.clear()
        save()
    }
}