package com.jeramtough.repeatwords2.controller.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.util.IntentUtil;
import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.business.LaunchService;

/**
 * @author 11718
 * on 2018  May 02 Wednesday 20:04.
 */
public class LaunchActivity extends BasicActivity
{
    private static final int REQUEST_PERMISSIONS_CODE = 666;
    private static final int BUSINESS_CODE_INIT_APP = 1;
    
    @InjectService()
    private LaunchService launchService;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        initResources();
    }
    
    @Override
    protected void initResources()
    {
        boolean isHadAllPermissions =
                launchService.requestNeededPermission(this, REQUEST_PERMISSIONS_CODE);
        if (isHadAllPermissions)
        {
            whenGetAllNeededPermissions();
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        switch (requestCode)
        {
            case REQUEST_PERMISSIONS_CODE:
            {
                for (int grantResult : grantResults)
                {
                    if (grantResult != PackageManager.PERMISSION_GRANTED)
                    {
                        return;
                    }
                }
                whenGetAllNeededPermissions();
            }
            default:
        }
    }
    
    @Override
    public void handleActivityMessage(Message message)
    {
        switch (message.what)
        {
            case BUSINESS_CODE_INIT_APP:
                IntentUtil.toTheOtherActivity(this, MainActivity.class);
                this.finish();
                break;
        }
    }
    
    //-----------------------------------------------------
    private void whenGetAllNeededPermissions()
    {
        launchService
                .initApp(new BusinessCaller(getActivityHandler(), BUSINESS_CODE_INIT_APP));
    }
    
}
