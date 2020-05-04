package com.prasunmondal.mbros_delivery.Utils

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.widget.Toast
import java.io.File
import com.prasunmondal.mbros_delivery.sessionData.AppContext.Singleton.instance as appContext


class DownloadRateList(private val context: Context) {

	companion object {
		private const val FILE_BASE_PATH = "file://"
		private const val MIME_TYPE = "application/vnd.android.package-archive"
	}

	fun enqueueDownload(url: String, destination: String, method: () -> Unit, title: String, description: String) {

//		val destination =
//		title = context.getString(R.string.checking_for_updates)
//		description = context.getString(R.string.metadata_downloading)

		val uri = Uri.parse("$FILE_BASE_PATH$destination")

		val file = File(destination)
		if (file.exists()) file.delete()

		val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
		val downloadUri = Uri.parse(url)
		val request = DownloadManager.Request(downloadUri)
		request.setMimeType(MIME_TYPE)
		request.setTitle(title)
		request.setDescription(description)
		request.setDestinationUri(uri)
		showInstallOption(method)
		downloadManager.enqueue(request)
	}

	private fun showInstallOption(method: () -> Unit) {
		// read the update values when file is downloaded
		val onComplete = object : BroadcastReceiver() {
			override fun onReceive(context: Context, intent: Intent) {
				println("Metadata Received!")
				Toast.makeText(appContext.getCustomerSelectionActivity(), "Download Complete", Toast.LENGTH_LONG).show()
				method.invoke()
			}
		}
		context.registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
	}
}
