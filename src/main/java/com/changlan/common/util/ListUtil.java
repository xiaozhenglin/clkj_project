package com.changlan.common.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
	
	public static boolean isEmpty(List list) {
		if(list == null || list.size()==0 || list.get(0) == null) { 
			return true;
		}
		return false;
	}

	public static List<Integer> changeStrToInteger(List<String> stringToList) {
		List<Integer> list =  new ArrayList<Integer>();
		for(String str: stringToList) {
			Integer result = Integer.parseInt(str);
			list.add(result);
		}
		return list;
	}
}
