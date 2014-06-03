package com.timesoft.kaitoo.common;

public class StringUtil {
	
	public static Boolean isEmpty(String s) {
		if(s == null || s.trim().equals("")) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
}
