package com.superh.hz.monika.realtime.task;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonMonitorTaskParser extends MonitorTaskParser{

	private static Logger logger = LoggerFactory.getLogger(CommonMonitorTaskParser.class);
	
	@Override
	public MonitorTask parser2Task(String taskType, String taskId, String taskParams) {
		
		List<MonitorCondition> monitorParams = new ArrayList<MonitorCondition>();
		String[] params = taskParams.split(MonitorTaskParser.SIMPLE_TASK_PARAMS_SPILT);
		
		try {
		for(int i=0;i<params.length;i++){
			
			String[] conditionItems = params[i].split(SIMPLE_TASK_PARAMS_ITEM_SPILT);
			MonitorCondition monitorCondition = new MonitorCondition(conditionItems[0],
					conditionItems[1],conditionItems[2],conditionItems[3]);
				monitorParams.add(monitorCondition);
		
		}
		}catch (ArrayIndexOutOfBoundsException ex) {
			
			logger.error("task param string is null or not format-task type is[{}],task id is[{}]", taskType, taskId);
			
			return new CommonMonitorTask(taskType, taskId, new ArrayList<MonitorCondition>());
		}
		
		return new CommonMonitorTask(taskType,taskId,monitorParams);
	}
	
}
