package org.medankulinar.maps;

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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

public class MapViewActivity extends Activity{
	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);

	private GoogleMap map;
	private MapFragment mapFragment;
	private DataView dataView;
	private List<org.mixare.lib.marker.Marker> markerList;

	LatLng startPoint;
	List<LatLng> listGeoPoint;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_view);

		mapFragment = (MapFragment) getFragmentManager().findFragmentById(
				id.map);
		map = mapFragment.getMap();

		if (map != null) {
			Marker hamburg = map.addMarker(new MarkerOptions()
					.position(HAMBURG).title("Hamburg"));
			Marker kiel = map.addMarker(new MarkerOptions()
					.position(KIEL)
					.title("Kiel")
					.snippet("Kiel is cool")
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.icon_map)));
		}

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
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.icon_map)));
			}
		}
	}
}
