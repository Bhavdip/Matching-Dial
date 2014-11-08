package com.example.androidcustomdial;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;


public class CustomDial extends Activity {

	private AdvDialView mAdvDialView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dial);
        mAdvDialView = (AdvDialView)findViewById(R.id.advceView);
        Display thisDisplay = this.getWindowManager().getDefaultDisplay();
        Point desiredSize = new Point();
        thisDisplay.getSize(desiredSize);
        int width = desiredSize.x;
        int height = desiredSize.y;
        mAdvDialView.initSize(width, height);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_dial, menu);
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
}
