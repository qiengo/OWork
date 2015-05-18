package com.wzh.lgtrans.view;

import java.io.Serializable;

public class CityInfo implements Serializable {

	private static final long serialVersionUID = -6731710204306064796L;
	private String id;
	private String name;

	public CityInfo() {}

	public CityInfo(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Cityinfo [id=" + id + ", name=" + name + "]";
	}

}