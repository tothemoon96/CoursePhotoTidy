package com.tothemoon.data;

import android.os.Parcel;
import android.os.Parcelable;

public class CoursePhoto implements Parcelable{
	private String picturePath;
	private String originStr;

	public CoursePhoto(String picturePath,String originStr){
		this.picturePath=picturePath;
		this.originStr=originStr;
	}
	
	public CoursePhoto() {
		// TODO Auto-generated constructor stub
	}

	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath){
		this.picturePath=picturePath;
	}
	public String getOriginStr() {
		return originStr;
	}
	public void setOriginStr(String originStr){
		this.originStr=originStr;
	}
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(picturePath);
		dest.writeString(originStr);
	}

	public static final Parcelable.Creator<CoursePhoto> CREATOR = new 

			Parcelable.Creator<CoursePhoto>() {

		@Override
		public CoursePhoto createFromParcel(Parcel source) {
			CoursePhoto cP = new CoursePhoto();
			cP.picturePath = source.readString();
			cP.originStr = source.readString();
			return cP;
		}
		@Override
		public CoursePhoto[] newArray(int size) {
			return new CoursePhoto[size];
		}
	};
	public CoursePhoto clone() {
		CoursePhoto cP = new CoursePhoto();
		cP.setPicturePath(CoursePhoto.this.picturePath);
		cP.setOriginStr(CoursePhoto.this.originStr);
		return cP;
	}
	
	
	
	
}
