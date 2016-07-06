package com.superh.hz.monika.realtime.task;

import java.util.List;

import net.sf.json.JSONObject;

public class CommonMonitorTask implements MonitorTask {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String taskType;
	private String taskId;
	private List<MonitorCondition> monitorParams;
	
	
	public CommonMonitorTask(String taskType,String taskId,List<MonitorCondition> monitorParams){
		this.setTaskType(taskType);
		this.setTaskId(taskId);
		this.setMonitorParams(monitorParams);
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

	public List<MonitorCondition> getMonitorParams() {
		return monitorParams;
	}

	public void setMonitorParams(List<MonitorCondition> monitorParams) {
		this.monitorParams = monitorParams;
	}
}

