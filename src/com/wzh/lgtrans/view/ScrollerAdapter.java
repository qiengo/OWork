package com.wzh.lgtrans.view;

import java.util.ArrayList;
import java.util.List;

public abstract class ScrollerAdapter<E> {
	protected List<E> dataList = new ArrayList<E>();
	private boolean isAllEnable = true;
	private E allItem = null;

	public ScrollerAdapter(boolean isAllEnable) {
		this.isAllEnable=isAllEnable;
	}

	public void setData(List<E> list) {
		if (list == null)
			return;
		dataList.clear();
		dataList.addAll(list);
		if (isAllEnable() && allItem != null) {
			if (!dataList.get(0).equals(allItem)) {
				dataList.add(0, allItem);
			}
		}
	}

	public int getSize() {
		if (dataList != null) {
			return dataList.size();
		} else {
			return 0;
		}
	}

	public boolean isAllEnable() {
		return isAllEnable;
	}

	public void setAllEnable(boolean isEnable) {
		this.isAllEnable = isEnable;
	}

	/**
	 * call this before setData
	 * @param object
	 */
	public void setAllItem(E object) {
		allItem = object;
	}

	public List<E> getDataList() {
		return dataList;
	}

	public boolean isAllItem(int i) {
		if (isAllEnable()&&allItem!=null&& i == 0) {
			return true;
		}
		return false;
	}

	public abstract String getContent(int i);
}
