package com.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class ImageModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String type;
	
	@Lob
	@Column(length = 50000000)
	private byte[] imageByte;

	

	public ImageModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ImageModel(Long id, String name, String type, byte[] imageByte) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.imageByte = imageByte;
	}
	

	public ImageModel(String name, String type, byte[] imageByte) {
		super();
		this.name = name;
		this.type = type;
		this.imageByte = imageByte;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getImageByte() {
		return imageByte;
	}

	public void setImageByte(byte[] imageByte) {
		this.imageByte = imageByte;
	}
	
	

}
