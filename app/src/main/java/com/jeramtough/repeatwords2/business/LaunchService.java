package com.jeramtough.repeatwords2.business;

import android.app.Activity;
import com.jeramtough.jtandroid.business.BusinessCaller;

/**
 * @author 11718
 * on 2018  May 02 Wednesday 20:06.
 */
public interface LaunchService
{
    /**
     * request needed permission of app.
     *
     * @param activity               activity instance
     * @param requestPermissionsCode activity request code
     * @return return true if have all permissions.
     */
    boolean requestNeededPermission(Activity activity, int requestPermissionsCode);
    
    /**
     * processing the data of app.
     *
     * @param businessCaller @BusinessCaller
     */
    void initApp(BusinessCaller businessCaller);
}
