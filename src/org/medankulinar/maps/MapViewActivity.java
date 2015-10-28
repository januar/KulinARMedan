package org.medankulinar.maps;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.medankulinar.DataView;
import org.medankulinar.MixView;
import org.medankulinar.R;
import org.medankulinar.R.id;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Base64;

public class MapViewActivity extends Activity{
	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);

	private GoogleMap map;
	private MapFragment mapFragment;
	private DataView dataView;
	private List<org.mixare.lib.marker.Marker> markerList;
	private String image;

	LatLng startPoint;
	List<LatLng> listGeoPoint;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_view);

		mapFragment = (MapFragment) getFragmentManager().findFragmentById(
				id.map);
		map = mapFragment.getMap();
		
		Intent intent = getIntent();
		image = intent.getStringExtra("image");

		dataView = MixView.getDataView();
		markerList = dataView.getDataHandler().getMarkerList();

		setStartPoint();
		createOverlay();
	}

	public void setStartPoint() {
		Location location = dataView.getContext().getLocationFinder()
				.getCurrentLocation();

		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		startPoint = new LatLng(latitude, longitude);

		Marker myLocation = map.addMarker(new MarkerOptions().position(
				new LatLng(latitude, longitude)).title("Here I am"));
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(
				myLocation.getPosition(), 15));

		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	}

	public void createOverlay() {

		listGeoPoint = new ArrayList<LatLng>();
		BitmapDescriptor icon = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_map);
		if(!image.equals("")){
			InputStream stream = null;
			try {
				stream = new ByteArrayInputStream(Base64.decode(image.getBytes("UTF-8"), Base64.DEFAULT));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Bitmap decodeByte = BitmapFactory.decodeStream(stream);
			icon = BitmapDescriptorFactory.fromBitmap(decodeByte);
		}
		
		for (org.mixare.lib.marker.Marker marker : markerList) {
			if (marker.isActive()) {
				LatLng point = new LatLng(marker.getLatitude(),
						marker.getLongitude());
				listGeoPoint.add(point);
				Marker mk = map.addMarker(new MarkerOptions()
						.position(
								new LatLng(marker.getLatitude(), marker
										.getLongitude()))
						.title(marker.getTitle())
						.icon(icon));
			}
		}
	}
}
