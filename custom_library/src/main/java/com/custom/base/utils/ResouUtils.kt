package com.custom.base.utils

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat

/**
 *  作者 : dao sanqing
 *  描述 : 资源获取工具
 *  日期 : 2021/7/23 2:10 下午
 */
object ResouUtils {

    private val resou by lazy { AppUtils.mContext.resources }

    fun getColor(@ColorRes colorRes: Int): Int {
        return ResourcesCompat.getColor(resou, colorRes, null)
    }

    fun getDrawable(@DrawableRes drawableRes: Int): Drawable? {
        return try {
            ResourcesCompat.getDrawable(resou,drawableRes, null)
        } catch (ex: Resources.NotFoundException){
            null
        }
    }

}