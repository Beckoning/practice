package com.pay.payment.center.jihe;

import java.util.*;
public class TestMap
{  
	public static void main(String args[])  
	{  
		try {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("1", "Hello");  
			hashMap.put("2", "World");  
			//			bb.remove("1");  //直接删除的方式 不会报错
			Iterator<String> it = hashMap.keySet().iterator();  
			while(it.hasNext()) {  
				Object ele = it.next(); 
				System.out.println(hashMap);
				if (ele.equals("1")) {
//					hashMap.remove(ele);    //出错 修改了映射结构 影响了迭代器遍历
					it.remove();              //用迭代器删除 则不会出错
				}
			} 
 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
 
	}  
 
}  