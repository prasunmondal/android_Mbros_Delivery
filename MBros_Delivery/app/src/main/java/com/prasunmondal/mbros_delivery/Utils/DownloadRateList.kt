package com.prasunmondal.mbros_delivery.Utils

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.view.View
import android.widget.Toast
import com.prasunmondal.mbros_delivery.Utility.showSnackbar
import com.google.android.material.snackbar.Snackbar
import com.prasunmondal.mbros_delivery.BuildConfig
import com.prasunmondal.mbros_delivery.R
import com.prasunmondal.mbros_delivery.sessionData.AppContext.Singleton.instance as appContext
import java.io.File
import com.prasunmondal.mbros_delivery.appData.FileManagerUtil.Singleton.instance as fileManagers
import com.prasunmondal.mbros_delivery.sessionData.FetchedRateList.Singleton.instance as fetchedRateList


class DownloadRateList(private val context: Context, private val url: String) {

	companion object {
		private const val FILE_BASE_PATH = "file://"
		private const val MIME_TYPE = "application/vnd.android.package-archive"
	}

	fun enqueueDownload(view: View, isRefresh: Boolean) {

//		println("Here....... " + fileManagers.downloadLink_Metadata.destination)
		val destination = fileManagers.storageLink_RateList.destination

		val uri = Uri.parse("$FILE_BASE_PATH$destination")

		val file = File(destination)
		if (file.exists()) file.delete()

		val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
		val downloadUri = Uri.parse(url)
		val request = DownloadManager.Request(downloadUri)
		request.setMimeType(MIME_TYPE)
		request.setTitle(context.getString(R.string.checking_for_updates))
		request.setDescription(context.getString(R.string.metadata_downloading))

		// set destination
		request.setDestinationUri(uri)

		showInstallOption(view, isRefresh)
		// Enqueue a new download and same the referenceId
		downloadManager.enqueue(request)
	}

	private fun showInstallOption(view: View, isRefresh: Boolean) {
		// read the update values when file is downloaded
		val onComplete = object : BroadcastReceiver() {
			override fun onReceive(context: Context, intent: Intent) {
				println("Metadata Received!")
				Toast.makeText(appContext.getCustomerSelectionActivity(), "Download Complete", Toast.LENGTH_LONG).show()
				promptAndInitiateUpdate(view)
//				fetchedRateList.updateButtonData(isRefresh)
			}
		}
		context.registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
	}

	private fun promptAndInitiateUpdate(view: View)
	{
		var availableVers = fetchedRateList.getValue(fetchedRateList.APP_DOWNLOAD_VERSION)
		val currentVers = BuildConfig.VERSION_CODE
		println("current value: $currentVers")
		if(availableVers == null) {
			availableVers = currentVers.toString()
		}
		if (availableVers.toInt() > currentVers) {
			view.showSnackbar(
				R.string.updateAvailable,
				Snackbar.LENGTH_INDEFINITE, R.string.update
			) {
				downloadAndUpdate()
			}
		}
	}

	fun downloadAndUpdate() {
		println("Im inside")
//		val apkUrl = fetchedRateList.getValue(fetchedRateList.APP_DOWNLOAD_LINK)
//		DownloadUpdate(context, apkUrl).enqueueDownload(view)
	}
}
