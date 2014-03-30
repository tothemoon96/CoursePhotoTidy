package com.tothemoon.data;

import java.util.ArrayList;
import java.util.List;

public class DailyPhoto {
	int id;
	String originStr;
	List<String> picturePath;
	public DailyPhoto(int id,String originStr,String picturePath){
		this.originStr=originStr;
		this.id=id;
		this.picturePath=new ArrayList<String>();
		this.picturePath.add(picturePath);
	}
	public void add(String path){
		picturePath.add(path);
	}
	public String getOriginStr(){
		return originStr;
	}
	public String[] getStringPicturePath(){
		return picturePath.toArray(new String[picturePath.size()]);
	}
}
