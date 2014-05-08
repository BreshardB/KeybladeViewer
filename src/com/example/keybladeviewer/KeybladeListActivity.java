package com.example.keybladeviewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class KeybladeListActivity extends ListActivity {

	Keyblade[] keyblades;
	
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Get intent from clicking on list item
		Intent receive = getIntent();
		
		new DownloadKeybladeJSON().execute(Integer.valueOf(R.raw.keyblades));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.keyblade_list, menu);
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
	
	public void setKeybladeArray(Keyblade[] result) {
		keyblades = result;
	}
	
	private class DownloadKeybladeJSON extends AsyncTask<Integer, Void, Keyblade[]> implements OnItemClickListener {

		@Override
		protected Keyblade[] doInBackground(Integer... params) {
			Keyblade[] temp_keys = null;
			BufferedReader br;
			InputStream is; 
			try {
				//Parse JSON file from res/raw
				is = getResources().openRawResource(params[0].intValue());
				br = new BufferedReader(new InputStreamReader(is));
				String json = "";
				String buffer = null;
				while((buffer = br.readLine()) != null) {
					json += buffer;
				}
				br.close();
				
				JSONObject keybladeJSON = new JSONObject(json);
				JSONArray keybladeArray = (JSONArray)keybladeJSON.getJSONArray("keyblades");
				temp_keys = new Keyblade[keybladeArray.length()];
				
				//Assign array of Keyblade objects necessary information from JSON array
				for(int i = 0; i < temp_keys.length; i++) {
					temp_keys[i] = new Keyblade();
					JSONObject temp = (JSONObject)keybladeArray.get(i);
					temp_keys[i].name = temp.getString("name");
					temp_keys[i].strength = temp.getString("strength");
					temp_keys[i].ability = temp.getString("ability");
					temp_keys[i].magic = temp.getString("magic");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return temp_keys;
			
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			keyblades = null;
		}
		
		@Override
		protected void onPostExecute(Keyblade[] result) {
			super.onPostExecute(result);
			keyblades = new Keyblade[result.length];
			for(int i = 0; i < keyblades.length; i++) {
				keyblades[i] = result[i];
			}
			KeybladeAdapter adapter = new KeybladeAdapter(context, keyblades);
			
			setListAdapter(adapter);
			
			getListView().setOnItemClickListener(this);
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(KeybladeListActivity.this, KeybladeStatView.class);
			
			intent.putExtra("Keyblade", keyblades[position]);
			intent.putExtra("position", position);
			
			startActivity(intent);
			//Activates slide animation
			//Code gathered from here:
			//http://stackoverflow.com/questions/5151591/android-left-to-right-slide-animation
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		}
    }
	
	public class KeybladeAdapter extends ArrayAdapter<Keyblade> {
		
		TypedArray icons;
		Drawable [] drawables;

		public KeybladeAdapter(Context context, Keyblade[] objects) {
			super(context, R.layout.keyblade_item, R.id.name, keyblades);
			
			icons = getResources().obtainTypedArray(R.array.keychain_icons);
			drawables = new Drawable[icons.length()];	 

			for (int i = 0; i < icons.length(); i++) {
				drawables[i] = icons.getDrawable(i);
			}
			
			icons.recycle();
			
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View v = super.getView(position, convertView, parent);
		    TextView textView = (TextView) v.findViewById(R.id.name);
		    textView.setText(keyblades[position].name);
		    textView.setCompoundDrawablesWithIntrinsicBounds(drawables[position], null, null, null);

		    return v;
		}
		
	}
    	
}
    
    

