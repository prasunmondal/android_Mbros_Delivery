package com.prasunmondal.mbros_delivery.utils.serializeUtils

import android.content.Context
import java.io.*

class SerializeUtil {
    /**
     * Saves a serializable object.
     *
     * @param context The application context.
     * @param objectToSave The object to save.
     * @param fileName The name of the file.
     * @param <T> The type of the object.
    </T> */
    fun saveSerializable(context: Context,
                         objectToSave: Any,
                         fileName: String?) {
        try {
            val fileOutputStream: FileOutputStream =
                context.openFileOutput(fileName, Context.MODE_PRIVATE)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.writeObject(objectToSave)
            objectOutputStream.close()
            fileOutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Loads a serializable object.
     *
     * @param context The application context.
     * @param fileName The filename.
     * @param <T> The object type.
     *
     * @return the serializable object.
    </T> */
    fun readSerializable(context: Context, fileName: String?): Any {
        var objectToReturn: Any
        try {
            val fileInputStream: FileInputStream = context.openFileInput(fileName)
            val objectInputStream = ObjectInputStream(fileInputStream)
            objectToReturn = objectInputStream.readObject()
            objectInputStream.close()
            fileInputStream.close()
            return  objectToReturn
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return "null"
    }

    fun removeSerializable(context: Context, filename: String?) {
        context.deleteFile(filename)
    }
}