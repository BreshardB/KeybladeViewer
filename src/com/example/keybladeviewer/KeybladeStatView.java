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
import android.widget.TextView;

public class KeybladeStatView extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keyblade_stat_view);

		Intent intent = getIntent();
		Keyblade intendedKey = (Keyblade)intent.getParcelableExtra("Keyblade");
		int position = intent.getIntExtra("position", 0);
		
	    String name = intendedKey.name;
	    String strength = intendedKey.strength;
	    String magic = intendedKey.magic;
	    String ability = intendedKey.ability;
		
		Log.d("BJB", intendedKey.name);
	    
        
        TypedArray pics = getResources().obtainTypedArray(R.array.keyblade_pics);
        TypedArray icons = getResources().obtainTypedArray(R.array.keychain_large_icons);
		Drawable[] drawables = new Drawable[pics.length()];	
		Drawable[] keychains = new Drawable[icons.length()];

		for (int i = 0; i < pics.length(); i++) {
			drawables[i] = pics.getDrawable(i);
			keychains[i] = icons.getDrawable(i);
		}
		
		pics.recycle();
		icons.recycle();
		
		ImageView mImage = (ImageView)findViewById(R.id.image);
		TextView mName = (TextView)findViewById(R.id.name);
		TextView mStrength = (TextView)findViewById(R.id.strength);
		TextView mMagic = (TextView)findViewById(R.id.magic);
		TextView mAbility = (TextView)findViewById(R.id.ability);
		ImageView mKeychain = (ImageView)findViewById(R.id.keychain);
		
		mImage.setImageDrawable(drawables[position]);
		mName.setText(name);
		mStrength.setText(strength);
		mMagic.setText(magic);
		mAbility.setText(ability);
		mKeychain.setImageDrawable(keychains[position]);
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
