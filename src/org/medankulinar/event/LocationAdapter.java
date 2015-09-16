package org.medankulinar.event;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.medankulinar.R.id;
import org.medankulinar.event.api.Poi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationAdapter extends ArrayAdapter<Poi> {
	Context context;
	int layoutResID;
	List<Poi> itemList;

	public LocationAdapter(Context context, int resource, List<Poi> objects) {
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
			view.setTag(drawerHolder);
		} else {
			drawerHolder = (ItemHolder) view.getTag();
		}

		Poi item = (Poi) this.itemList.get(position);
		
		drawerHolder.txt_location.setText(item.getTitle());
		drawerHolder.txt_address.setText(item.getAddress());
		drawerHolder.txt_kategori.setText("Kategori : " + item.getKategori());
		
		if (item.getImg() != null) {
			Log.e("image", item.getImg());
			InputStream stream = null;
			try {
				stream = new ByteArrayInputStream(Base64.decode(item.getImg().getBytes("UTF-8"), Base64.DEFAULT));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Bitmap decodeByte = BitmapFactory.decodeStream(stream);
			drawerHolder.img_location.setImageBitmap(decodeByte);
		}
		
		return view;
	}
	
	private static class ItemHolder{
		TextView txt_location;
		ImageView img_location;
		TextView txt_address;
		TextView txt_kategori;
	}
}
