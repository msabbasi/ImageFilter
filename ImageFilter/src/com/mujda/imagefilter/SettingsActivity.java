package com.mujda.imagefilter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.SeekBar;

public class SettingsActivity extends Activity {

	public static final String PREFS_NAME = "MyPrefsFile";
	
	SeekBar filterSizeBar;
	TextView sizeText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
		
		filterSizeBar = (SeekBar) findViewById(R.id.seekBar1);
		sizeText = (TextView) findViewById(R.id.textView3);
		
		filterSizeBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if(progress%2==0){
					progress++;
				}
				sizeText.setText(""+progress);
			}
		});
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		boolean isMean = settings.getBoolean("meanMode", true);
		int size = settings.getInt("filterSize", 3);
		
		((RadioButton) findViewById(R.id.radioMean)).setChecked(isMean);
		((RadioButton) findViewById(R.id.radioMedian)).setChecked(!isMean);
		sizeText.setText(""+size);
		filterSizeBar.setProgress(size);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onStop(){
		super.onStop();
		
		int size = filterSizeBar.getProgress();
		if (size%2==0) {
			size++;
		}
		boolean isMean = ((RadioButton) findViewById(R.id.radioMean)).isChecked();
				
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("meanMode", isMean);
		editor.putInt("filterSize", size);

		editor.commit();
	}
	
	
}
