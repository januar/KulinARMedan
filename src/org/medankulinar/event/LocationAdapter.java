package org.medankulinar.event;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.medankulinar.R.id;
import org.medankulinar.event.api.Location;
import org.medankulinar.event.api.Poi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LocationAdapter extends ArrayAdapter<Location> {
	Context context;
	int layoutResID;
	List<Location> itemList;

	public LocationAdapter(Context context, int resource, List<Location> objects) {
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
		View view = convertView;

		if (view == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			drawerHolder = new ItemHolder();
			view = inflater.inflate(layoutResID, parent, false);
			
			drawerHolder.txt_location = (TextView)view.findViewById(id.txt_location_name);
			drawerHolder.img_location = (ImageView)view.findViewById(id.location_image);
			drawerHolder.txt_address = (TextView)view.findViewById(id.txt_address);
			drawerHolder.txt_kategori = (TextView)view.findViewById(id.txt_kategori);
			drawerHolder.progress_layout = (LinearLayout)view.findViewById(id.progress_image_layout);
			view.setTag(drawerHolder);
		} else {
			drawerHolder = (ItemHolder) view.getTag();
		}

		Location item = (Location) this.itemList.get(position);
		
		drawerHolder.txt_location.setText(item.getName());
		drawerHolder.txt_address.setText(item.getAddress());
		drawerHolder.txt_kategori.setText("Kategori : " + item.getCategory());
		
		if (item.getImageUrl() != null) {
			if(item.getImageUrl().trim() != "")
				new ImageAsyncTask(drawerHolder, item).execute(item.getImageUrl().trim());
		}
		
		/*if (item.getImg() != null) {
			InputStream stream = null;
			try {
				stream = new ByteArrayInputStream(Base64.decode(item.getImg().getBytes("UTF-8"), Base64.DEFAULT));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Bitmap decodeByte = BitmapFactory.decodeStream(stream);
			drawerHolder.img_location.setImageBitmap(decodeByte);
		}*/
		
		return view;
	}
	
	private class ImageAsyncTask extends AsyncTask<String, String, Boolean>
	{
		private ItemHolder drawer;
		private Location location;
		
		public ImageAsyncTask(ItemHolder drawer, Location location){
			this.drawer = drawer;
			this.location = location;
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urldisplay = params[0];
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            if (in != null) {
					location.setImage(BitmapFactory.decodeStream(in));
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
			drawer.img_location.setVisibility(View.INVISIBLE);
			drawer.progress_layout.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result) {
				drawer.img_location.setImageBitmap(location.getImage());
			}
			
			drawer.img_location.setVisibility(View.VISIBLE);
			drawer.progress_layout.setVisibility(View.INVISIBLE);
		}
	}
	
	private static class ItemHolder{
		TextView txt_location;
		ImageView img_location;
		TextView txt_address;
		TextView txt_kategori;
		LinearLayout progress_layout;
	}
}
