package org.medankulinar.event;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.medankulinar.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ListEventActivity extends Activity {

	private ListView lvEvent;
	
	ApiRequest jParser;
	// Progress Dialog
	private ProgressDialog pDialog;
	List<Event> listevent;
	JSONArray arrayevent;
	private static final String VIEW_EVENT_URL = AppConfig.SERVER
			+ "json_event.php";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_event_activity);

		lvEvent = (ListView) findViewById(R.id.listevent);
		listevent = new ArrayList<Event>();
		listevent.clear();
		jParser = new ApiRequest();

	}
	
	private void updateList() {

		
		EventAdapter adapter = new EventAdapter(this, listevent);
		lvEvent.setAdapter(adapter);
		// to do nothing...

		lvEvent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// getting values from selected ListItem
				String id_artikel = ((TextView) view.findViewById(R.id.tveventID))
						.getText().toString();

				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						EventDetailActivity.class);
				// sending idnama_kasus to next activity
				in.putExtra("id_artikel", id_artikel);

				// starting new activity and expecting some response back
				startActivityForResult(in, 100);

			}
		});

	}

	public void updateJSONdata() {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String json = ApiRequest.makeHttpRequest(VIEW_EVENT_URL, ApiRequest.GET, params);
		Log.d("JSON", json.toString());
		// when parsing JSON stuff, we should probably
		// try to catch any exceptions:
		try {

			// I know I said we would check if "Posts were Avail." (success==1)
			// before we tried to read the individual posts, but I lied...
			// mComments will tell us how many "posts" or comments are
			// available
			//arrayevent = json.getJSONArray("list_event");

			// looping through all posts according to the json object returned
			for (int i = 0; i < arrayevent.length(); i++) {
				JSONObject event = arrayevent.getJSONObject(i);
				listevent.add(new
				 Event(BitmapFactory.decodeResource(getResources(),
				 R.drawable.logo), 
				 event.getString("id_artikel"), 
				 event.getString("judul"), 
				 event.getString("gambar"),
				 event.getString("tanggal"),
				 event.getString("waktu"),
				 event.getString("waktu"))); // end of add
						 
					
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// loading the comments via AsyncTask
		listevent.clear();
		new Loadevent().execute();
	}

	public class Loadevent extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ListEventActivity.this);
			pDialog.setMessage("Loading data event...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
			pDialog.dismiss();
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			 updateJSONdata();
			return null;

		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pDialog.dismiss();
			updateList();
		}

	}
}