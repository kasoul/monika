package com.superh.hz.monika.realtime.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class MonitorTaskBuilder {
	
	public static List<MonitorTask> buildTaskList(Map<String,String> taskInfoMap){
		
		long nowTime = System.currentTimeMillis();
		List<MonitorTask> list = new ArrayList<MonitorTask>();
		for(String taskInfoKey : taskInfoMap.keySet()){
			String taskType = taskInfoKey.split(MonitorTask.TASK_KEY_SPILT)[0];
			String taskId = taskInfoKey.split(MonitorTask.TASK_KEY_SPILT)[1];
			JSONObject jsonTask = JSONObject.fromObject(taskInfoMap.get(taskInfoKey));
			
			long startTime = jsonTask.getLong(MonitorTask.TASK_BEGIN_TIME_FIELD_NAME);
			long endTime = jsonTask.getLong(MonitorTask.TASK_END_TIME_FILED_NAME);
			if(nowTime < startTime || nowTime > endTime){
				//System.out.println(Long.parseLong(beginTime));
				//System.out.println(Long.parseLong(endTime));
				continue;
			}else{
				list.add(buildTask(taskType,taskId,taskInfoMap.get(taskInfoKey)));
			}
		}
		return list;
	}
	
	private static MonitorTask buildTask(String taskType,String taskId, String taskJson ){
		
		if(taskType.equals(String.valueOf(VehicleMonitorTypeEnum.VehicelIntelligent))){
			return new IntellgientMonitorTask(taskId,taskJson);
		}else{
			return new SimpleMonitorTask(taskType,taskId,taskJson);
		}
	}
	
	
	
}
