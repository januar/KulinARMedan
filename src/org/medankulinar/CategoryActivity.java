package org.medankulinar;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.medankulinar.R.id;
import org.medankulinar.event.ApiRequest;
import org.medankulinar.event.AppConfig;
import org.medankulinar.event.ListPoiActivity;
import org.medankulinar.event.LocationDetailActivity;
import org.medankulinar.event.api.Category;
import org.medankulinar.event.api.Location;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CategoryActivity extends AppCompatActivity {
	
	CategoryAdapter adapter;
	List<Category> dataList;
	ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		setupActionBar();
		
		dataList = new ArrayList<Category>();
		adapter = new CategoryAdapter(this, R.layout.category_item, dataList);
		listView = (ListView) findViewById(R.id.list_category);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Category entity = adapter.getItem(position);
				Intent intent = new Intent(CategoryActivity.this, MixView.class);
				Bundle bundle = new Bundle();
				bundle.putInt("id_category", entity.getIdCategory());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		new RestApi().execute();
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		ActionBar bar = getSupportActionBar();
		int change = bar.getDisplayOptions() ^ ActionBar.DISPLAY_SHOW_HOME;
        bar.setDisplayOptions(change, ActionBar.DISPLAY_SHOW_HOME);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category, menu);
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
	
	public class CategoryAdapter extends ArrayAdapter<Category>{
		Context context;
		int layoutResID;
		List<Category> itemList;
		View view;
		
		public CategoryAdapter(Context context, int resource,
				List<Category> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
			this.context = context;
			this.layoutResID = resource;
			this.itemList = objects;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ItemHolder drawerHolder;
			view = convertView;

			if (view == null) {
				LayoutInflater inflater = ((Activity) context).getLayoutInflater();
				drawerHolder = new ItemHolder();
				view = inflater.inflate(layoutResID, parent, false);
				
				drawerHolder.txt_category = (TextView)view.findViewById(id.txt_category);
				drawerHolder.img_category = (ImageView)view.findViewById(id.img_category);
				view.setTag(drawerHolder);
			} else {
				drawerHolder = (ItemHolder) view.getTag();
			}

			Category item = (Category) this.itemList.get(position);
			
			drawerHolder.txt_category.setText(item.getCategory());
			
			if (item.getImage() != null) {
				InputStream stream = null;
				try {
					stream = new ByteArrayInputStream(Base64.decode(item.getImage().getBytes("UTF-8"), Base64.DEFAULT));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Bitmap decodeByte = BitmapFactory.decodeStream(stream);
				drawerHolder.img_category.setImageBitmap(decodeByte);
			}
			
			return view;
		}
		
	}
	
	private class RestApi extends AsyncTask<String, String, Boolean> {
		private ProgressBar progressBar;
		private List<Category> dataList;

		public RestApi() {
			this.progressBar = (ProgressBar) findViewById(R.id.category_progressbar);
		}

		@Override
		protected Boolean doInBackground(String... args) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String response = ApiRequest.makeHttpRequest(AppConfig.GET_CATEGORY,
					ApiRequest.GET, params);
			if (response.equals("")) {
				return false;
			} else {
				Gson gson = new Gson();
				try {
					Category[] result = gson.fromJson(response, Category[].class);
					dataList = Arrays.asList(result);
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
			progressBar.setVisibility(View.VISIBLE);
			listView.setVisibility(View.INVISIBLE);
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
			progressBar.setVisibility(View.INVISIBLE);
			listView.setVisibility(View.VISIBLE);
		}
	}
	
	private static class ItemHolder{
		TextView txt_category;
		ImageView img_category;
	}
}
