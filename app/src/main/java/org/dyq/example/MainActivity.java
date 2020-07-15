package org.dyq.example;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import org.dyq.common.base.CommonActivity;
import org.dyq.common.util.APPUtils;
import org.dyq.common.util.SpannUtils;

import java.util.Objects;

public class MainActivity extends CommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.text_view);

        SpannUtils.getBuilder(textView)
                .append(Objects.requireNonNull(APPUtils.getVersionName()))
                .append("\n我是附加第一个").setTextColor(Color.RED)
                .append("\n我是附加第二个")
                .append("\n我是附加第三个").setTextColor(Color.BLUE).setBold().setTextSize(30)
                .build();
    }
}