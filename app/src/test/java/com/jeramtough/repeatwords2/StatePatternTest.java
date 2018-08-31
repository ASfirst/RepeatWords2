package com.jeramtough.repeatwords2;

import org.junit.Test;

/**
 * @author 11718
 * on 2018  May 07 Monday 15:48.
 */

public class StatePatternTest
{
	@Test
	public void main()
	{
		Person person=new Person();
		if (1==1)
		{
			person.setCurrentState(Person.STATE1);
		}
		else if (2==3)
		{
			person.setCurrentState(Person.STATE1);
		}
		else
		{
			person.setCurrentState(Person.STATE3);
		}
		person.eat();
	}
	
	public class Person
	{
		private static final int STATE1 = 1;
		private static final int STATE2 = 2;
		private static final int STATE3 = 3;
		
		private int currentState = 0;
		
		private void eat()
		{
			switch (currentState)
			{
				case STATE1:
					break;
				
				case STATE2:
					break;
				
				case STATE3:
					break;
			}
		}
		
		private void run()
		{
			switch (currentState)
			{
				case STATE1:
					break;
				
				case STATE2:
					break;
				
				case STATE3:
					break;
			}
		}
		
		private void sleep()
		{
			switch (currentState)
			{
				case STATE1:
					break;
				
				case STATE2:
					break;
				
				case STATE3:
					break;
			}
		}
		
		public int getCurrentState()
		{
			return currentState;
		}
		
		public void setCurrentState(int currentState)
		{
			this.currentState = currentState;
		}
	}
	
	public interface State
	{
		void run();
		
		void eat();
		
		void sleep();
	}
	
	public class State1 implements State
	{
		@Override
		public void run(){}
		
		@Override
		public void eat(){}
		
		@Override
		public void sleep(){}
	}
	
	public class State2 implements State
	{
		@Override
		public void run(){}
		
		@Override
		public void eat(){}
		
		@Override
		public void sleep(){}
	}
	
	//每个状态下的方法,执行者角色也有
	public class Person1
	{
		private State currentState;
		
		private void eat()
		{
			currentState.eat();
		}
		
		public void run()
		{
			currentState.run();
		}
		
		public void sleep()
		{
			currentState.sleep();
		}
		
		public State getCurrentState()
		{
			return currentState;
		}
		
		public void setCurrentState(State currentState)
		{
			this.currentState = currentState;
		}
	}
	
	@Test
	public void main1()
	{
		Person1 person1=new Person1();
		if (1==1)
		{
			person1.setCurrentState(new State1());
		}
		else if (2==3)
		{
			person1.setCurrentState(new State2());
		}
		else
		{
			person1.setCurrentState(new State1());
		}
		person1.eat();
	}
}
