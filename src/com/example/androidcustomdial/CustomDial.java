package com.example.androidcustomdial;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class CustomDial extends Activity {

	private SeekBar valueBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dial);
        valueBar = (SeekBar)findViewById(R.id.valueBar);
        valueBar.setMax(100);
        final AdvProgressBar progressbar = (AdvProgressBar)findViewById(R.id.progressbar);
        progressbar.setValue(0);
        valueBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int position, boolean arg2) {
				progressbar.setValue(position);
			}
		});
        
    }
}
