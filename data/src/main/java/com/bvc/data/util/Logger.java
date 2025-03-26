package com.bvc.data.util;

import android.util.Log;

public class Logger 
{
//	private static boolean FORCE_LOG = CApp.test;
	private static boolean FORCE_LOG = true;
	private static String TAG_MESSAGE = "Jk";
	private static String LINE_SEPARATOR   = System.getProperty("line.separator");
	public static void loge(String message)
	{
		if(FORCE_LOG){
			while (message.length() > 0) {
				if (message.length() > 4000) {
					Log.e(TAG_MESSAGE+"["+ Thread.currentThread().getName()+"]",LINE_SEPARATOR+message.substring(0, 4000)+LINE_SEPARATOR+getStackTraceMessage(1));
					message = message.substring(4000);
				} else {
					Log.e(TAG_MESSAGE+"["+ Thread.currentThread().getName()+"]",LINE_SEPARATOR+message+LINE_SEPARATOR+getStackTraceMessage(1));
					break;
				}
			}
		}
	}
	public static void logw(String message)
	{
		if(FORCE_LOG)
			Log.w(TAG_MESSAGE+"["+ Thread.currentThread().getName()+"]",message+LINE_SEPARATOR+getStackTraceMessage(1));
	}

	public static void log(String message)
	{
		if(FORCE_LOG)
			Log.d(TAG_MESSAGE+"["+ Thread.currentThread().getName()+"]",message+LINE_SEPARATOR+getStackTraceMessage(1));
	}
	
	public static void log(String format, Object...args)
	{
		if(FORCE_LOG)
			log(String.format(format,args));
	}
	
	public static void trace()
	{
		if(FORCE_LOG)
			trace("Stack Trace Analisis");
	}
	
	public static void trace(String message){
		if(FORCE_LOG)log(new Exception(message));
	}
	
	public static void log(Throwable t)
	{
		if(!FORCE_LOG)
			return;
		
		String message = t.getClass().getName();
		
		if(t.getCause()!=null) {
			message = t.getCause().getMessage();
		}
		
		Log.d(TAG_MESSAGE+"["+ Thread.currentThread().getName()+"]",message+LINE_SEPARATOR+getStackTraceMessage(1),t);
	}
	
	public static void log(String message, Throwable t)
	{
		if(FORCE_LOG) 
			Log.d(TAG_MESSAGE+"["+ Thread.currentThread().getName()+"]",message,t);
	}
	
	public static void log(String tag, String message)
	{
		if(FORCE_LOG)
			Log.d(tag+"["+ Thread.currentThread().getName()+"]",message+LINE_SEPARATOR+getStackTraceMessage(1));
	}
	
	public static void log(String tag, String message, Throwable t)
	{
		if(FORCE_LOG)
			Log.d(tag+"["+ Thread.currentThread().getName()+"]",message+LINE_SEPARATOR+getStackTraceMessage(1),t);
	}
	

///////////////////////////////////////////////////////////////////////////////
	
	private void nothing(String tag, String message, Throwable t)
	{
		Log.d(tag+"["+ Thread.currentThread().getName()+"]",message+LINE_SEPARATOR+getStackTraceMessage(1),t);
	}
	
	public static String getStackTraceMessage(int fromTheTop)
	{
		StackTraceElement elem = getStackTraceElement(fromTheTop+1);
		StringBuffer result = new StringBuffer();
		result.append(" at ").append(elem.getClassName());
		result.append(".").append(elem.getMethodName()).append(" written in ");
		result.append(elem.getFileName()).append("[").append(elem.getLineNumber()).append(" Line]");
		return result.toString();
	}

	public static StackTraceElement getStackTraceElement(int fromTheTop)
	{
		StackTraceElement[] elem = new Throwable().getStackTrace();
		int index = fromTheTop+1;
		return elem[index];
	}
	
	public static void main(String[] args)
	{
		test();
	}
	
	public static void test()
	{
		test1();
	}
	
	public static void test1()
	{
		System.out.println("----------------");
		System.out.println(getStackTraceMessage(1));
	}
}
