package com.superh.hz.monika.realtime.task;

import java.io.Serializable;

import net.sf.json.JSONObject;

public interface MonitorTask extends Serializable {

	public final static String TASK_KEY_SPILT = "-";
	public final static String TASK_BEGIN_TIME_FIELD_NAME = "monikaTaskBeginTime";
	public final static String TASK_END_TIME_FILED_NAME = "monikaTaskEndTime";
	
	//返回true则为目标记录，将记录推送到结果集合
	public boolean executeMonitor(JSONObject jsonTuple);
	
	public String getTaskId();

	public String getTaskType();

	
}
