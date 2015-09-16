package org.medankulinar.event.api;

import java.util.List;

public class Poi {
	private int id;
	private String tgl;
	private String title;
	private String descrip;
	private String kategori;
	private String address;
	private long lat;
	private long lon;
	private String img;
	private String phone;
	private String mail;
	private String web;

	public Poi() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTgl() {
		return tgl;
	}

	public void setTgl(String tgl) {
		this.tgl = tgl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return descrip;
	}

	public void setescription(String descrip) {
		this.descrip = descrip;
	}

	public String getKategori() {
		return kategori;
	}

	public void setKategori(String kategori) {
		this.kategori = kategori;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getLat() {
		return lat;
	}

	public void setLat(long lat) {
		this.lat = lat;
	}

	public long getLon() {
		return lon;
	}

	public void setLon(long lon) {
		this.lon = lon;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}
	
	public class ListPoi{
		public List<Poi> listPoi;
		
		public ListPoi() {
		}
	}
}
