package com.superh.hz.monika.realtime.result;

import net.sf.json.JSONObject;

public class MonitorResult {
	
	private String monitorTaskType;
	private String monitorTaskId;
	
	private String monitorTimestamp;				// 监控时间戳
	private JSONObject jsonTuple;				
	
	public MonitorResult(String monitorTaskType, String monitorTaskId, JSONObject jsonTuple) {
		super();
		this.monitorTaskType = monitorTaskType;
		this.monitorTaskId = monitorTaskId;
		this.setJsonTuple(jsonTuple);
		
	}
	
	public MonitorResult() {
		super();
	}
	
	public String getMonitorTaskType() {
		return monitorTaskType;
	}
	public void setMonitorTaskType(String monitorTaskType) {
		this.monitorTaskType = monitorTaskType;
	}
	public String getMonitorTaskId() {
		return monitorTaskId;
	}
	public void setMonitorTaskId(String monitorTaskId) {
		this.monitorTaskId = monitorTaskId;
	}

	
	public String getMonitorTimestamp() {
		return monitorTimestamp;
	}

	public void setMonitorTimestamp(String monitorTimestamp) {
		this.monitorTimestamp = monitorTimestamp;
	}

	

	public JSONObject getJsonTuple() {
		return jsonTuple;
	}

	public void setJsonTuple(JSONObject jsonTuple) {
		this.jsonTuple = jsonTuple;
	}

	public static void main(String[] args) {
		MonitorResult mr = new MonitorResult();
		mr.setMonitorTaskType("2");
		mr.setMonitorTaskId("1");
		JSONObject resultJson = JSONObject.fromObject(mr);
		System.out.println(resultJson);
	}

}
