package org.medankulinar.event.api;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

public class Location implements Parcelable{

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
	
	public Location(Parcel in)
	{
		String[] dataString = new String[6];
		in.readStringArray(dataString);
		this.category = dataString[0];
		this.name = dataString[1];
		this.description = dataString[2];
		this.address = dataString[3];
		this.image_url = dataString[4];
		this.image = decodeBase64(dataString[5]);
		
		float[] dataFloat = new float[2];
		in.readFloatArray(dataFloat);
		this.longitude = dataFloat[0];
		this.latitude = dataFloat[1];
		
		int[] dataInt = new int[2];
		in.writeIntArray(dataInt);
		this.id_location = dataInt[0];
		this.id_category = dataInt[1];
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
	
	public String encodeToBase64()
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		this.image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	    byte[] b = baos.toByteArray();
	    String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
	    
	    return imageEncoded;
	}
	
	public Bitmap decodeBase64(String input) 
	{
	    byte[] decodedByte = Base64.decode(input, Base64.DEFAULT);
	    return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length); 
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeStringArray(new String[]{this.category, this.name, this.description, this.address, this.image_url, this.encodeToBase64()});
		dest.writeFloatArray(new float[] {this.longitude, this.latitude});
		dest.writeIntArray(new int[]{this.id_location, this.id_category});
//		dest.writeValue(this.image);
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
    
    
    public class LocationResult{
    	public int count;
    	public Location[] data;
    }
}
