package com.jeramtough.repeatwords2.service;

import android.app.Activity;

import com.jeramtough.jtandroid.ioc.context.IocContext;
import com.jeramtough.jtcomponent.task.response.FutureTaskResponse;
import com.jeramtough.repeatwords2.component.task.TaskCallbackInMain;

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
     */
    FutureTaskResponse initApp(IocContext iocContext, TaskCallbackInMain taskCallback);
}
