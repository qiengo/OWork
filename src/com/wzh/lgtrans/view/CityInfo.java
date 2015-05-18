package com.wzh.lgtrans.view;

import java.io.Serializable;

public class CityInfo implements Serializable {

	private static final long serialVersionUID = -6731710204306064796L;
	private String id = null;
	private String name = null;

	public CityInfo() {
	}

	public CityInfo(String id, String name) {
		setId(id);
		setName(name);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isNull() {
		if (id == null && name == null) {
			return true;
		}
		return false;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void clone(CityInfo city) {
		setId(city.id);
		setName(city.name);
	}

	@Override
	public String toString() {
		return "Cityinfo [id=" + id + ", name=" + name + "]";
	}

}
