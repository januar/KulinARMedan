package org.medankulinar.event.api;

import java.io.Serializable;

import android.graphics.Bitmap;

@SuppressWarnings("serial")
public class Location implements Serializable{

	private int id_location;
	private int id_category;
	private String category;
	private String name;
	private Float longitude;
	private Float latitude;
	private String description;
	private String address;
	private String image_url;
	private Bitmap image;
	
	public Location() {
		// TODO Auto-generated constructor stub
	}
	
	public void setId(int id) {
		this.id_location = id;
	}
	public int getId() {
		return this.id_location;
	}

	public void setIdCategory(int id) {
		this.id_category = id;
	}
	public int getIdCategory() {
		return this.id_category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategory() {
		return this.category;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public Float getLongitude() {
		return this.longitude;
	}
	
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public Float getLatitude() {
		return this.latitude;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return this.description;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return this.address;
	}
	
	public void setImageUrl(String image_url) {
		this.image_url = image_url;
	}
	public String getImageUrl() {
		return this.image_url;
	}
	
	public void setImage(Bitmap image) {
		this.image = image;
	}
	public Bitmap getImage() {
		return this.image;
	}
}
