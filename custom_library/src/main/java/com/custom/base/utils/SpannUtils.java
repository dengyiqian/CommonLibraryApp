package com.custom.base.utils;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.custom.base.widget.CustomImageSpan;

public class SpannUtils {

    public static Builder getBuilder(@NonNull TextView textView) {
        return new Builder(textView);
    }

    public static class Builder {

        private int instIndex = 0;
        private int textColor = 0;
        private int textSize = 0;
        private int backgroundColor = 0;
        private int spannFlag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

        private boolean isBold = false;

        private CharSequence text;
        private TextView textView;
        private CustomImageSpan imageSpan;
        private SpannableStringBuilder spannBuilder;

        private Builder(@NonNull TextView textView) {
            this.textView = textView;
            this.text = textView.getText();
            spannBuilder = new SpannableStringBuilder();
        }

        /**
         * 设置标识
         *
         * @param spannFlag <ul>
         *             <li>{@link Spanned#SPAN_INCLUSIVE_EXCLUSIVE}</li>
         *             <li>{@link Spanned#SPAN_INCLUSIVE_INCLUSIVE}</li>
         *             <li>{@link Spanned#SPAN_EXCLUSIVE_EXCLUSIVE}</li>
         *             <li>{@link Spanned#SPAN_EXCLUSIVE_INCLUSIVE}</li>
         *             </ul>
         * @return {@link Builder}
         */
        public Builder setFlag(int spannFlag) {
            this.spannFlag = spannFlag;
            return this;
        }

        public Builder setTextColor(@ColorInt int color){
            this.textColor = color;
            return this;
        }

        public Builder setTextSize(int size){
            this.textSize = size;
            return this;
        }

        public Builder instImage(Drawable drawable,int index,int marginLeft, int marginRight){
            setSpan();
            this.instIndex = index;
            imageSpan = new CustomImageSpan(drawable,marginLeft,marginRight);
            return this;
        }

        public Builder instImage(@DrawableRes int drawable,int index,int marginLeft, int marginRight){
            setSpan();
            this.instIndex = index;
            imageSpan = new CustomImageSpan(drawable,marginLeft,marginRight);
            return this;
        }

        /**
         * 设置背景色
         * @param color 背景色
         * @return {@link Builder}
         */
        public Builder setBackgroundColor(@ColorInt int color) {
            this.backgroundColor = color;
            return this;
        }

        /**
         * 设置粗体
         * @return {@link Builder}
         */
        public Builder setBold() {
            isBold = true;
            return this;
        }

        /**
         * 追加样式字符串
         * @param text 样式字符串文本
         * @return {@link Builder}
         */
        public Builder append(@NonNull CharSequence text) {
            setSpan();
            this.text = text;
            return this;
        }

        private void setSpan() {
            if (text == null){
                return;
            }

            int start = spannBuilder.length();
            int end = spannBuilder.append(text).length();
            if (textColor != 0){
                spannBuilder.setSpan(new ForegroundColorSpan(textColor),start,end,spannFlag);
                textColor = 0;
            }
            if (backgroundColor != 0){
                spannBuilder.setSpan(new BackgroundColorSpan(backgroundColor),start,end,spannFlag);
                backgroundColor = 0;
            }
            if (textSize != 0){
                spannBuilder.setSpan(new AbsoluteSizeSpan(textSize,true),start,end,spannFlag);
                textSize = 0;
            }
            if (isBold){
                spannBuilder.setSpan(new StyleSpan(Typeface.BOLD),start,end,spannFlag);
                isBold = false;
            }
            if (imageSpan != null){
                spannBuilder.setSpan(imageSpan,instIndex, instIndex+1, ImageSpan.ALIGN_BASELINE);
                instIndex = 0;
                imageSpan = null;
            }
            spannFlag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        }

        /**
         * 创建样式字符串
         * @return 样式字符串
         */
        public void build() {
            setSpan();
            if (textView != null) {
                textView.setText(spannBuilder);
            }
        }

    }

}