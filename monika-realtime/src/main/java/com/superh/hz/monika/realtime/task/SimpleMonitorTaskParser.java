package com.superh.hz.monika.realtime.task;

import java.util.HashMap;
import java.util.Map;

public class SimpleMonitorTaskParser extends MonitorTaskParser {
	

	public MonitorTask parser2Task(String taskType,String taskId,String taskString) {
		Map<String,String> monitorParams = new HashMap<String,String>();
		String[] params = taskString.split(MonitorTaskParser.SIMPLE_TASK_PARAMS_SPILT);
		if(params.length<=2){
			;
		}else {
			for(int i=0;i<params.length-1;i++){
				monitorParams.put(params[i].split(SIMPLE_TASK_PARAMS_ITEM_SPILT)[0], 
						params[i].split(SIMPLE_TASK_PARAMS_ITEM_SPILT)[1]);
			}
		}
		return new SimpleMonitorTask(taskType,taskId,monitorParams);
	}


}
