package com.tothemoon.data;

//主要负责解析yyyy:mm:dd hh:mm:ss信息，也负责解析yyyy:mm:dd
public class DateDealer {
	
	final String originStr;//欲解析时间字符串	
	String value=null;//临时保存信息的字符串
	public int year;
	public int month;
	public int day;
	public int hour;
	public int minute;
	public int weekDay;
	public int second;
	public long idS;
	public int id; 
	
	public DateDealer(String originStr){
		this.originStr=originStr;
		getYear();
		getMonth();
		getDay();
		getHour();
		getMinute();
		getSecond();
		getWeekDay();
		getId();
	}
	
	public String getOriginStr(){
		return originStr;
	}
	
	public void getYear(){
		value = originStr.substring(0, 4);
		try{
			year=Integer.parseInt(value);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void getMonth(){
		value = originStr.substring(5, 7);
		try{
			month=Integer.parseInt(value);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void getDay(){
		value = originStr.substring(8, 10);
		try{
			day=Integer.parseInt(value);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void getHour(){
		value = originStr.substring(11, 13);
		try{
			hour=Integer.parseInt(value);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void getMinute(){
		value = originStr.substring(14, 16);
		try{
			minute=Integer.parseInt(value);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void getSecond(){
		char[] secondC=new char[2];
		secondC[0]=originStr.charAt(17);
		secondC[1]=originStr.charAt(18);
		value=new String(secondC);
		try{
			second=Integer.parseInt(value);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int sumSecond(){
		return hour*3600+minute*60+second;
	}
	
	//判断指定日期是星期几
	public void getWeekDay(){  
	    try{
	    	int month=this.month,year=this.year;
			if(month==1){
				month=13;
				year=year-1;
			}
			if(month==2){
				month=14;
				year=year-1;
			}
			int h = (int) ((day + Math.floor(26 * (month + 1) / 10) + (year % 100)
					+ Math.floor((year % 100) / 4)
					+ Math.floor(Math.abs(year / 100) / 4) + 5 * Math
					.abs(year / 100)) % 7);
			switch (h) {
			case 0: weekDay= 6;break;
			case 1: weekDay= 7;break;
			case 2: weekDay= 1;break;
			case 3: weekDay= 2;break;
			case 4: weekDay= 3;break;
			case 5: weekDay= 4;break;
			case 6: weekDay= 5;break;
			}
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	//判断指定日期是给指定日期一个id
	public void getId(){
	    int leap;
	    switch(month)
	    {
	                 case 1:id = 0;break;
	                 case 2:id = 31;break;
	                 case 3:id = 31 + 28;break;
	                 case 4:id = 31 + 28 + 31;break;
	                 case 5:id = 31 + 28 + 31 + 30;break;
	                 case 6:id = 31 + 28 + 31 + 30 + 31;break;
	                 case 7:id = 31 + 28 + 31 + 30 + 31 + 30;break;
	                 case 8:id = 31 + 28 + 31 + 30 + 31 + 30 + 31;break;
	                 case 9:id = 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31;break;
	                 case 10:id = 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30;break;
	                 case 11:id = 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31;break;
	                 case 12:id = 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30;break;
	                 default:id =0;break;
	    }
	    id += day;
		if ((year % 100 == 0 && year % 400 == 0 ) || (year % 4 == 0 && year % 100 != 0)){
			leap = 1;
		}
		else{
			leap = 0;
		}
		if (leap == 1 && month > 2){
			id += 1;
		}
		id=year*1000+id;
		idS=id*100000+sumSecond();
	}
}