package com.superh.hz.monika.realtime.enumeration;

/**
 * @Describe:监控任务类型标记码
 */
public enum MonikaMonitorTaskSign {

	SIMPLE_TASK(1), COMMON_TASK(2), SELFDEFINED_TASK(3);

	private int value;

	private MonikaMonitorTaskSign(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String toString(){
		return String.valueOf(this.getValue());
	}
	
	public static void main(String[] args) {
		System.out.println(MonikaMonitorTaskSign.SIMPLE_TASK);
	}
}
