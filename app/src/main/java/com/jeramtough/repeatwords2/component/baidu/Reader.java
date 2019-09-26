package com.jeramtough.repeatwords2.component.baidu;

/**
 * Created by 11718
 * on 2017  九月 17 星期日 20:43.
 */

public interface Reader
{
	void speech(String text);

	void speechOnce(String text);

	void pause();
	
	void resume();
	
	void stop();
	
	
	boolean isReading();
	
	VoiceSetting getBaiduVoiceSetting();
}
