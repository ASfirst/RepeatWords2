package com.jeramtough.repeatwords2.component.baidu;

import android.content.Context;
import com.jeramtough.jtandroid.function.AppSetting;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

/**
 * @author 11718
 * on 2018  May 06 Sunday 15:06.
 */
@JtComponent
public class BaiduVoiceSetting extends AppSetting implements VoiceSetting {
    private boolean isRepeated = false;

    @IocAutowire
    public BaiduVoiceSetting(Context context) {
        super(context);
    }

    @Override
    public int getReadEnglishSpeed() {
        return getSharedPreferences().getInt("readEnglishSpeed", 5);
    }

    @Override
    public void setReadEnglishSpeed(int readEnglishSpeed) {
        getEditor().putInt("readEnglishSpeed", readEnglishSpeed);
        getEditor().apply();
    }

    @Override
    public int getReadChineseSpeed() {
        return getSharedPreferences().getInt("readChineseSpeed", 5);
    }

    @Override
    public void setReadChineseSpeed(int readChineseSpeed) {
        getEditor().putInt("readChineseSpeed", readChineseSpeed);
        getEditor().apply();
    }

    @Override
    public long getRepeatIntervalTime() {
        return getSharedPreferences().getLong("repeatIntervalTime", 0);
    }

    @Override
    public void setRepeatIntervalTime(long repeatIntervalTime) {
        getEditor().putLong("repeatIntervalTime", repeatIntervalTime);
        getEditor().apply();
    }

    @Override
    public boolean isRepeated() {
        return isRepeated;
    }

    @Override
    public void setRepeated(boolean isRepeated) {
        this.isRepeated = isRepeated;
    }
}
