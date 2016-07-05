package com.superh.hz.monika.realtime.constant;


public enum MonikaMonitorTaskFlag {

	SimpleTask(1), CommonTask(2), DefinedTask(3);

	private int value;

	private MonikaMonitorTaskFlag(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String toString(){
		return String.valueOf(this.getValue());
	}
	
	public static void main(String[] args) {
		System.out.println(MonikaMonitorTaskFlag.SimpleTask);
	}
}
