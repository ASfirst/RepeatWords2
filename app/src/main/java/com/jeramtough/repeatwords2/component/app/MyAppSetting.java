package com.jeramtough.repeatwords2.component.app;

import android.content.Context;

import com.jeramtough.jtandroid.function.AppSetting;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.component.teacher.TeacherType;

/**
 * @author 11718
 * on 2018  May 03 Thursday 14:50.
 */
@JtComponent
public class MyAppSetting extends AppSetting
{
	private int learningMode = 0;
	
	@IocAutowire
	public MyAppSetting(Context context)
	{
		super(context);
	}
	
	public int getPerLearningCount()
	{
		return getSharedPreferences().getInt("perLearningCount", 10);
	}
	
	public void setPerLearningCount(int perLearningCount)
	{
		getEditor().putInt("perLearningCount", perLearningCount);
		getEditor().apply();
	}
	
	public void setLearningMode(int learningMode)
	{
		this.learningMode = learningMode;
	}
	
	public int getLearningMode()
	{
		return learningMode;
	}
	
	public TeacherType getTeacherType()
	{
		int teacherTypeId = getSharedPreferences()
				.getInt("teacherType", TeacherType.LISTENING_TEACHER.getTeacherTypeId());
		return TeacherType.getLearningMode(teacherTypeId);
	}
	
	public void setTeacherType(TeacherType teacherType)
	{
		getEditor().putInt("teacherType", teacherType.getTeacherTypeId());
		getEditor().apply();
	}
	
}
