package com.superh.hz.monika.realtime.enumeration;


public enum MonikaMonitorTaskParamsOpeSign {

	EQUAL("eq"), GREATERTHAN("gt"), LOWERTHAN("lt"), CONTAIN("cont"),WITHIN("in");

	private String value;

	private MonikaMonitorTaskParamsOpeSign(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String toString(){
		return String.valueOf(this.getValue());
	}
	
	public static void main(String[] args) {
		System.out.println(MonikaMonitorTaskParamsOpeSign.EQUAL);
	}
}
