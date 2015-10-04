package org.medankulinar.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.medankulinar.R;
import org.medankulinar.R.layout;
import org.medankulinar.event.api.Location;
import org.medankulinar.event.api.Location.LocationResult;
import com.google.gson.Gson;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ListPoiActivity extends AppCompatActivity {

	LocationAdapter adapter;
	List<Location> dataList;
	ListView listView;
	public View footer_view;
	private int id_category;
	private int offset;
	private int count_data;
	private boolean is_load;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_poi);
		setupActionBar();
		
		dataList = new ArrayList<Location>();
		adapter = new LocationAdapter(this, R.layout.location_view, dataList);
		listView = (ListView) findViewById(R.id.list_location);
		listView.setAdapter(adapter);
		
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		id_category = bundle.getInt("id_category", 1);
		offset = 0;
		is_load = false;
		
		footer_view = this.getLayoutInflater().inflate(layout.footer_view, null);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Location entity = adapter.getItem(position);
				Intent intent = new Intent(ListPoiActivity.this, LocationDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putParcelable("poi", entity);
				bundle.putString("action", "fromList");
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		listView.setOnScrollListener(new AbsListView.OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if((totalItemCount - visibleItemCount) == firstVisibleItem && totalItemCount > 1){
					if (!is_load) {
						if((offset * 5) < count_data){
							listView.addFooterView(footer_view);
							new RestApi(false).execute(Integer.toString(id_category), Integer.toString(offset));
							offset++;
							is_load = true;
						}
					}
				}
			}
		});
		
		
		new RestApi(true).execute(Integer.toString(id_category), Integer.toString(offset));
		offset++;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_poi, menu);
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

	private class RestApi extends AsyncTask<String, String, Boolean> {
		private ProgressBar progressBar;
		private List<Location> dataList;
		private boolean first;

		public RestApi(boolean first) {
			this.progressBar = (ProgressBar) findViewById(R.id.inbox_progressbar);
			this.first = first;
		}

		@Override
		protected Boolean doInBackground(String... args) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id_category", args[0]));
			params.add(new BasicNameValuePair("offset", args[1]));
			String response = ApiRequest.makeHttpRequest(AppConfig.GET_LIST,
					ApiRequest.GET, params);
			if (response.equals("")) {
				return false;
			} else {
				Gson gson = new Gson();
				try {
					LocationResult result = gson.fromJson(response, LocationResult.class);
					count_data = result.count;
					//Location[] listLocation = gson.fromJson(result.data, Location[].class);
					dataList = Arrays.asList(result.data);
				} catch (Exception e) {
					ApiRequest.LOG = "Bad request";
					Log.e("RestApi", response);
					Log.e("RestApi", e.getMessage());
					return false;
				}
			}
			return true;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (first) {
				progressBar.setVisibility(View.VISIBLE);
				listView.setVisibility(View.INVISIBLE);
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result) {
				adapter.addAll(dataList);
				adapter.notifyDataSetChanged();
			}else{
				Toast.makeText(getApplication(), ApiRequest.LOG, Toast.LENGTH_SHORT).show();
			}
			
			if (first) {
				listView.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.INVISIBLE);
			}else{
				listView.removeFooterView(footer_view);
				is_load = false;
			}
		}
	}
}
