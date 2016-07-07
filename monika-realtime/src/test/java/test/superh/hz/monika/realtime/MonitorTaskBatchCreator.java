package test.superh.hz.monika.realtime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.superh.hz.monika.realtime.result.MonitorResult;
import com.superh.hz.monika.realtime.task.MonitorTask;
import com.superh.hz.monika.realtime.task.MonitorTaskParser;


import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

public class MonitorTaskBatchCreator {

	public static void main(String[] args) {
		
		//addSimpleTask();
		//addCommonTask();
		
		//getALLTask();
		//deleteAllTask();
		//getResluts();
		//delResluts();
		
	}
	
	/**
	 * 添加智能布控任务 
	 */
	public static void addSimpleTask(){
		Jedis jedis = new Jedis("node2", 6379);
		Map<String,String> map = new HashMap<String,String>();
			String monitorTaskKey = "1-" + 6;
			JSONObject jsonTask = new JSONObject();
			jsonTask.element(MonitorTask.TASK_BEGIN_TIME_FIELD_NAME, 0l);
			jsonTask.element(MonitorTask.TASK_END_TIME_FILED_NAME, 9999999999999L);
			/*String paramsString = "age" + MonitorTaskParser.SIMPLE_TASK_PARAMS_ITEM_SPILT +"7"
					+ MonitorTaskParser.SIMPLE_TASK_PARAMS_SPILT + "name"
					+ MonitorTaskParser.SIMPLE_TASK_PARAMS_ITEM_SPILT +"Jack";*/
			/*String paramsString = "age" + MonitorTaskParser.SIMPLE_TASK_PARAMS_ITEM_SPILT +"6"
					+ MonitorTaskParser.SIMPLE_TASK_PARAMS_SPILT + "name"
					+ MonitorTaskParser.SIMPLE_TASK_PARAMS_ITEM_SPILT +"Lily";*/
			String paramsString = "";
			jsonTask.element(MonitorTask.TASK_PARAMS_FILED_NAME, paramsString);
			//System.out.println(jsonTask.toString());
			map.put(monitorTaskKey, jsonTask.toString());
		//System.out.println(jedis.exists(RedisKeyConstant.VEHICLE_MONITOR_TASK_KEY));
		jedis.hmset("hc:test:monitor:task", map);
		//System.out.println(jedis.exists(RedisKeyConstant.VEHICLE_MONITOR_TASK_KEY));
		Map<String,String> mapget = jedis.hgetAll("hc:test:monitor:task");
		System.out.println("redis任务数量:" +  mapget.size());
		for(String key : mapget.keySet()){
			System.out.println("任务key:" + key  + ";" + "任务参数" + mapget.get(key));
			//JSONObject jtask = JSONObject.fromObject(mapget.get(key));
			//System.out.println(mp.getPlateNumber().equals(""));
			//System.out.println(mp.getNoPlate()==0);
			//System.out.println(mp.getPlateColor()==0);
			//System.out.println(mp.getResourcesUUIDs().length);
		}
	}
	
	/**
	 * 添加车牌监控任务 
	 */
	public static void addCommonTask(){
		Jedis jedis = new Jedis("node2", 6379);
		Map<String,String> map = new HashMap<String,String>();
			String monitorTaskKey = "1-" + 2;
			JSONObject jsonTask = new JSONObject();
			jsonTask.element(MonitorTask.TASK_BEGIN_TIME_FIELD_NAME, 9999999999999L);
			jsonTask.element(MonitorTask.TASK_END_TIME_FILED_NAME, 9999999999999L);
			String paramsString = "age" + MonitorTaskParser.SIMPLE_TASK_PARAMS_ITEM_SPILT +"7"
					+ MonitorTaskParser.SIMPLE_TASK_PARAMS_SPILT + "name"
					+ MonitorTaskParser.SIMPLE_TASK_PARAMS_ITEM_SPILT +"Jack";
			jsonTask.element(MonitorTask.TASK_PARAMS_FILED_NAME, paramsString);
			System.out.println(jsonTask.toString());
			map.put(monitorTaskKey, jsonTask.toString());
		//System.out.println(jedis.exists(RedisKeyConstant.VEHICLE_MONITOR_TASK_KEY));
		jedis.hmset("hc:test:monitor:task", map);
		//System.out.println(jedis.exists(RedisKeyConstant.VEHICLE_MONITOR_TASK_KEY));
		Map<String,String> mapget = jedis.hgetAll("hc:test:monitor:task");
		System.out.println("redis任务数量:" +  mapget.size());
		for(String key : mapget.keySet()){
			System.out.println("任务key:" + key  + ";" + "任务参数" + mapget.get(key));
			//JSONObject jtask = JSONObject.fromObject(mapget.get(key));
			//System.out.println(mp.getPlateNumber().equals(""));
			//System.out.println(mp.getNoPlate()==0);
			//System.out.println(mp.getPlateColor()==0);
			//System.out.println(mp.getResourcesUUIDs().length);
		}
	}
	
