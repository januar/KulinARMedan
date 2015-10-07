package org.medankulinar.event;

import android.graphics.Bitmap;

public class Event {

	private String id_artikel;
	private String judul;
	private String gambar;
	private String tanggal;
	private String waktu;

	public Event(Bitmap ic,String eid, String j, String g, String tgl,
			String jm, String lok) {
		id_artikel = eid;
		judul = j;
		gambar = g;
		tanggal = tgl;
		waktu = jm;

	}

	public String getEventID() {
		return id_artikel;
	}

	public void setEventID(String eventID) {
		this.id_artikel = eventID;
	}

	public String getJudul() {
		return judul;
	}

	public void setJudul(String judul) {
		this.judul = judul;
	}
	
	public String getGambar() {
		return gambar;
	}

	public void setGambar(String gambar) {
		this.gambar = gambar;
	}

	public String getTanggal() {
		return tanggal;
	}

	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
	}

	public String getWaktu() {
		return waktu;
	}

	public void setWaktu(String waktu) {
		this.waktu = waktu;
	}

}
