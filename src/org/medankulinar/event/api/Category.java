package org.medankulinar.event.api;

public class Category {
	
	private int id_category;
	private String category;
	private String image;

	public Category() {
		// TODO Auto-generated constructor stub
	}
	
	public void setIdCategory(int id_category) {
		this.id_category = id_category;
	}
	public int getIdCategory()
	{
		return this.id_category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategory() {
		return this.category;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	public String getImage() {
		return this.image;
	}

}
