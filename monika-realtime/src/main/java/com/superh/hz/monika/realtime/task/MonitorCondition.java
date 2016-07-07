package com.superh.hz.monika.realtime.task;

import java.io.Serializable;

import com.superh.hz.monika.realtime.enumeration.MonikaMonitorTaskParamsOpeSign;
import com.superh.hz.monika.realtime.enumeration.MonikaMonitorTaskParamsValueTypeSign;
import com.superh.hz.monika.realtime.throwable.MonitorConditionException;

public class MonitorCondition implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private String field;
	private String operation;
	private int valueType;
	private String value;

	public MonitorCondition(String field, String operation, String valueType, String value) {

		this.field = field;
		this.operation = operation;
		this.valueType = Integer.parseInt(valueType);
		this.value = value;

	}

	public boolean filter(String target) throws MonitorConditionException {

		if (valueType == MonikaMonitorTaskParamsValueTypeSign.STRING.getValue()) {

			if (operation.equals(MonikaMonitorTaskParamsOpeSign.EQUAL.getValue())) {
				
				return value.equals(target);
				
			} else if (operation.equals(MonikaMonitorTaskParamsOpeSign.CONTAIN.getValue())) {
				
				return target.contains(value);
				
			} else if (operation.equals(MonikaMonitorTaskParamsOpeSign.WITHIN.getValue())) {
				
				return value.contains(target);
				
			} else {

				throw new MonitorConditionException("task paramter operation is not correct.(type is \'string\')");

			}
		} else if (valueType == MonikaMonitorTaskParamsValueTypeSign.INT.getValue()) {

			int valueInt = Integer.parseInt(value);
			int targetInt = Integer.parseInt(target);

			if (operation.equals(MonikaMonitorTaskParamsOpeSign.EQUAL.getValue())) {
				
				return valueInt == targetInt;
				
			} else if (operation.equals(MonikaMonitorTaskParamsOpeSign.GREATERTHAN.getValue())) {
				
				return targetInt > valueInt;
				
			} else if (operation.equals(MonikaMonitorTaskParamsOpeSign.LOWERTHAN.getValue())) {
				
				return targetInt < valueInt;
				
			} else {

				throw new MonitorConditionException("task paramter operation is not correct.(type is \'int\')");

			}
		} else if (valueType == MonikaMonitorTaskParamsValueTypeSign.DATE.getValue()) {

			long valueInt = Long.parseLong(value);
			long targetInt = Long.parseLong(target);

			if (operation.equals(MonikaMonitorTaskParamsOpeSign.EQUAL.getValue())) {
				
				return valueInt == targetInt;
				
			} else if (operation.equals(MonikaMonitorTaskParamsOpeSign.GREATERTHAN.getValue())) {
				
				return targetInt > valueInt;
				
			} else if (operation.equals(MonikaMonitorTaskParamsOpeSign.LOWERTHAN.getValue())) {
				
				return targetInt < valueInt;
				
			} else {

				throw new MonitorConditionException("task paramter operation is not correct.(type is \'date\')");

			}
		} else {

			throw new MonitorConditionException("task paramter value type is not correct.");

		}
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public int getValueType() {
		return valueType;
	}

	public void setValueType(int valueType) {
		this.valueType = valueType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
