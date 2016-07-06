package com.superh.hz.monika.realtime.task;

public abstract class MonitorTaskParser {
	
	public static final String SIMPLE_TASK_PARAMS_SPILT = ";";
	public static final String SIMPLE_TASK_PARAMS_ITEM_SPILT = ":";
	
	public abstract MonitorTask parser2Task(String taskType,String taskId,String taskParams);

}
