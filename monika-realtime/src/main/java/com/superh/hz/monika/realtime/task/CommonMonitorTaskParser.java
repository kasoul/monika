package com.superh.hz.monika.realtime.task;

import java.util.ArrayList;
import java.util.List;

public class CommonMonitorTaskParser extends MonitorTaskParser{

	@Override
	public MonitorTask parser2Task(String taskType, String taskId, String taskParams) {
		List<MonitorCondition> monitorParams = new ArrayList<MonitorCondition>();
		String[] params = taskParams.split(MonitorTaskParser.SIMPLE_TASK_PARAMS_SPILT);
		if(params.length<=2){
			;
		}else {
			for(int i=2;i<params.length-1;i++){
				String[] conditionItems = params[i].split(SIMPLE_TASK_PARAMS_ITEM_SPILT);
				MonitorCondition monitorCondition = new MonitorCondition(conditionItems[0],
						conditionItems[1],conditionItems[2],conditionItems[3]);
				monitorParams.add(monitorCondition);
			}
		}
		return new CommonMonitorTask(taskType,taskId,monitorParams);
	}
	
}
