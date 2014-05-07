package com.example.keybladeviewer;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class KeybladeStatView extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keyblade_stat_view);

		Intent intent = getIntent();
	    String clickedKeyblade = intent.getStringExtra("Clicked");

        
        TypedArray pics = getResources().obtainTypedArray(R.array.keyblade_pics);
		Drawable[] drawables = new Drawable[pics.length()];	 

		for (int i = 0; i < pics.length(); i++) {
			drawables[i] = pics.getDrawable(i);
		}
		
		pics.recycle();
		
		ImageView image = (ImageView)findViewById(R.id.image);
		image.setImageDrawable(drawables[0]);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.keyblade_stat_view, menu);
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
