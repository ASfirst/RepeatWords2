package com.jeramtough.repeatwords2.component.baidu;

/**
 * @author 11718
 * on 2018  May 06 Sunday 15:57.
 */
public interface VoiceSetting
{
	int getReadEnglishSpeed();
	
	void setReadEnglishSpeed(int readEnglishSpeed);
	
	int getReadChineseSpeed();
	
	void setReadChineseSpeed(int readChineseSpeed);
	
	long getRepeatIntervalTime();
	
	void setRepeatIntervalTime(long repeatIntervalTime);
	
	boolean isRepeated();
	
	void setRepeated(boolean isRepeated);
}
