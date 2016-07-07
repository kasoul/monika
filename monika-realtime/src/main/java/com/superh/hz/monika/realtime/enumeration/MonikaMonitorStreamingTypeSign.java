package com.superh.hz.monika.realtime.enumeration;

public enum MonikaMonitorStreamingTypeSign {
	
	DIRECT(1), RECEIVER(2);

	private int value;

	private MonikaMonitorStreamingTypeSign(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String toString(){
		return String.valueOf(this.getValue());
	}
	
	public static void main(String[] args) {
		System.out.println(MonikaMonitorStreamingTypeSign.DIRECT);
	}
}
