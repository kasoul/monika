package com.superh.hz.monika.realtime.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.superh.hz.monika.realtime.constant.MonikaConfiguration;
import com.superh.hz.monika.realtime.enumeration.MonikaMonitorTaskSign;

import net.sf.json.JSONObject;

public class MonitorTaskBuilder {
	
	private static MonitorTaskParser monitorTaskParser;
	
	public static void init(){
		
		if(MonikaConfiguration.getInstance().getMonitorTaskFlag()==
				MonikaMonitorTaskSign.SIMPLE_TASK.getValue()){
			
			monitorTaskParser = new SimpleMonitorTaskParser();
			
		}else if(MonikaConfiguration.getInstance().getMonitorTaskFlag()==
				MonikaMonitorTaskSign.COMMON_TASK.getValue()){
			
			monitorTaskParser = new CommonMonitorTaskParser();
			
		}else{
			Class<?> c;
			try {
				
				c = Class.forName(MonikaConfiguration.getInstance().getMonitorTaskParserClass());
				monitorTaskParser = (MonitorTaskParser)c.newInstance();
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static List<MonitorTask> buildTaskList(Map<String,String> taskInfoMap){
		
		long nowTime = System.currentTimeMillis();
		List<MonitorTask> list = new ArrayList<MonitorTask>();
		
		for(String taskInfoKey : taskInfoMap.keySet()){
			
			String taskType = taskInfoKey.split(MonitorTask.TASK_KEY_SPILT)[0];
			String taskId = taskInfoKey.split(MonitorTask.TASK_KEY_SPILT)[1];
			JSONObject jsonTask = JSONObject.fromObject(taskInfoMap.get(taskInfoKey));
			
			long startTime = jsonTask.getLong(MonitorTask.TASK_BEGIN_TIME_FIELD_NAME);
			long endTime = jsonTask.getLong(MonitorTask.TASK_END_TIME_FILED_NAME);
			String taskParams = jsonTask.getString(MonitorTask.TASK_PARAMS_FILED_NAME); 
			
			if(nowTime < startTime || nowTime > endTime){
				
				//System.out.println(Long.parseLong(beginTime));
				//System.out.println(Long.parseLong(endTime));
				continue;
				
			}else{
				
				MonitorTask task = buildTask(taskType,taskId,taskParams);
				if(!task.isNullParams()){
					
					list.add(buildTask(taskType,taskId,taskParams));
					
				}
				
			}
			
		}
		
		return list;
	}
	
	private static MonitorTask buildTask(String taskType,String taskId, String taskParams ){
		
		return monitorTaskParser.parser2Task(taskType,taskId,taskParams);
	
	}
	
	
	
}
