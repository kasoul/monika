package com.superh.hz.monika.realtime.task;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleMonitorTaskParser extends MonitorTaskParser {

	private static Logger logger = LoggerFactory.getLogger(SimpleMonitorTaskParser.class);

	public MonitorTask parser2Task(String taskType, String taskId, String taskParams) {

		Map<String, String> monitorParams = new HashMap<String, String>();
		String[] params = taskParams.split(MonitorTaskParser.SIMPLE_TASK_PARAMS_SPILT);

		logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++");
		logger.debug("任务类型：" + taskType + ";" + "任务id：" + taskId + ";" + "任务参数：" + taskParams + ";" + "任务参数个数："
				+ params.length);
		logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		try {
			for (int i = 0; i < params.length; i++) {
				monitorParams.put(params[i].split(SIMPLE_TASK_PARAMS_ITEM_SPILT)[0],
						params[i].split(SIMPLE_TASK_PARAMS_ITEM_SPILT)[1]);
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			logger.error("task param string is null or not format-task type is[{}],task id is[{}]", taskType, taskId);
			return new SimpleMonitorTask(taskType, taskId, new HashMap<String, String>());
		}

		return new SimpleMonitorTask(taskType, taskId, monitorParams);

	}

}
