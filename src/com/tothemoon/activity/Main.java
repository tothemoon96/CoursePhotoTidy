package com.tothemoon.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.coursephototidy.R;
import com.tothemoon.data.CourseInformation;
import com.tothemoon.data.PhotoInformation;
import com.tothemoon.data.CoursePhoto;
import com.tothemoon.util.StringDealer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Main extends Activity
{
	ListView listView;
	SimpleAdapter simpleAdapter;
	String[] courses;
	String[] teachers;
	String[] details;
	String[] days;
	String[] beginTimes;
	String[] endTimes;
	
	CourseInformation[] courseInformation;
	
	File picturePath;
	List<File> pictureList;
	List<String> exifInformationList;
	
	List<PhotoInformation> photoInformationList;
	
	ProgressDialog pd;//显示处理照片的进度条
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			if(msg.what == 0x123){
				Toast.makeText(Main.this,"读取相片失败",Toast.LENGTH_SHORT).show();
				pd.dismiss();
			}
			if(msg.what == 0x234){
				Toast.makeText(Main.this,"相片读取完毕",Toast.LENGTH_SHORT).show();
				pd.dismiss();
			}
			if(msg.what == 0x345){
				Toast.makeText(Main.this,"无相片信息",Toast.LENGTH_SHORT).show();
			}
			if(msg.what == 0x456){//
				pd.show();
			}
		}	
	};
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Intent intent = getIntent();
		ArrayList<Parcelable> listcI =intent.getParcelableArrayListExtra("courseInformation");
		courseInformation = listcI.toArray(new CourseInformation[listcI.size()]);
		
		List<String> list = new ArrayList<String>();
		for(int i=0;i<courseInformation.length;i++){
			list.add(courseInformation[i].getCourse());
		}
		courses = list.toArray(new String[list.size()]);
		
		list = new ArrayList<String>();
		for(int i=0;i<courseInformation.length;i++){
			list.add(courseInformation[i].getDay());
		}
		days = list.toArray(new String[list.size()]);
		
		list = new ArrayList<String>();
		for(int i=0;i<courseInformation.length;i++){
			list.add(courseInformation[i].getDay());
		}
		days = list.toArray(new String[list.size()]);
		
		list = new ArrayList<String>();
		for(int i=0;i<courseInformation.length;i++){
			list.add(courseInformation[i].getTeacher());
		}
		teachers = list.toArray(new String[list.size()]);
		
		list = new ArrayList<String>();
		for(int i=0;i<courseInformation.length;i++){
			list.add(courseInformation[i].getDetail());
		}
		details = list.toArray(new String[list.size()]);
		
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < courses.length; i++)
		{
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("course", days[i]+"|"+courses[i]);
			listItem.put("teachers", teachers[i]);
			listItem.put("details", details[i]);
			listItems.add(listItem);
		}
		// 创建一个SimpleAdapter
		simpleAdapter = new SimpleAdapter(Main.this, listItems,
			R.layout.course, 
			new String[] { "course", "teachers","details"},
			new int[] { R.id.course, R.id.teacher,R.id.detail});
		listView=(ListView) findViewById(R.id.mylist);
		listView.setAdapter(simpleAdapter);
		
		
		//初始化进度条
		pd=new ProgressDialog(Main.this);
		pd.setTitle("正在扫描手机上的照片");
		pd.setMessage("扫描照片中，请等待");
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setIndeterminate(true);
		pd.setCancelable(false);
		
		photoInformationList = new ArrayList<PhotoInformation>();
		pictureList = new ArrayList<File>();
		exifInformationList= new ArrayList<String>();
		
		new Thread()
		{
			@Override
			public void run()
			{
				//读取相片信息
				File sdCardDir = Environment.getExternalStorageDirectory();
				try {
					Message msg=new Message();
					msg.what=0x456;
					
					handler.sendMessage(msg);
					picturePath = new File(sdCardDir.getCanonicalPath()+"/"+"DCIM"+"/");
					getFile(picturePath);
					
					for(int i=0;i<pictureList.size();i++){
						photoInformationList.add(new PhotoInformation(exifInformationList.get(i),pictureList.get(i)));
					}
					
					msg.what=0x234;
					handler.sendMessage(msg);
				} 
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Message msg=new Message();
					msg.what=0x123;
					handler.sendMessage(msg);
				}
			}
		}.start();

		// 为ListView的列表项单击事件绑定事件监听器
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			// 第position项被单击时激发该方法。
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{
				List<CoursePhoto> coursePhotoList = new ArrayList<CoursePhoto>();
				for(int i=0;i<photoInformationList.size();i++){
					if(photoInformationList.get(i).sumMinute>=StringDealer.transCourseStartTime(courseInformation[position].getBeginTime())
							&&photoInformationList.get(i).sumMinute<=StringDealer.transCourseEndTime(courseInformation[position].getEndTime())
							&&Integer.parseInt(courseInformation[position].getDay())==photoInformationList.get(i).dateDealer.weekDay)
					{	
						coursePhotoList.add(new CoursePhoto(photoInformationList.get(i).picturePath.toString(),photoInformationList.get(i).dateDealer.getOriginStr()));
					}
				}
				if(coursePhotoList.size()!=0){
					//创建Intend，放入course信息
					Intent intent = new Intent(Main.this,PhotoPreview.class);
					intent.putExtra("beginTime",courseInformation[position].getBeginTime());
					intent.putExtra("course",courseInformation[position].getCourse());
					intent.putExtra("endTime",courseInformation[position].getEndTime());
					//传入放入照片信息
					intent.putParcelableArrayListExtra("coursePhotoList",(ArrayList<? extends Parcelable>) coursePhotoList);
					// 启动intent对应的Activity
					startActivity(intent);
				}
				else{
					Message msg=new Message();
					msg.what=0x345;
					handler.sendMessage(msg);
				}
			}
		});
	}
	
	public List<File> getFile(File file) throws IOException{
		File[] fileArray =file.listFiles();
		for (File f : fileArray) {
			if(f.isFile()){
				ExifInterface exifInterface = new ExifInterface(f.toString());
				String fDateTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
				//判断文件是照片
				if(fDateTime!=null){
					pictureList.add(f);
					exifInformationList.add(fDateTime);
				}
			}else{
				getFile(f);
			}
		}
		return pictureList;
	}	
}