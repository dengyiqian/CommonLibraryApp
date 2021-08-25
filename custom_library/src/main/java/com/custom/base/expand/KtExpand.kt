package com.custom.base.expand

import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import coil.load
import coil.loadAny
import com.custom.base.CustomApp
import com.custom.base.utils.AppUtils
import com.custom.base.utils.ResouUtils
import com.custom.base.viewmodel.BaseViewMolde
import java.lang.reflect.ParameterizedType

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
    @DrawableRes bottomRes: Int = 0,
    size: Int = 0
){
    val left = ResouUtils.getDrawable(leftRes)
    val top = ResouUtils.getDrawable(topRes)
    val right = ResouUtils.getDrawable(rightRes)
    val bottom = ResouUtils.getDrawable(bottomRes)

    if (size > 0){
        left?.setBounds(0,0,size,size)
        top?.setBounds(0,0,size,size)
        right?.setBounds(0,0,size,size)
        bottom?.setBounds(0,0,size,size)
    } else {
        left?.setBounds(0,0,left.minimumWidth,left.minimumHeight)
        top?.setBounds(0,0,top.minimumWidth,top.minimumHeight)
        right?.setBounds(0,0,right.minimumWidth,right.minimumHeight)
        bottom?.setBounds(0,0,bottom.minimumWidth,bottom.minimumHeight)
    }

    setCompoundDrawables(left,top,right,bottom)
}

fun ImageView.loadUrl(data: Any?){
    loadAny(data)
}

fun <T> getClazz(obj: Any, index:Int = 0): T {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as T
}

inline fun <reified VM : BaseViewMolde> AppCompatActivity.initViewModel(): VM{
    (this.application as? CustomApp).let {
        if (it == null) {
            throw NullPointerException("你的Application没有继承框CustomApp类，暂时无法使用getViewModelProvider该方法")
        } else {
            return it.getViewModelProvider().get(VM::class.java)
        }
    }
}

inline fun <reified VM : BaseViewMolde> Fragment.initViewModel(): VM{
    (requireActivity().application as? CustomApp).let {
        if (it == null) {
            throw NullPointerException("你的Application没有继承框CustomApp类，暂时无法使用getViewModelProvider该方法")
        } else {
            return it.getViewModelProvider().get(VM::class.java)
        }
    }
}