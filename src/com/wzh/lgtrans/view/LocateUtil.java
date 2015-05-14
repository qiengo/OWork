package com.wzh.lgtrans.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wzh.lgtrans.util.FileUtil;

/**
 * 
 * 城市代码
 * 
 * @author wzh
 * 
 */
public class LocateUtil {

	private List<LocateInfo> prov_list = new ArrayList<LocateInfo>();
	private HashMap<String, List<LocateInfo>> city_map = new HashMap<String, List<LocateInfo>>();
	private HashMap<String, List<LocateInfo>> couny_map = new HashMap<String, List<LocateInfo>>();

	private static LocateUtil model;
	private LocateUtil() {}
	
	public static LocateUtil getSingleton() {
		if (null == model) {
			model = new LocateUtil();
		}
		return model;
	}
	
	public void loadData(Context ctx){
		CityParser parser = new CityParser();
		String area_str = FileUtil.readAssets(ctx, "area.json");
		prov_list = parser.getJSONParserResult(area_str, "area0");
		city_map = parser.getJSONParserResultArray(area_str, "area1");
		couny_map = parser.getJSONParserResultArray(area_str, "area2");
	}

	public List<LocateInfo> getProvlist() {
		return prov_list;
	}
	public List<LocateInfo> getCityList(String provCode) {
		return city_map.get(provCode);
	}
	public List<LocateInfo> getCounyList(String cityCode) {
		return couny_map.get(cityCode);
	}
	public void LocCode(String code) {
		if(code.length()==6){
			String provCode=code.substring(0, 2)+"0000";
			System.out.println("======provCode:"+provCode);
			String locProv=null;
			String locCity=null;
			String locCouny=null;
			int prov_count=prov_list.size();
			for(int i=0;i<prov_count;i++){
				LocateInfo locateInfo=prov_list.get(i);
				if(locateInfo.getId().equals(provCode)){
					locProv=locateInfo.getId();
					System.out.println("======provName:"+locateInfo.getName());
					break;
				}
			}
			if(locProv!=null){
				String cityCode=code.substring(0, 4)+"00";
				System.out.println("======cityCode:"+cityCode);
				List<LocateInfo> cityList=getCityList(locProv);
				int city_count=cityList.size();
				for(int i=0;i<city_count;i++){
					LocateInfo locateInfo=cityList.get(i);
					if(locateInfo.getId().equals(cityCode)){
						locCity=locateInfo.getId();
						System.out.println("======cityName:"+locateInfo.getName());
						break;
					}
				}
			}
			if(locCity!=null){
				List<LocateInfo> counyList=getCounyList(locCity);
				int couny_count=counyList.size();
				for(int i=0;i<couny_count;i++){
					LocateInfo locateInfo=counyList.get(i);
					if(locateInfo.getId().equals(code)){
						locCouny=locateInfo.getId();
						System.out.println("======counyName:"+locateInfo.getName());
						break;
					}
				}
			}
		}
		
	}
	
	private class CityParser {

		public List<LocateInfo> getJSONParserResult(String JSONString, String key) {
			List<LocateInfo> list = new ArrayList<LocateInfo>();
			JsonObject result = new JsonParser().parse(JSONString).getAsJsonObject().getAsJsonObject(key);

			Iterator<Entry<String, JsonElement>> iterator = result.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, JsonElement> entry = (Entry<String, JsonElement>) iterator.next();
				LocateInfo cityinfo = new LocateInfo();
				cityinfo.setName(entry.getValue().getAsString());
				cityinfo.setId(entry.getKey());
				list.add(cityinfo);
			}
			return list;
		}

		public HashMap<String, List<LocateInfo>> getJSONParserResultArray(String JSONString, String key) {
			HashMap<String, List<LocateInfo>> hashMap = new HashMap<String, List<LocateInfo>>();
			JsonObject result = new JsonParser().parse(JSONString).getAsJsonObject().getAsJsonObject(key);

			Iterator<Entry<String, JsonElement>> iterator = result.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, JsonElement> entry = (Entry<String, JsonElement>) iterator.next();
				List<LocateInfo> list = new ArrayList<LocateInfo>();
				JsonArray array = entry.getValue().getAsJsonArray();
				for (int i = 0; i < array.size(); i++) {
					LocateInfo cityinfo = new LocateInfo();
					cityinfo.setName(array.get(i).getAsJsonArray().get(0).getAsString());
					cityinfo.setId(array.get(i).getAsJsonArray().get(1).getAsString());
					list.add(cityinfo);
				}
				hashMap.put(entry.getKey(), list);
			}
			return hashMap;
		}
	}
}
