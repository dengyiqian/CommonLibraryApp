package com.custom.base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;

import org.jetbrains.annotations.NotNull;

/**
 * 作者 : dao sanqing
 * 描述 : Drawable与文字剧中
 * 日期 : 2021/8/11 9:04 下午
 */
public class DrawableCenterTextView extends AppCompatCheckBox {
    public DrawableCenterTextView(@NonNull @NotNull Context context) {
        super(context);
    }

    public DrawableCenterTextView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableCenterTextView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final float buttonContentWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        float textWidth = 0f;
        final Layout layout = getLayout();
        if (layout != null) {
            for (int i = 0; i < layout.getLineCount(); i++) {
                textWidth = Math.max(textWidth, layout.getLineRight(i));
            }
        }
        // Compute left drawable width, if any
        Drawable[] drawables = getCompoundDrawables();
        Drawable drawableLeft = drawables[0];

        int drawableWidth = (drawableLeft != null) ? drawableLeft.getIntrinsicWidth() : 0;

        // We only count the drawable padding if there is both an icon and text
        int drawablePadding = ((textWidth > 0) && (drawableLeft != null)) ? getCompoundDrawablePadding() : 0;

        // Adjust contents to center
        float bodyWidth = textWidth + drawableWidth + drawablePadding;
        int translate = (int) ((buttonContentWidth - bodyWidth));
        if (translate != 0) setPadding(translate, 0, translate, 0);

        super.onDraw(canvas);
    }
}
