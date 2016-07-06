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
		/*System.out.println("调试点-任务参数个数：" + monitorParams.size() +":"+taskType+":"+taskId);*/
		for(String paramkey:monitorParams.keySet()){
			if(!(monitorParams.get(paramkey).equals(jsonTuple.getString(paramkey)))){
				/*System.out.println("调试点：" + paramkey + ":" 
			+ monitorParams.get(paramkey) + ":" +jsonTuple.getString(paramkey));*/
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean isNullParams() {
		return monitorParams==null||monitorParams.size()==0;
	}

	
}
