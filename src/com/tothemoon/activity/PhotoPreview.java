package com.tothemoon.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.coursephototidy.R;
import com.tothemoon.data.CoursePhoto;
import com.tothemoon.data.DailyPhoto;
import com.tothemoon.data.PhotoInformation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PhotoPreview extends Activity {
	SimpleAdapter simpleAdapter;
	ListView listView;
	TextView coursename;
	CoursePhoto[] coursePhotoList;
	PhotoInformation[] photoInformationList;
	List<DailyPhoto> dailyPhotoList ;
	List<Map<String, Object>> listItems;
	CoursePhoto cP;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photolist);
		// 获取启动该Result的Intent
		Intent intent = getIntent();
		ArrayList<Parcelable> listcPL =intent.getParcelableArrayListExtra("coursePhotoList");
		String course = intent.getStringExtra("course");
		String beginTime = intent.getStringExtra("beginTime");
		String endTime = intent.getStringExtra("endTime");
		
		coursename = (TextView) findViewById(R.id.coursename);
		coursename.setText(course);
		
		listView=(ListView) findViewById(R.id.timelist);
		
		//如果没有照片
		if(listcPL.size()==0){
			Toast.makeText(PhotoPreview.this,"暂无该课堂的照片信息",Toast.LENGTH_SHORT).show();
		}
		
		//如果只有一张照片
		else if(listcPL.size()==1){
			cP=(CoursePhoto) listcPL.get(0);
			listItems = new ArrayList<Map<String, Object>>();
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("pl_date",cP.getOriginStr().substring(0,10));
			listItem.put("pl_time", beginTime+"-"+endTime);
			listItems.add(listItem);
			
			simpleAdapter = new SimpleAdapter(PhotoPreview.this, listItems,
					R.layout.photolist_listview, 
					new String[] { "pl_date", "pl_time"},
					new int[] { R.id.pl_date, R.id.pl_time});
			listView.setAdapter(simpleAdapter);
			
			listView.setOnItemClickListener(new OnItemClickListener()
			{
				// 第position项被单击时激发该方法。
				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id)
				{
					String[] picturePathString=new String[]{cP.getPicturePath()};
					//创建Intend，放入course信息
					Intent sendIntent = new Intent(PhotoPreview.this,com.example.testalbums.MainActivity.class);
					Bundle data=new Bundle();  
					data.putStringArray("picturePath", picturePathString);  
					sendIntent.putExtras(data);
				    // 启动intent对应的Activity
					startActivity(sendIntent);
				}
			});
		}
		else{
			
			coursePhotoList=listcPL.toArray(new CoursePhoto[listcPL.size()]);
		
			photoInformationList=new PhotoInformation[coursePhotoList.length];
			for(int i=0;i<coursePhotoList.length;i++){
				photoInformationList[i]=new PhotoInformation(coursePhotoList[i].getOriginStr(),new File(coursePhotoList[i].getPicturePath()));
			}
		
			for(int i=0;i<photoInformationList.length;i++){
				for(int j=i+1;j<photoInformationList.length;j++){
					if(photoInformationList[i].idS<photoInformationList[j].idS){
						PhotoInformation temp = new PhotoInformation(photoInformationList[i].dateDealer.getOriginStr(),
								photoInformationList[i].picturePath);
						photoInformationList[i]=new PhotoInformation(photoInformationList[j].dateDealer.getOriginStr(),
								photoInformationList[j].picturePath);
						photoInformationList[j]=new PhotoInformation(temp.dateDealer.getOriginStr(),
								temp.picturePath);
						
					}
				}
			}
			
			
			dailyPhotoList = new ArrayList<DailyPhoto>();
			dailyPhotoList.add(new DailyPhoto(photoInformationList[0].id,
					photoInformationList[0].dateDealer.getOriginStr(),
					photoInformationList[0].picturePath.toString()));
			
			int j=0;
			for(int i=0;i<photoInformationList.length;i++){
				if(i+1<photoInformationList.length){
					if(photoInformationList[i+1].id==photoInformationList[i].id)
							dailyPhotoList.get(j).add(photoInformationList[i+1].picturePath.toString());
					else{
						dailyPhotoList.add(new DailyPhoto(photoInformationList[i+1].id,
								photoInformationList[i+1].dateDealer.getOriginStr(),
								photoInformationList[i+1].picturePath.toString()));
						j++;
					}
				}
			}
			
			listItems = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < dailyPhotoList.size(); i++)
			{
				Map<String, Object> listItem = new HashMap<String, Object>();
				listItem.put("pl_date", (dailyPhotoList.get(i).getOriginStr()).substring(0, 10));
				listItem.put("pl_time", beginTime+"-"+endTime);
				listItems.add(listItem);
			}
			simpleAdapter = new SimpleAdapter(PhotoPreview.this, listItems,
					R.layout.photolist_listview, 
					new String[] { "pl_date", "pl_time"},
					new int[] { R.id.pl_date, R.id.pl_time});
			listView.setAdapter(simpleAdapter);
			
			listView.setOnItemClickListener(new OnItemClickListener()
			{
				// 第position项被单击时激发该方法。
				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id)
				{
					String[] picturePathString=dailyPhotoList.get(position).getStringPicturePath();
					//创建Intend，放入course信息
					Intent sendIntent = new Intent(PhotoPreview.this,com.example.testalbums.MainActivity.class);
					Bundle data=new Bundle();  
					data.putStringArray("picturePath", picturePathString);  
					sendIntent.putExtras(data);
				    // 启动intent对应的Activity
					startActivity(sendIntent);
				}
			});
		}
		
		
	}
}
