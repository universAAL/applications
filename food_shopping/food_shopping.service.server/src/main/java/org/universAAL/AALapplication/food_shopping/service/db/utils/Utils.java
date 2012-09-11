package org.universAAL.AALapplication.food_shopping.service.db.utils;

public class Utils {

	public static String stringValueOf(long[] array){
		StringBuffer ret = new StringBuffer();
		if (array != null && array.length > 0){
			for(int i=0; i<array.length; i++){
				ret.append(array[i]).append(",");
			}
			ret.deleteCharAt(ret.lastIndexOf(","));
		}
		return ret.toString();
	}
	
	public static String stringValueOf(Long[] array){
		StringBuffer ret = new StringBuffer();
		if (array != null && array.length > 0){
			for(int i=0; i<array.length; i++){
				ret.append(array[i]).append(",");
			}
			ret.deleteCharAt(ret.lastIndexOf(","));
		}
		return ret.toString();
	}
}
