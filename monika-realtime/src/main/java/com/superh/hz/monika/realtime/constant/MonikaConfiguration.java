package com.superh.hz.monika.realtime.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.superh.hz.monika.realtime.PropertiesHelper;

/**
 * @Describe:自定义作业配置项
 */
public class MonikaConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(MonikaConfiguration.class);
	
	private int monitorTaskFlag;
	private String monitorTaskClass;
	private String monitorTaskParserClass;
	private static volatile MonikaConfiguration instance;
	
	private MonikaConfiguration(){
		
	}
	
	public static MonikaConfiguration buildInstance(PropertiesHelper propertiesHelper){
		
		if (instance == null) {
			synchronized (MonikaConfiguration.class) {
		       if (instance == null) {
		    	   instance = new MonikaConfiguration();
		    	   instance.setMonitorTaskFlag(propertiesHelper.getInt(
		    			   MonikaMonitorConstant.MONIKA_MONITOR_TASK_FLAG, 1));
		    	   logger.info("MONITOR_TASK_FLAG is [{}]",instance.getMonitorTaskFlag());
		    	   instance.setMonitorTaskClass(propertiesHelper.getProperty(
		    			   MonikaMonitorConstant.MONIKA_MONITOR_TASK_CLASS));
		    	   logger.info("MONIKA_MONITOR_TASK_CLASS is [{}]",instance.getMonitorTaskClass());
		    	   instance.setMonitorTaskClass(propertiesHelper.getProperty(
		    			   MonikaMonitorConstant.MONIKA_MONITOR_TASK_PARSER_CLASS));
		    	   logger.info("MONIKA_MONITOR_TASK_PARSER_CLASS is [{}]",instance.getMonitorTaskParserClass());
		       }
		    }
		}
		
		return instance;
	}
	
	public static MonikaConfiguration getInstance(){
		return instance;
	}

	public int getMonitorTaskFlag() {
		return monitorTaskFlag;
	}

	public void setMonitorTaskFlag(int monitorTaskFlag) {
		this.monitorTaskFlag = monitorTaskFlag;
	}

	public String getMonitorTaskClass() {
		return monitorTaskClass;
	}

	public void setMonitorTaskClass(String monitorTaskClass) {
		this.monitorTaskClass = monitorTaskClass;
	}

	public String getMonitorTaskParserClass() {
		return monitorTaskParserClass;
	}

	public void setMonitorTaskParserClass(String monitorTaskParserClass) {
		this.monitorTaskParserClass = monitorTaskParserClass;
	}
	
}
