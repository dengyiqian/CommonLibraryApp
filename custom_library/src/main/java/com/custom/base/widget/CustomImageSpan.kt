package com.custom.base.widget

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan
import androidx.annotation.DrawableRes
import com.custom.base.utils.AppUtils

/**
 *  作者 : dao sanqing
 *  描述 : description
 *  日期 : 2021/8/24 10:07 上午
 */
class CustomImageSpan : ImageSpan {

    private var mMarginLeft = 0
    private var mMarginRight = 0

    constructor(drawable: Drawable,marginLeft: Int = 0, marginRight: Int = 0): super(drawable){
        this.mMarginLeft = marginLeft
        this.mMarginRight = marginRight
    }

    constructor(@DrawableRes drawableRes: Int, marginLeft: Int = 0, marginRight: Int = 0):super(AppUtils.mContext,drawableRes){
        this.mMarginLeft = marginLeft
        this.mMarginRight = marginRight
    }

    override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val dra = drawable
        val fm = paint.fontMetricsInt
        val axisX = mMarginLeft + x
        val transY = ((y + fm.descent + y + fm.ascent) / 2 - dra.bounds.bottom / 2).toFloat()
        canvas.save()
        canvas.translate(axisX,transY)
        dra.draw(canvas)
        canvas.restore()
    }

    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        return mMarginLeft + super.getSize(paint, text, start, end, fm) + mMarginRight
    }

}