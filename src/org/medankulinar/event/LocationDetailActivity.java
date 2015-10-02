package org.medankulinar.event;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.medankulinar.R;
import org.medankulinar.R.id;
import org.medankulinar.event.api.Location;
import org.medankulinar.maps.MapRoutingActivity;
import com.google.gson.Gson;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class LocationDetailActivity extends AppCompatActivity {
	
	private Location poi;
	
	private TextView txt_location;
	private TextView txt_address;
	private TextView txt_kategori;
	private TextView txt_description;
	private ImageView img_location;
	private ImageButton btn_direction;
	private LinearLayout progress_layout;
	
	private static String REGEX_HTML = "<[^<>]+>";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_detail);
		
		setupActionBar();
		txt_location = (TextView)findViewById(id.txt_location_name);
		txt_address = (TextView)findViewById(id.txt_address);
		txt_kategori = (TextView)findViewById(id.txt_kategori);
		txt_description = (TextView)findViewById(id.txt_description);
		img_location = (ImageView)findViewById(id.location_image);
		btn_direction = (ImageButton)findViewById(id.btn_direction);
		progress_layout = (LinearLayout)findViewById(id.progress_image_layout);
		
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		
		String action = bundle.getString("action");
		if (action.equals("fromList")) {
			poi = (Location) bundle.getSerializable("poi");
			txt_location.setText(poi.getName());
			txt_address.setText(poi.getAddress());
			txt_kategori.setText("Kategori : " + poi.getCategory());
			txt_description.setText(poi.getDescription().replaceAll(REGEX_HTML, ""));
			
			if (poi.getImage() != null) {
				img_location.setImageBitmap(poi.getImage());
			}
		}else{
			String id = bundle.getString("id");
			new RestApi().execute(id);
		}
		
		btn_direction.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LocationDetailActivity.this, MapRoutingActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("poi", (Serializable) poi);
				bundle.putString("action", "direction");
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		ActionBar bar = getSupportActionBar();
		int change = bar.getDisplayOptions() ^ ActionBar.DISPLAY_HOME_AS_UP;
        bar.setDisplayOptions(change, ActionBar.DISPLAY_HOME_AS_UP);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.location_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case android.R.id.home:
			//NavUtils.navigateUpFromSameTask(this);
			finish();
			return true;
		case R.id.action_settings:
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class RestApi extends AsyncTask<String, String, Boolean>{
		private ScrollView scrollView;
		private ProgressBar progressBar;
		
		public RestApi()
		{
			this.scrollView = (ScrollView)findViewById(id.scrollView1);
			this.progressBar = (ProgressBar)findViewById(id.inbox_progressbar);
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			this.progressBar.setVisibility(View.VISIBLE);
			this.scrollView.setVisibility(View.INVISIBLE);
		}

		@Override
		protected Boolean doInBackground(String... args) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", args[0]));
			String response = ApiRequest.makeHttpRequest(AppConfig.GET_DETAIL,
					ApiRequest.GET, params);
			if (response.equals("")) {
				return false;
			} else {
				Gson gson = new Gson();
				try {
					poi = gson.fromJson(response, Location.class);
				} catch (Exception e) {
					ApiRequest.LOG = "Bad request";
					Log.e("RestApi", e.getMessage());
					return false;
				}
			}
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result) {
				txt_location.setText(poi.getName());
				txt_address.setText(poi.getAddress());
				txt_kategori.setText("Kategori : " + poi.getCategory());
				txt_description.setText(poi.getDescription().replaceAll(REGEX_HTML, ""));
				
				if (poi.getImageUrl() != null) {
					if(poi.getImageUrl().trim() != "")
						new ImageAsyncTask().execute(poi.getImageUrl().trim());
				}
			}else {
				Toast.makeText(getApplication(), ApiRequest.LOG, Toast.LENGTH_SHORT).show();
			}
			
			this.progressBar.setVisibility(View.INVISIBLE);
			this.scrollView.setVisibility(View.VISIBLE);
		}	
	}
	
	private class ImageAsyncTask extends AsyncTask<String, String, Boolean>
	{
		
		public ImageAsyncTask(){
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urldisplay = params[0];
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            if (in != null) {
					poi.setImage(BitmapFactory.decodeStream(in));
				} else {
					return false;
				}
//	            mIcon11 = BitmapFactory.decodeStream(in);
	            
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	            return false;
	        }
			
			return true;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			img_location.setVisibility(View.INVISIBLE);
			progress_layout.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result) {
				img_location.setImageBitmap(poi.getImage());
			}
			
			img_location.setVisibility(View.VISIBLE);
			progress_layout.setVisibility(View.INVISIBLE);
		}
	}
}
