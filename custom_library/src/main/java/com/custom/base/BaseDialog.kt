package com.custom.base

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.annotation.StyleRes

/**
 * 作者 :
 * 描述 : description
 * 日期 : 2021/8/18 11:53 上午
 */
class BaseDialog constructor(context: Context): Dialog(context,R.style.CustomDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
    }

    override fun onStart() {
        super.onStart()
        window?.windowManager?.let {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val params = window!!.attributes
            params!!.width = (it.defaultDisplay.width * 0.8).toInt()
            window!!.attributes = params
        }
    }

}