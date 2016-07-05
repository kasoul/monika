package com.superh.hz.monika.realtime.task;

import java.util.Map;

import net.sf.json.JSONObject;

public class SimpleMonitorTask implements MonitorTask {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String taskType;
	private String taskId;
	private Map<String,String> monitorParams;
	
	
	public SimpleMonitorTask(String taskType,String taskId,Map<String,String> monitorParams){
		this.setTaskType(taskType);
		this.setTaskId(taskId);
		this.setMonitorParams(monitorParams);
	}

	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	public String getTaskType() {
		return taskType;
	}


	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}


	public Map<String,String> getMonitorParams() {
		return monitorParams;
	}


	public void setMonitorParams(Map<String,String> monitorParams) {
		this.monitorParams = monitorParams;
	}


	@Override
	public boolean executeMonitor(JSONObject jsonTuple) {
		
		return false;
	}

	
}
