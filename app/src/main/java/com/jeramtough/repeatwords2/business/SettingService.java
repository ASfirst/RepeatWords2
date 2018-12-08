package com.jeramtough.repeatwords2.business;

import com.jeramtough.jtandroid.business.BusinessCaller;

/**
 * @author 11718
 * on 2018  May 05 Saturday 20:35.
 */
public interface SettingService
{
	void setPerLearningCount(int count);
	
	int getPerLearningCount();
	
	void setReadEnglishSpeed(int degree);
	
	int getReadEnglishSpeed();
	
	void setRepeatIntervalTime(long time);
	
	long getRepeatIntervalTime();
	
	void backupTheLearningRecord(BusinessCaller businessCaller);
	
	void recoverTheLearningRecord(BusinessCaller businessCaller);
	
	int getTeacherType();
	
	int getLearningMode();
	
	void setTeacherType(int teacherTypeId);
	
	void setLearningMode(int learningMode);

	void clearHavedLearnedWordToday(BusinessCaller businessCaller);
}
