package com.jeramtough.repeatwords2.component.learningmode;

import java.util.List;

/**
 * @author 11718
 * on 2018  May 07 Monday 14:57.
 */
public enum LearningMode
{
	NEW(0), REVIME(1), MARKED(2);
	
	private int learningModeId;
	
	LearningMode(int learningModeId)
	{
		this.learningModeId = learningModeId;
	}
	
	public int getLearningModeId()
	{
		return learningModeId;
	}
	
	public static LearningMode getLearningMode(int learningModeId)
	{
		switch (learningModeId)
		{
			case 0:
				return NEW;
			case 1:
				return REVIME;
			case 2:
				return MARKED;
		}
		return NEW;
	}
}
