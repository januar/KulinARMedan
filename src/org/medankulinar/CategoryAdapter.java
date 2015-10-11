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
import org.medankulinar.event.api.Category;

import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryAdapter extends ArrayAdapter<Category> {
	private Context context;
	private int layoutResID;
	private List<Category> itemList;
	private View view;
	
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
	
	public static class ItemHolder{
		TextView txt_category;
		ImageView img_category;
	}
}
