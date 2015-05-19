package com.wzh.lgtrans.struct;

import java.io.Serializable;

public class IdName implements Serializable {

	private static final long serialVersionUID = -6731710204306064796L;
	private String id = null;
	private String name = null;

	public IdName() {
	}

	public IdName(String id, String name) {
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

	public void clone(IdName idName) {
		setId(idName.id);
		setName(idName.name);
	}

	@Override
	public String toString() {
		return "IdName [id=" + id + ", name=" + name + "]";
	}

}
