package com.custom.base.expand

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter

/**
 *  作者 : dao sanqing
 *  描述 : description
 *  日期 : 2021/8/14 3:35 下午
 */
object BindingDataAdapter {

    @JvmStatic
    @BindingAdapter("viewSelected")
    fun setViewSelected(view: View, state: Boolean) {
        view.isSelected = state
    }

    @JvmStatic
    @BindingAdapter("iconDrawableTint")
    fun setIconDrawableTint(view: TextView, @ColorInt color: Int) {
        val drawables = view.compoundDrawablesRelative
        for (index in drawables.indices) {
            val drawable = drawables[index]
            if (drawable != null) {
                drawable.setTint(color)
                return
            }
        }
    }

}