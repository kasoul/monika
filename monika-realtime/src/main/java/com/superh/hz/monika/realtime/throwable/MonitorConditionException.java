package com.superh.hz.monika.realtime.throwable;

public class MonitorConditionException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MonitorConditionException(){
		super();
	}
	
	public MonitorConditionException(String message) {
		super(message);
	}
	
	public MonitorConditionException(Throwable cause) {
		super(cause);
	}
	
	public MonitorConditionException(String message,Throwable cause) {
		super(message,cause);
	}
	
	public String toString(){
		
		StringBuffer ex = new StringBuffer();

		ex.append(super.toString() + ":" + getMessage());
		StackTraceElement[] trace = getStackTrace();
        for(StackTraceElement traceElement : trace)
        	ex.append("\n " + traceElement);
		return ex.toString();
		
	}
	
	
	
}
