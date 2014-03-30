package com.tothemoon.util;

import java.util.ArrayList;
import java.util.List;


public class StringDealer {
	final String originStr;//欲解析字符串
	final String matchingStr;//匹配的key
	String value=null;//key对应的value
	List<String> target;//输出结果
	int startIndex=0;//确定value的起始位置("后的第一个字符)
	int endIndex=0;//确定value的结束位置(")
	
	public StringDealer(String originStr,String matchingStr){
		this.originStr=originStr;
		this.matchingStr=matchingStr;
		value=new String();
		target=new ArrayList<String>();
		startIndex=0;
		endIndex=0;
	}
	public void searchString(){
		startIndex=originStr.indexOf(matchingStr,startIndex)+matchingStr.length();
	}
	public void cutString(){
		//获取value值的字符串索引
		startIndex++;
		endIndex = startIndex+1;
		endIndex=originStr.indexOf("\"",endIndex);
		value = originStr.substring(startIndex, endIndex);
	}
	public void bulidList(){
		target.add(value);
	}
	//解析字符串
	public List<String> praseString(){
		while(endIndex<originStr.length()){
		searchString();
		if(startIndex-matchingStr.length()==-1) break;//如果到originStr结尾找不到字符串，跳出循环
		cutString();
		bulidList();
		endIndex=startIndex;
		}
		return target;
	}
	//将1,2,3,4,5,6,7转换成星期
	public static String[] transWeekday(String[] day){
		outer:
			for (int i=0;i<day.length;i++){
				if(day[i].equals("1")) {day[i]="周一";continue outer;}
				else if(day[i].equals("2")) {day[i]="周二";continue outer;}
				else if(day[i].equals("3")) {day[i]="周三";continue outer;}
				else if(day[i].equals("4")) {day[i]="周四";continue outer;}
				else if(day[i].equals("5")) {day[i]="周五";continue outer;}
				else if(day[i].equals("6")) {day[i]="周六";continue outer;}
				else if(day[i].equals("7")) {day[i]="周日";continue outer;}
			}
			return day;
	}
	
	public static int transCourseStartTime(String beginTime){
		int sumMinute=0;
		if(beginTime.equals("1")) sumMinute=8*60;
		else if(beginTime.equals("2")) sumMinute=8*60+50;
		else if(beginTime.equals("3")) sumMinute=9*60+50;
		else if(beginTime.equals("4")) sumMinute=10*60+40;
		else if(beginTime.equals("5")) sumMinute=11*60+30;
		else if(beginTime.equals("6")) sumMinute=13*60+15;
		else if(beginTime.equals("7")) sumMinute=14*60+5;
		else if(beginTime.equals("8")) sumMinute=14*60+55;
		else if(beginTime.equals("9")) sumMinute=15*60+45;
		else if(beginTime.equals("10")) sumMinute=16*60+40;
		else if(beginTime.equals("11")) sumMinute=17*60+30;
		else if(beginTime.equals("12")) sumMinute=19*60;
		else if(beginTime.equals("13")) sumMinute=19*60+50;
		else if(beginTime.equals("14")) sumMinute=20*60+40;
		return sumMinute;
	}
	
	public static int transCourseEndTime(String beginTime){
		int sumMinute=0;
		if(beginTime.equals("1")) sumMinute=8*60+45;
		else if(beginTime.equals("2")) sumMinute=9*60+35;
		else if(beginTime.equals("3")) sumMinute=10*60+35;
		else if(beginTime.equals("4")) sumMinute=11*60+25;
		else if(beginTime.equals("5")) sumMinute=12*60+15;
		else if(beginTime.equals("6")) sumMinute=14*60;
		else if(beginTime.equals("7")) sumMinute=14*60+50;
		else if(beginTime.equals("8")) sumMinute=15*60+40;
		else if(beginTime.equals("9")) sumMinute=16*60+30;
		else if(beginTime.equals("10")) sumMinute=17*60+25;
		else if(beginTime.equals("11")) sumMinute=18*60+15;
		else if(beginTime.equals("12")) sumMinute=19*60+45;
		else if(beginTime.equals("13")) sumMinute=20*60+35;
		else if(beginTime.equals("14")) sumMinute=21*60+25;
		return sumMinute;
	}
}
