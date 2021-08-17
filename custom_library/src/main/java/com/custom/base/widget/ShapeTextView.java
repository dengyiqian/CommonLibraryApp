package com.custom.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;

import com.custom.base.R;


public class ShapeTextView extends AppCompatTextView {

    private int defaultColor = 0x20000000;
    private int defaultSelectorColor = 0x20000000;

    private int solidColor;

    private int strokeColor;
    private int strokeWidth;

    private float strokeDashWidth;
    private float strokeDashGap;

    /**按下颜色*/
    private int selectorPressedColor;
    /**禁用颜色*/
    private int selectorDisableColor;
    /**正常颜色*/
    private int selectorNormalColor;

    /**四角 角度*/
    private float cornersRadius;
    /**左上 角度*/
    private float cornersTopLeftRadius;
    /**右上 角度*/
    private float cornersTopRightRadius;
    /**左下 角度*/
    private float cornersBottomLeftRadius;
    /**右下 角度*/
    private float cornersBottomRightRadius;

    public ShapeTextView(Context context) {
        this(context, null);
    }

    public ShapeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeTextView);
        solidColor = typedArray.getColor(R.styleable.ShapeTextView_solid_color, defaultColor);
        selectorPressedColor = typedArray.getColor(R.styleable.ShapeTextView_pressed_color, defaultSelectorColor);
        selectorDisableColor = typedArray.getColor(R.styleable.ShapeTextView_disable_color, defaultSelectorColor);
        selectorNormalColor = typedArray.getColor(R.styleable.ShapeTextView_normal_color, defaultSelectorColor);

        cornersRadius = typedArray.getDimensionPixelSize(R.styleable.ShapeTextView_corners_radius, 0);
        cornersTopLeftRadius = typedArray.getDimensionPixelSize(R.styleable.ShapeTextView_top_left_radius, 0);
        cornersTopRightRadius = typedArray.getDimensionPixelSize(R.styleable.ShapeTextView_top_right_radius, 0);
        cornersBottomLeftRadius = typedArray.getDimensionPixelSize(R.styleable.ShapeTextView_bottom_left_radius, 0);
        cornersBottomRightRadius = typedArray.getDimensionPixelSize(R.styleable.ShapeTextView_bottom_right_radius, 0);

        strokeColor = typedArray.getColor(R.styleable.ShapeTextView_stroke_color, defaultColor);
        strokeWidth = typedArray.getDimensionPixelSize(R.styleable.ShapeTextView_stroke_width, 0);
        strokeDashGap = typedArray.getDimensionPixelSize(R.styleable.ShapeTextView_stroke_dash_gap, 0);
        strokeDashWidth = typedArray.getDimensionPixelSize(R.styleable.ShapeTextView_stroke_dash_width, 0);
        typedArray.recycle();

        initShape();
    }

    private void initShape(){
        if (selectorPressedColor != defaultSelectorColor){
            setClickable(true);
        }
        if (strokeDashWidth > 0 && strokeDashGap == 0){
            strokeDashGap = dip2px(1f);
        } else if (strokeDashWidth == 0 && strokeDashGap > 0){
            strokeDashWidth = dip2px(1f);
        }

        ViewCompat.setBackground(this, getListDrawable());
    }

    private StateListDrawable getListDrawable(){
        StateListDrawable listDrawable = new StateListDrawable();
        listDrawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, getDrawable(android.R.attr.state_pressed));
        listDrawable.addState(new int[]{-android.R.attr.state_enabled}, getDrawable(-android.R.attr.state_enabled));
        listDrawable.addState(new int[]{},getDrawable(android.R.attr.state_enabled));
        return listDrawable;
    }

    private Drawable getDrawable(int state){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(solidColor);
        if (strokeWidth > 0){
            drawable.setStroke(strokeWidth,strokeColor,strokeDashWidth, strokeDashGap);
        }
        if (cornersRadius > 0f){
            drawable.setCornerRadius(cornersRadius);
        } else if (cornersTopLeftRadius > 0f || cornersTopRightRadius > 0f || cornersBottomRightRadius >0f || cornersBottomLeftRadius > 0f){
            drawable.setCornerRadii(new float[]{
                    cornersTopLeftRadius,cornersTopLeftRadius,
                    cornersTopRightRadius,cornersTopRightRadius,
                    cornersBottomLeftRadius,cornersBottomLeftRadius,
                    cornersBottomRightRadius,cornersBottomRightRadius
            });
        }
        switch (state){
            case android.R.attr.state_pressed:
                drawable.setColor(selectorPressedColor);
                break;
            case -android.R.attr.state_enabled:
                drawable.setColor(selectorDisableColor);
                break;
            case android.R.attr.state_enabled:
                drawable.setColor(selectorNormalColor);
                break;
        }
        return drawable;
    }

    private int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
