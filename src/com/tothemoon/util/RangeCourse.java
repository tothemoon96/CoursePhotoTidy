package com.tothemoon.util;

//对课表按时间排序
public class RangeCourse{
	public static void order(String[] days,String[] beginTimes,String[] endTimes,String[] courses,String[] teachers,String[] details){
		int[] marks=new int[days.length];
		for (int i=0;i<days.length;i++){
			try{
				marks[i]=Integer.parseInt(days[i])*14+Integer.parseInt(beginTimes[i]);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		//开始排序
		int tempI = 0;
		String tempS = null;
		for (int i=0;i<days.length;i++){
			for (int j=i;j<days.length;j++){
				if(marks[i]>marks[j]){
					tempI=marks[i];
					marks[i]=marks[j];
					marks[j]=tempI;
					
					tempS = new String(days[i]);
					days[i]=new String(days[j]);
					days[j]=new String(tempS);
					
					tempS = new String(beginTimes[i]);
					beginTimes[i]=new String(beginTimes[j]);
					beginTimes[j]=new String(tempS);
					
					tempS = new String(endTimes[i]);
					endTimes[i]=new String(endTimes[j]);
					endTimes[j]=new String(tempS);
					
					tempS = new String(courses[i]);
					courses[i]=new String(courses[j]);
					courses[j]=new String(tempS);
					
					tempS = new String(teachers[i]);
					teachers[i]=new String(teachers[j]);
					teachers[j]=new String(tempS);
					
					tempS = new String(details[i]);
					details[i]=new String(details[j]);
					details[j]=new String(tempS);
				}
			}
		}
	}
}