package com.custom.base.expand

import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import coil.load
import coil.loadAny
import com.custom.base.utils.AppUtils
import com.custom.base.utils.ResouUtils

/**
 *  作者 : dao sanqing
 *  描述 : description
 *  日期 : 2021/8/14 3:08 下午
 */

fun Int.toSp(): Int{
    return (toPx() / AppUtils.mContext.resources.displayMetrics.scaledDensity).toInt()
}

fun Float.toSp(): Float{
    return (toPx() / AppUtils.mContext.resources.displayMetrics.scaledDensity)
}

fun Int.toPx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        AppUtils.mContext.resources.displayMetrics
    ).toInt()
}

fun Float.toPx(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        AppUtils.mContext.resources.displayMetrics
    )
}

fun TextView.setDrawable(
    @DrawableRes leftRes: Int = 0,
    @DrawableRes topRes: Int = 0,
    @DrawableRes rightRes: Int = 0,
    @DrawableRes bottomRes: Int = 0
){
    val left = ResouUtils.getDrawable(leftRes)
    val top = ResouUtils.getDrawable(topRes)
    val right = ResouUtils.getDrawable(rightRes)
    val bottom = ResouUtils.getDrawable(bottomRes)

    left?.setBounds(0,0,left.minimumWidth,left.minimumHeight)
    top?.setBounds(0,0,top.minimumWidth,top.minimumHeight)
    right?.setBounds(0,0,right.minimumWidth,right.minimumHeight)
    bottom?.setBounds(0,0,bottom.minimumWidth,bottom.minimumHeight)
    setCompoundDrawables(left,top,right,bottom)
}

fun ImageView.loadUrl(data: Any?){
    loadAny(data)
}