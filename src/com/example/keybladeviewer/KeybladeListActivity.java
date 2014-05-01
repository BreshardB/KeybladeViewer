package com.example.keybladeviewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class KeybladeListActivity extends ListActivity {
	
	Keyblade[] keyblades;
	
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TypedArray pics = getResources().obtainTypedArray(R.array.keyblade_pics);
		Drawable[] drawables = new Drawable[pics.length()];	 

		for (int i = 0; i < pics.length(); i++) {
			drawables[i] = pics.getDrawable(i);
		}
		
		pics.recycle();
		
		/*Topping [] tops = new Topping[toppings.length];
		
		for(int i = 0; i < toppings.length; i++) {
			tops[i] = new Topping(toppings[i], drawables[i]);
			
		}
		
		ToppingAdapter adapter = new ToppingAdapter(this, tops);
		
		setListAdapter(adapter);*/
		
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
	
	private class DownloadKeybladeJSON extends AsyncTask<Integer, Void, Keyblade[]> {

		@Override
		protected Keyblade[] doInBackground(Integer... params) {
			Keyblade[] temp_keys = null;
			BufferedReader br;
			InputStream is; 
			try {
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
		}
    }
	
	public class KeybladeAdapter extends ArrayAdapter<Keyblade> {

		public KeybladeAdapter(Context context, Keyblade[] objects) {
			super(context, R.layout.keyblade_item, R.id.name, keyblades);
			
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View v = super.getView(position, convertView, parent);
		    TextView textView = (TextView) v.findViewById(R.id.name);
		    textView.setText(keyblades[position].name);

		    return v;
		}
		
	}
	
	public class Keyblade {
    	String name = "";
    	String strength = "";
    	String magic = "";
    	String ability = "";
    	Drawable pic = null;
    }
}
    
    

