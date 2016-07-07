package com.superh.hz.monika.realtime.task;

import com.superh.hz.monika.realtime.enumeration.MonikaMonitorTaskParamsOpeSign;
import com.superh.hz.monika.realtime.enumeration.MonikaMonitorTaskParamsValueTypeSign;
import com.superh.hz.monika.realtime.throwable.MonitorConditionException;

public class MonitorCondition {

	private String field;
	private String operation;
	private String valueType;
	private String value;

	public MonitorCondition(String field, String operation, String valueType, String value) {

		this.field = field;
		this.operation = operation;
		this.valueType = valueType;
		this.value = value;

	}

	public boolean filter(String target) throws MonitorConditionException {

		if (valueType.equals(MonikaMonitorTaskParamsValueTypeSign.STRING)) {

			if (operation.equals(MonikaMonitorTaskParamsOpeSign.EQUAL)) {
				
				return value.equals(target);
				
			} else if (operation.equals(MonikaMonitorTaskParamsOpeSign.CONTAIN)) {
				
				return target.contains(value);
				
			} else if (operation.equals(MonikaMonitorTaskParamsOpeSign.WITHIN)) {
				
				return value.contains(target);
				
			} else {

				throw new MonitorConditionException("task paramter operation is not correct.(type is \'string\')");

			}
		} else if (valueType.equals(MonikaMonitorTaskParamsValueTypeSign.INT)) {

			int valueInt = Integer.parseInt(value);
			int targetInt = Integer.parseInt(target);

			if (operation.equals(MonikaMonitorTaskParamsOpeSign.EQUAL)) {
				
				return valueInt == targetInt;
				
			} else if (operation.equals(MonikaMonitorTaskParamsOpeSign.GREATERTHAN)) {
				
				return targetInt > valueInt;
				
			} else if (operation.equals(MonikaMonitorTaskParamsOpeSign.LOWERTHAN)) {
				
				return targetInt < valueInt;
				
			} else {

				throw new MonitorConditionException("task paramter operation is not correct.(type is \'int\')");

			}
		} else if (valueType.equals(MonikaMonitorTaskParamsValueTypeSign.DATE)) {

			long valueInt = Long.parseLong(value);
			long targetInt = Long.parseLong(target);

			if (operation.equals(MonikaMonitorTaskParamsOpeSign.EQUAL)) {
				
				return valueInt == targetInt;
				
			} else if (operation.equals(MonikaMonitorTaskParamsOpeSign.GREATERTHAN)) {
				
				return targetInt > valueInt;
				
			} else if (operation.equals(MonikaMonitorTaskParamsOpeSign.LOWERTHAN)) {
				
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

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
