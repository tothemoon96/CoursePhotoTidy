package com.tothemoon.data;

import android.os.Parcel;
import android.os.Parcelable;

public class CourseInformation implements Parcelable {
	private int id;
	private String course;
	private String teacher;
	private String detail;
	private String day;
	private String beginTime;
	private String endTime;

	public CourseInformation(String day,String beginTime,String 

endTime,String course,String teacher,String detail){
		this.course=course;
		this.teacher=teacher;
		this.detail=detail;
		this.day=day;
		this.beginTime=beginTime;
		this.endTime=endTime;
	}

	public CourseInformation() {
		// TODO Auto-generated constructor stub
	}

	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public String getCourse(){
		return course;
	}
	public void setCourse(String course){
		this.course=course;
	}
	public String getTeacher(){
		return teacher;
	}
	public void setTeacher(String teacher){
		this.teacher=teacher;
	}
	public String getDetail(){
		return detail;
	}
	public void setDetail(String detail){
		this.detail=detail;
	}
	public String getDay(){
		return day;
	}
	public void setDay(String day){
		this.day=day;
	}
	public String getBeginTime(){
		return beginTime;
	}
	public void setBeginTime(String beginTime){
		this.beginTime=beginTime;
	}
	public String getEndTime(){
		return endTime;
	}
	public void setEndTime(String endTime){
		this.endTime=endTime;
	}
	
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(course);
		dest.writeString(teacher);
		dest.writeString(detail);
		dest.writeString(day);
		dest.writeString(beginTime);
		dest.writeString(endTime);
	}

	public static final Parcelable.Creator<CourseInformation> CREATOR = new 

Parcelable.Creator<CourseInformation>() {

		@Override
		public CourseInformation createFromParcel(Parcel source) {
			CourseInformation cI = new CourseInformation();
			cI.id = source.readInt();
			cI.course = source.readString();
			cI.teacher = source.readString();
			cI.detail=source.readString();
			cI.day = source.readString();
			cI.beginTime = source.readString();
			cI.endTime = source.readString();
			return cI;
		}
		@Override
		public CourseInformation[] newArray(int size) {
			return new CourseInformation[size];
		}
	};
	public CourseInformation clone() {
		CourseInformation cI = new CourseInformation();
		cI.setId(CourseInformation.this.id);
		cI.setCourse(CourseInformation.this.course);
		cI.setTeacher(CourseInformation.this.teacher);
		cI.setDetail(CourseInformation.this.detail);
		cI.setDay(CourseInformation.this.day);
		cI.setBeginTime(CourseInformation.this.beginTime);
		cI.setEndTime(CourseInformation.this.endTime);
		return cI;
	}
}
