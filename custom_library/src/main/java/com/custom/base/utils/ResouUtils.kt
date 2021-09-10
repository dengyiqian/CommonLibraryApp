package com.custom.base.utils

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat

/**
 *  作者 : dao sanqing
 *  描述 : 资源获取工具
 *  日期 : 2021/7/23 2:10 下午
 */
object ResouUtils {

    private val resou by lazy { AppUtils.mContext.resources }

    @JvmStatic
    fun getColor(@ColorRes colorRes: Int): Int {
        return ResourcesCompat.getColor(resou, colorRes, null)
    }

    @JvmStatic
    fun getDrawable(@DrawableRes drawableRes: Int): Drawable? {
        return try {
            ResourcesCompat.getDrawable(resou,drawableRes, null)
        } catch (ex: Resources.NotFoundException){
            null
        }
    }

    @JvmStatic
    fun getString(@StringRes strRes: Int): String {
        return AppUtils.mContext.resources.getString(strRes)
    }

    @JvmStatic
    fun getArrayString(@ArrayRes strRes: Int): Array<String> {
        return AppUtils.mContext.resources.getStringArray(strRes)
    }
}