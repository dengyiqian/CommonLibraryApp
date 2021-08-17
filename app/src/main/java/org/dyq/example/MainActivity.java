package org.dyq.example;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.custom.base.BaseViewMolde;
import com.custom.base.ToolbarActivity;
import com.custom.base.utils.AppUtils;
import com.custom.base.utils.SpannUtils;

import org.dyq.example.databinding.ActivityMainBinding;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class MainActivity extends ToolbarActivity<ActivityMainBinding, BaseViewMolde> {

    public MainActivity() {
        super(BaseViewMolde.class);
    }

    @Nullable
    @Override
    protected CharSequence settingTitle() {
        return "测试";
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        SpannUtils.getBuilder(mBinding.textView)
                .append(Objects.requireNonNull(AppUtils.getVersionName()))
                .append("\n我是附加第一个").setTextColor(Color.RED)
                .append("\n我是附加第二个")
                .append("\n我是附加第三个").setTextColor(Color.BLUE).setBold().setTextSize(30)
                .build();
    }
}