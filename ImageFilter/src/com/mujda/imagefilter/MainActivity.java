package com.mujda.imagefilter;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private Context context = this;
	
	private static int IMAGE_REQUEST = 1;
	
	public static final String PREFS_NAME = "MyPrefsFile";
	
	private Bitmap image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			changeSettings();
			return true;
		}
		if (id == R.id.action_load_image) {
			changeImage();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void changeImage() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, IMAGE_REQUEST);
	}
	
	public void changeSettings() {
		Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
		startActivity(intent);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			if (requestCode == IMAGE_REQUEST) {
				Uri uri = data.getData();
				InputStream input = null; 
				try {
					input = this.context.getContentResolver().openInputStream(uri);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				image = BitmapFactory.decodeStream(input);
				//ByteArrayOutputStream out = new ByteArrayOutputStream();
				//Bitmap image = imageHandler.handleImage(data, this.context);
				ImageView imageView = (ImageView)findViewById(R.id.imageView1); 
				imageView.setImageBitmap(image);
			}
		}
	}
	
	public void applyFilter(View v) {
		
		AsyncFilter filterTask = new AsyncFilter();
		
		filterTask.execute(image);
		
	}
	
	private class AsyncFilter extends AsyncTask<Bitmap, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(Bitmap... params) {
			SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			boolean isMean = settings.getBoolean("meanMode", true);
			int size = settings.getInt("filterSize", 3);
			
			if(isMean) {
				if (image == null){
					 image = BitmapFactory.decodeFile("blank.bmp");
				}
				image = Filter.applyMeanFilter(image, size);
			}
			
			return image;
		}
		
		//@Override
	//	protected void onProgressUpdate(Void) {
			
		//}
		
		@Override
		protected void onPostExecute(Bitmap image) {
			ImageView imageView = (ImageView)findViewById(R.id.imageView1); 
			imageView.setImageBitmap(image);
		}
	}
}
