package com.tothemoon.data;

import java.io.File;

public class PhotoInformation {
	public DateDealer dateDealer;
	public File picturePath;
	public int sumMinute;
	public int id;
	public long idS;
	
	public PhotoInformation(String dateDealer,File picturePath){
		this.dateDealer=new DateDealer(dateDealer);
		this.picturePath=picturePath;
		getSumMinute();
		getId();
	}
	
	public void getSumMinute(){
		sumMinute=dateDealer.hour*60+dateDealer.minute;
	}
	
	public void getId(){
		id=dateDealer.id;
		idS=dateDealer.idS;
	}
}
