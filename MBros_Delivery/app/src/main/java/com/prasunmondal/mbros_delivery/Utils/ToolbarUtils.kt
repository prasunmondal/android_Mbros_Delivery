package com.prasunmondal.mbros_delivery.Utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.Spannable
import android.view.Window
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class ToolbarUtils : AppCompatActivity() {

    object Singleton {
        var instance = ToolbarUtils()
    }

    fun setActionbarTextColor(apb: ActionBar, res: Resources, window: Window, statusBarColor: Int, actionBarColor: Int, textColor: Int, text: String) {
        val title: String = apb.title.toString()
        val spannableTitle: Spannable = SpannableString(text)
        spannableTitle.setSpan(
            ForegroundColorSpan(res.getColor(textColor)),
            0,
            spannableTitle.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        apb.title = text
        window.statusBarColor = res.getColor(statusBarColor)
        apb.setBackgroundDrawable(ColorDrawable(res.getColor(actionBarColor)))
    }
}