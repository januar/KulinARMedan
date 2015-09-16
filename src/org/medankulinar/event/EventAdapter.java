package org.medankulinar.event;

import java.util.List;

import org.medankulinar.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EventAdapter extends BaseAdapter {
	private Context mContext;
	private List<Event> mlistinfo;

	public EventAdapter(Context c, List<Event> l) {
		mContext = c;
		mlistinfo = l;

	}

	public int getCount() {
		return mlistinfo.size();
	}

	public Object getItem(int pos) {
		return mlistinfo.get(pos);
	}

	public long getItemId(int pos) {
		return pos;
	}

	public View getView(int pos, View convertView, ViewGroup parent) {
		Event event = mlistinfo.get(pos);
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.row_event, null);
		}

		// set name
		TextView tvJudul = (TextView) convertView.findViewById(R.id.tvJudul);
		tvJudul.setText(event.getJudul());

		// set almt
		TextView tvGambar = (TextView) convertView.findViewById(R.id.tvLokasi);
		tvGambar.setText(event.getGambar());
		// set almt
		TextView tvWaktu = (TextView) convertView.findViewById(R.id.tvWaktu);
		tvWaktu.setText(event.getTanggal()+ " jam "+event.getWaktu());

		// set web
		TextView tvID = (TextView) convertView.findViewById(R.id.tveventID);
		tvID.setText(event.getEventID());

		return convertView;
	}
}
