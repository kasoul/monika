package com.superh.hz.monika.realtime.enumeration;


public enum MonikaMonitorTaskParamsValueTypeSign {

	STRING(1), INT(2), DATE(3);

	private int value;

	private MonikaMonitorTaskParamsValueTypeSign(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String toString(){
		return String.valueOf(this.getValue());
	}
	
	public static void main(String[] args) {
		System.out.println(MonikaMonitorTaskParamsValueTypeSign.STRING);
	}
}
