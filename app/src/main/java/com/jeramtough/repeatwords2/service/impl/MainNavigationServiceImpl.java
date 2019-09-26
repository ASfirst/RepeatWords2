package com.jeramtough.repeatwords2.service.impl;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.repeatwords2.component.app.MyAppSetting;
import com.jeramtough.repeatwords2.component.learning.mode.LearningMode;
import com.jeramtough.repeatwords2.component.learning.teacher.TeacherType;
import com.jeramtough.repeatwords2.service.MainNavigationService;

@JtServiceImpl
class MainNavigationServiceImpl implements MainNavigationService {

    private MyAppSetting myAppSetting;

    @IocAutowire
    MainNavigationServiceImpl(MyAppSetting myAppSetting) {
        this.myAppSetting = myAppSetting;
    }

    @Override
    public TeacherType getTeacherType() {
        return myAppSetting.getTeacherType();
    }

    @Override
    public LearningMode getLearningMode() {
        return LearningMode.getLearningMode(myAppSetting.getLearningMode());
    }
}
