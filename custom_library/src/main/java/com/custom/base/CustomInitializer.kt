package com.custom.base

import android.content.Context
import androidx.startup.Initializer
import com.custom.base.utils.AppUtils
import com.tencent.mmkv.MMKV

/**
 *  作者 : dao sanqing
 *  描述 : description
 *  日期 : 2021/7/23 2:25 下午
 */
class CustomInitializer : Initializer<Unit>{
    override fun create(context: Context) {
        MMKV.initialize(context)
        AppUtils.mContext = context.applicationContext
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}