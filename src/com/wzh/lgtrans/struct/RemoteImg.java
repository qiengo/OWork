package com.wzh.lgtrans.struct;

import org.json.JSONObject;


/**
 * 存储URL图片的结构体
 * @author 王植桦 ewangzhihua@yeah.net
 * @version 创建时间：2014年3月10日
 *
 */
public class RemoteImg {

	/**
	 * 图片尺寸
	 */
	private int width,height;
	/**
	 * 图片地址
	 */
	private String url;
	/**
	 * 图片内容，如链接，要跳转的activity等。
	 */
	private String content;
	
	public RemoteImg(){}
	public RemoteImg(JSONObject imgObject){
		fromJsonObject(imgObject);
	}
	public void setWidth(int w){
		width=w;
	}
	public int getWidth(){
		return width;
	}
	public void setHeight(int h){
		height=h;
	}
	public int getHeight(){
		return height;
	}
	public void setUrl(String u){
		url=u;
	}
	public String getUrl(){
		return url;
	}
	public void setContent(String a){
		content=a;
	}
	public String getContent(){
		return content;
	}
	public float getRatio(){
		float ratio=width/(float)height;
		System.out.println("width/(float)height=ratio "+width+"/"+height+"="+ratio);
		return ratio;
	}
	public void fromJsonObject(JSONObject imgObject){
		if(imgObject!=null){
			setWidth(imgObject.optInt("width"));
			setHeight(imgObject.optInt("height"));
			setUrl(imgObject.optString("url"));
			setContent(imgObject.optString("content"));
		}
	}
	
	public String toString(){
		return "width:"+width+"\nheight:"+height
				+"\nurl:"+url+"\ncontent:"+content;
	}
}
