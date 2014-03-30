package com.example.testalbums;

import java.io.File;
import java.util.ArrayList;

import com.example.coursephototidy.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	private ArrayList<String> mList;
	private String[] picturePath;
	private GridView gridView;
	private ImageAdapter imageAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Bundle data=this.getIntent().getExtras();
        picturePath = data.getStringArray("picturePath");
        mList=new ArrayList<String>();
        for(String s:picturePath) mList.add(s);
        
		gridView = (GridView) findViewById(R.id.gridView1);
		imageAdapter = new ImageAdapter(this,mList);
		gridView.setAdapter(imageAdapter);
		
		gridView.setOnItemClickListener(new OnItemClickListener()
		{
			// 第position项被单击时激发该方法。
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(new File(picturePath[position])), "image/*");
				startActivity(intent);
			}
				
		});
	}
}
