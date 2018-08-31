package com.jeramtough.repeatwords2;

import junit.framework.Test;

/**
 * @author 11718
 * on 2018  May 07 Monday 16:18.
 */
public class StrategyTest
{
	public class Logger
	{
		private void printInfoText(String text) {}
		
		private void printErrorText(String text) {}
		
		private void printWarnText(String text) { }
	}
	
	@org.junit.Test
	public void main()
	{
		Logger logger=new Logger();
		if (1 == 0)
		{
			logger.printInfoText("adsfsdaf");
		}
		else
		{
			logger.printWarnText("dsafsafa");
		}
	}
	
	public interface Print
	{
		void print(String text);
	}
	
	public class InfoPrint implements Print
	{
		@Override
		public void print(String text)
		{
			System.out.println("INFO:" + text);
		}
	}
	
	public class ErrorPrint implements Print
	{
		@Override
		public void print(String text)
		{
			System.err.println("ERROR:" + text);
		}
	}
	
	//执行者对象
	public class Logger1
	{
		private Print print;
		
		public void printLog(String text)
		{
			print.print(text);
		}
		
		public Print getPrint()
		{
			return print;
		}
		
		public void setPrint(Print print)
		{
			this.print = print;
		}
	}
	
	
	@org.junit.Test
	public void main1()
	{
		Logger1 logger1 = new Logger1();
		
		if (1 == 0)
		{
			logger1.setPrint(new InfoPrint());
		}
		else
		{
			logger1.setPrint(new ErrorPrint());
		}
		
		logger1.printLog("dsafsdfasdf");
	}
}
