package com.example.keybladeviewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {
	
	JSONArray keyblades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        new DownloadKeybladeJSON().execute(Integer.valueOf(R.raw.keyblades));
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private class DownloadKeybladeJSON extends AsyncTask<Integer, Void, JSONArray> {

		@Override
		protected JSONArray doInBackground(Integer... params) {
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
				
				JSONObject keyblade = new JSONObject(json);
				JSONArray keybladeArray = (JSONArray)keyblade.getJSONArray("keyblades");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}
    }
}
