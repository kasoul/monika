package com.superh.hz.monika.realtime.task;

import java.util.Map;

import net.sf.json.JSONObject;

public class CommonMonitorTask implements MonitorTask {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String taskType;
	private String taskId;
	private Map<String,String> monitorParams;
	
	
	public CommonMonitorTask(String taskType,String taskId,String taskString){
		this.setTaskType(taskType);
		this.setTaskId(taskId);
		this.setMonitorParams(MonitorTaskParser.parse2SimpleTask(taskString));
	}
	
	public static void main(String[] args) {
	}

	@Override
	public boolean executeMonitor(JSONObject jsonTuple) {
		return false;
	}

	@Override
	public String getTaskId() {
		return taskId;
	}

	@Override
	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}

