package test.superh.hz.monika.realtime.throwable;

import com.superh.hz.monika.realtime.throwable.MonitorConditionException;

public class TestCase {

	public static void main(String[] args) {
		try {
			testa();
		} catch (MonitorConditionException e) {
			//e.printStackTrace();
			System.out.println(e.toString());
		}
	}
	
	public static void testa() throws MonitorConditionException{
		throw new MonitorConditionException();
	}
	
	
}
