package org.medankulinar.maps;

import org.medankulinar.DataView;
import org.medankulinar.MixView;
import org.medankulinar.R;
import org.medankulinar.R.id;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MapRoutingActivity extends Activity implements RoutingListener {
	
	private GoogleMap map;
	private MapFragment mapFragment;
	private DataView dataView;
	private org.medankulinar.event.api.Location poi;
	private LatLng startPoint;
	private LatLng endPoint;
	private Polyline polyline;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_routing);
		
		mapFragment = (MapFragment) getFragmentManager().findFragmentById(
				id.map);
		map = mapFragment.getMap();
		dataView = MixView.getDataView();
		
		setStartPoint();
		
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		poi = (org.medankulinar.event.api.Location) bundle.getParcelable("poi");
		
		endPoint = new LatLng(poi.getLatitude(), poi.getLongitude());
		
		Routing routing = new Routing.Builder()
				.travelMode(AbstractRouting.TravelMode.DRIVING)
				.withListener(this).waypoints(startPoint, endPoint)
				.build();
		routing.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map_routing, menu);
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
	
	public void setStartPoint() {
		Location location = dataView.getContext().getLocationFinder()
				.getCurrentLocation();

		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		startPoint = new LatLng(latitude, longitude);

		Marker myLocation = map.addMarker(new MarkerOptions().position(
				new LatLng(latitude, longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue)).title("Here I am"));
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(
				myLocation.getPosition(), 15));

		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
	}

	@Override
	public void onRoutingFailure() {
		// TODO Auto-generated method stub
		Toast.makeText(this,"Something went wrong, Try again", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRoutingStart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRoutingSuccess(PolylineOptions mPolyOptions, Route route) {
		// TODO Auto-generated method stub
		
		CameraUpdate center = CameraUpdateFactory.newLatLng(startPoint);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

        map.moveCamera(center);
        
      //adds route to the map.
        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color(getResources().getColor(R.color.routing_line));
        polyOptions.width(10);
        polyOptions.addAll(mPolyOptions.getPoints());
        polyline=map.addPolyline(polyOptions);

        // Start marker
        MarkerOptions options = new MarkerOptions();
        /*options.position(startPoint);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
        map.addMarker(options);*/

        // End marker
        options = new MarkerOptions();
        options.position(endPoint);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
        map.addMarker(options);
	}

	@Override
	public void onRoutingCancelled() {
		// TODO Auto-generated method stub

	}
}
