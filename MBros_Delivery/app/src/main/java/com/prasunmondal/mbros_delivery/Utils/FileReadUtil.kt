package com.prasunmondal.mbros_delivery.Utils

import com.prasunmondal.mbros_delivery.appData.FilePaths
import com.opencsv.CSVReader
import java.io.File
import java.io.FileReader
import java.io.IOException

class FileReadUtil {

    object Singleton {
        var instance = FileReadUtil()
    }

    fun readPairCSVnPopulateMap(map: MutableMap<String, String>, fileName: FilePaths) {
        try {
            val reader = CSVReader(FileReader(File(fileName.destination)))
            var nextLine: Array<String>
            while (reader.peek() != null) {
                nextLine = reader.readNext()
                map[nextLine[0]] = nextLine[1]
            }
            println(map)
        } catch (e: IOException) {
            println(e)
            throw (e)
        }
    }

    fun getValue_forKey(fileName: FilePaths, keyColumnIndex: Int, key: String, valueColumnIndex: Int): String? {
        try {
            val reader = CSVReader(FileReader(File(fileName.destination)))
            var nextLine: Array<String>
            while (reader.peek() != null) {
                nextLine = reader.readNext()
                if(nextLine[keyColumnIndex] == key) {
                    return nextLine[valueColumnIndex]
                }
            }
        } catch (e: IOException) {
            println(e)
            throw (e)
        }
//        "when asked value doesn't exist.. need to update the values")
        return null
    }

    fun getListOfValues(fileName: FilePaths, keyColumnIndex: Int): MutableList<String>? {
        var list : MutableList<String> = mutableListOf<String>()
        try {
            val reader = CSVReader(FileReader(File(fileName.destination)))
            var nextLine: Array<String>
            while (reader.peek() != null) {
                nextLine = reader.readNext()
                if(nextLine[keyColumnIndex].length > 0)
                    list.add(nextLine[keyColumnIndex])
            }
        } catch (e: IOException) {
            println(e)
            throw (e)
        }
//        "when asked value list is empty.. need to update the values"
        return list
    }
}