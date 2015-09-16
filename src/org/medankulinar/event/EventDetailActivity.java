package org.medankulinar.event;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.medankulinar.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

@SuppressLint("NewApi")
public class EventDetailActivity extends Activity {

	String event;
	JSONObject dataevent;
	// TextView tampil;

	String id_artikel;

	WebView tampil;

	// Progress Dialog
	private ProgressDialog pDialog;

	// single event url
	private static final String url_details = AppConfig.SERVER
			+ "json_event_detail.php";

	private static final String TAG_eventID = "id_artikel";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_detail);

		tampil = (WebView) findViewById(R.id.webView1);
		// getting event details from intent
		Intent i = getIntent();

		// getting event id (idievent) from intent
		id_artikel = i.getStringExtra(TAG_eventID);

		// Getting complete event details in background thread
		new GetEventDetail().execute();

	}

	/**
	 * Background Async Task to Get complete event details
	 * */
	class GetEventDetail extends AsyncTask<String, String, String> {
		String html;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EventDetailActivity.this);
			pDialog.setMessage("Loading event details. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Getting event details in background thread
		 * */
		protected String doInBackground(String... params) {

			// Building Parameters
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("id_artikel", id_artikel));

			// getting event details by making HTTP request
			// Note that event details url will use GET request
			String json = ApiRequest.makeHttpRequest(url_details,
					ApiRequest.GET, params1);

			// check your log for json response
			Log.d("Single event Details", json.toString());

			if (json.length() > 0) {
				// successfully received event details
				// JSONArray eventObject = json.getJSONArray("list_event"); //
				// JSON

				// get first event object from JSON Array
				// dataevent = eventObject.getJSONObject(0);

				Log.d("event", dataevent.toString());

				try {
					// build html parser
					// * cek status

					// judul=event.getString("judul");
					html = "<h2>" + dataevent.getString("judul") + "</h2>"
							+ "<img src='http://10.0.2.2/webservice/list/img/"
							+ dataevent.getString("gambar") + "'/>"
							+ "<p> Tanggal: " + dataevent.getString("tanggal")
							+ "</br>jam" + dataevent.getString("waktu");

					Log.d("HTML", html);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // Array

			} else {
				// event with idievent not found
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String filename) {
			// dismiss the dialog once got all details
			pDialog.dismiss();
			tampil.loadData(html, "text/html", "UTF-8");
			// tampil.setText(html));

		}
	}

}
