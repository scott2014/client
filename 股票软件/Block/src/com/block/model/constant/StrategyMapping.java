package com.block.model.constant;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class StrategyMapping {
	private static Map<String,String> map = new HashMap<String,String>();

	static {
		map.put("01BP01", "一买开");
		map.put("01SU01", "一卖平01");
		map.put("01SU02", "一卖平02");
		map.put("02SP01", "二卖开");
		map.put("02BU01", "二买平01");
		map.put("02BU02", "二买平02");
		map.put("03BP01", "三买开");
		map.put("03SU01", "三卖平");
		map.put("04SP01", "四卖开");
		map.put("04BU01", "四买平");
		map.put("05BP01", "五买开");
		map.put("05SU01", "五卖平");
		map.put("01SP01", "一卖开");
		map.put("01BU01", "一买平01");
		map.put("01BU02", "一买平02");
		map.put("02BP01", "二买开");
		map.put("02SU01", "二卖平01");
		map.put("02SU02", "二卖平02");
		map.put("03SP01", "三卖开");
		map.put("03BU01", "三买平");
		map.put("04BP01", "四买开");
		map.put("04SU01", "四卖平");
		map.put("05SP01", "五卖开");
		map.put("05BU01", "五买平");
	}
	
	public static String getKey(String value) {
		Set keySet = StrategyMapping.map.keySet();
		Iterator<String> it = keySet.iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (key.equals(value)) return key;
		}
		return "";
	}
	
	public static String getValue(String key) {
		return StrategyMapping.map.get(key);
	}
}
