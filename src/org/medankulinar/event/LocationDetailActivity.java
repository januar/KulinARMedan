package org.medankulinar.event;

import org.medankulinar.R;
import org.medankulinar.R.id;
import org.medankulinar.R.layout;
import org.medankulinar.R.menu;
import org.medankulinar.event.api.Poi;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationDetailActivity extends ActionBarActivity {
	
	private Poi poi;
	
	private TextView txt_location;
	private TextView txt_address;
	private TextView txt_kategori;
	private TextView txt_description;
	private ImageView img_location;
	private ImageButton btn_direction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_detail);
		
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		poi = (Poi) bundle.getSerializable("poi");
		
		txt_location = (TextView)findViewById(id.txt_location_name);
		txt_address = (TextView)findViewById(id.txt_address);
		txt_kategori = (TextView)findViewById(id.txt_kategori);
		txt_description = (TextView)findViewById(id.txt_description);
		img_location = (ImageView)findViewById(id.location_image);
		btn_direction = (ImageButton)findViewById(id.btn_direction);
		
		txt_location.setText(poi.getTitle());
		txt_address.setText(poi.getAddress());
		txt_kategori.setText("Kategori : " + poi.getKategori());
		txt_description.setText(poi.getDescription());
		
		if (poi.getImg() != null) {
			byte[] byteString = Base64.decode(poi.getImg(), Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(byteString, 0, byteString.length);
			img_location.setImageBitmap(bitmap);
		}
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