	public static void getALLTask(){
		Jedis jedis = new Jedis("node2", 6379);
		
		System.out.println(jedis.exists("hc:test:monitor:task"));
		//System.out.println(jedis.exists(RedisKeyConstant.VEHICLE_MONITOR_TASK_KEY));
		Map<String,String> mapget = jedis.hgetAll("hc:test:monitor:task");
		System.out.println("redis任务数量:" +  mapget.size());
		for(String key : mapget.keySet()){
			System.out.println("任务key:" + key  + ";" + "任务参数" + mapget.get(key));
			//JSONObject jtask = JSONObject.fromObject(mapget.get(key));
			//System.out.println(mp.getPlateNumber().equals(""));
			//System.out.println(mp.getNoPlate()==0);
			//System.out.println(mp.getPlateColor()==0);
			//System.out.println(mp.getResourcesUUIDs().length);
		}
	}
	
	public static void deleteAllTask(){
		Jedis jedis = new Jedis("node2", 6379);
		System.out.println(jedis.exists("hc:test:monitor:task"));
		jedis.del("hc:test:monitor:task");
		System.out.println(jedis.exists("hc:test:monitor:task"));
	}
	
	/*public static void addResult(){
		Jedis jedis = new Jedis("172.16.4.113", 6379);
	
		MonitorResult monitorResult = new MonitorResult();
		monitorResult.setMonitorTaskType("2");;
		monitorResult.setMonitorTaskId("2");
		monitorResult.setPlateNumber("浙ALGUIC");
		//monitorParams.setResourcesUUIDs("a,b,c".split(","));
		//monitorParams.setNoPlate((byte) '1');
		//monitorParams.setPlateColor(25);
		String monitorResultJson = JSONObject.fromObject(monitorResult).toString();
		System.out.println(jedis.exists(RedisKeyConstant.VEHICLE_MONITOR_RESULT_KEY));
		
		//System.out.println(jedis.exists(RedisKeyConstant.VEHICLE_MONITOR_TASK_KEY));
		jedis.lpush(RedisKeyConstant.VEHICLE_MONITOR_RESULT_KEY,monitorResultJson);
		
		System.out.println(jedis.llen(RedisKeyConstant.VEHICLE_MONITOR_RESULT_KEY));
		System.out.println(jedis.exists(RedisKeyConstant.VEHICLE_MONITOR_RESULT_KEY));
		System.out.println(jedis.lpop(RedisKeyConstant.VEHICLE_MONITOR_RESULT_KEY));
		System.out.println(jedis.llen(RedisKeyConstant.VEHICLE_MONITOR_RESULT_KEY));

	} */
	
	public static void getResluts(){
		Jedis jedis = new Jedis("node2", 6379);
		
		List<String> list = jedis.lrange("hc:test:monitor:result",0,-1);
		int count = 0;
		System.out.println(list.size());
		for(String s :list){
			System.out.println(s);
			MonitorResult mr = ((MonitorResult)JSONObject.toBean(JSONObject.fromObject(s),MonitorResult.class));
			if(mr.getMonitorTaskType().equals("1")){
				count++;
			}
		}
		
		System.out.println(count);
		//System.out.println(list.get(0));

	}
	
	public static void delResluts(){
		Jedis jedis = new Jedis("node2", 6379);
		
		jedis.del("hc:test:monitor:result");
		List<String> list = jedis.lrange("hc:test:monitor:result",0,-1);
		System.out.println(list.size());

	}
	
}
