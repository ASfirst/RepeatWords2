package com.jeramtough.repeatwords2.action.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import com.jeramtough.jtandroid.controller.activity.JtIocActivity;

/**
 * @author 11718
 * on 2018  May 02 Wednesday 19:58.
 */
public class BasicActivity extends JtIocActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

}
