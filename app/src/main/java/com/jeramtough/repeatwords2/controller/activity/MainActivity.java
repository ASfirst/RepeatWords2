package com.jeramtough.repeatwords2.controller.activity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.jeramtough.jtandroid.function.DblclickExit;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ui.JtViewPager;
import com.jeramtough.jtlog3.P;
import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.component.adapter.MainPagerAdapter;
import com.jeramtough.repeatwords2.controller.fragment.BaseFragment;
import com.jeramtough.repeatwords2.controller.handler.MainNavigationHandler;

/**
 * @author 11718
 */
public class MainActivity extends BasicActivity {
    @InjectComponent
    private DblclickExit dblclickExit;

    private MainNavigationHandler mainNavigationHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initResources();
    }

    @Override
    protected void initResources() {
        mainNavigationHandler = new MainNavigationHandler(this);
    }

    @Override
    public void onBackPressed() {
        dblclickExit.clickExit();
    }
}
