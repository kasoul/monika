package com.superh.hz.monika.realtime.task;

public abstract class MonitorTaskParser {
	
	protected static final String SIMPLE_TASK_PARAMS_SPILT = ";";
	protected static final String SIMPLE_TASK_PARAMS_ITEM_SPILT = ",";
	
	public abstract MonitorTask parser2Task(String taskType,String taskId,String taskParams);

}
