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
        window?.let { win->
            win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val params = win.attributes
            params.width = (context.resources.displayMetrics.widthPixels * 0.8).toInt()
            win.attributes = params
        }
    }

}