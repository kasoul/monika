package com.superh.hz.monika.realtime.task;

import java.util.HashMap;
import java.util.Map;

public class MonitorTaskParser {
	
	private static final String SIMPLE_TASK_PARAMS_SPILT = ";";
	private static final String SIMPLE_TASK_PARAMS_ITEM_SPILT = ",";
	
	public static Map<String,String> parse2SimpleTask(String taskString) {
		Map<String,String> monitorParams = new HashMap<String,String>();
		String[] params = taskString.split(SIMPLE_TASK_PARAMS_SPILT);
		if(params.length<=2){
			return monitorParams;
		}else {
			for(int i=0;i<params.length-1;i++){
				monitorParams.put(params[i].split(SIMPLE_TASK_PARAMS_ITEM_SPILT)[0], 
						params[i].split(SIMPLE_TASK_PARAMS_ITEM_SPILT)[1]);
			}
		}
		return monitorParams;
	}
	
	public static Map<String,String> parse2CommonTask(String taskString) {
		Map<String,String> monitorParams = new HashMap<String,String>();
		
		return monitorParams;
	}
	
	
}
