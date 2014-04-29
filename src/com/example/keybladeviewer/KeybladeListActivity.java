package com.example.keybladeviewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class KeybladeListActivity extends ActionBarActivity {
	
	Keyblade[] keyblades;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keyblade_list);
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
	
	private class DownloadKeybladeJSON extends AsyncTask<Integer, Void, Keyblade[]> {

		@Override
		protected Keyblade[] doInBackground(Integer... params) {
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
				keyblades = new Keyblade[keybladeArray.length()];
				
				for(int i = 0; i < keyblades.length; i++) {
					keyblades[i] = new Keyblade();
					JSONObject temp = (JSONObject)keybladeArray.get(i);
					keyblades[i].name = temp.getString("name");
					keyblades[i].strength = temp.getString("strength");
					keyblades[i].ability = temp.getString("ability");
					keyblades[i].magic = temp.getString("magic");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return keyblades;
		}
		
		@Override
		protected void onPostExecute(Keyblade[] result) {
		}
    }
	
	public class KeybladeAdapter extends ArrayAdapter<Keyblade> {

		public KeybladeAdapter(Context context, int resource,
				int textViewResourceId, Keyblade[] objects) {
			super(context, resource, textViewResourceId, keyblades);
			// TODO Auto-generated constructor stub
		}
		
	}
    
    public class Keyblade {
    	String name = "";
    	String strength = "";
    	String magic = "";
    	String ability = "";
    }

}
