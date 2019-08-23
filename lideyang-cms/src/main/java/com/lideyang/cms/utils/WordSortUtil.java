package com.lideyang.cms.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class WordSortUtil {

	public static String wordSortDesc(String str) {
		 String result=null;
	        try {
	            result = IKAnalyzerSupplyProduct.startIKAnalyzer(str);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		String[] split = result.split(" ");
    	Map<String,Integer> hashmap = new HashMap<String,Integer>();
    	for (String string : split) {
			// System.out.println(string);
    		Integer i = hashmap.get(string);
    		if(i == null) {
    			hashmap.put(string, 1);
    		}else {
    			hashmap.put(string, hashmap.get(string)+1);
    		}
		}
    	 List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(hashmap.entrySet());
         Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
             //升序排序
             public int compare(Entry<String, Integer> o1,
                     Entry<String, Integer> o2) {
                 return o2.getValue() - o1.getValue();
             }
             
         });
         String returnStr = list.get(0).getKey()+","+list.get(1).getKey()+","+list.get(2).getKey();
		return returnStr;
	}
}
