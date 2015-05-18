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
public class CityUtil {

	private List<CityInfo> prov_list = new ArrayList<CityInfo>();
	private HashMap<String, List<CityInfo>> city_map = new HashMap<String, List<CityInfo>>();
	private HashMap<String, List<CityInfo>> couny_map = new HashMap<String, List<CityInfo>>();

	private static CityUtil model;
	private CityUtil() {}
	
	public static CityUtil getSingleton() {
		if (null == model) {
			model = new CityUtil();
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

	public List<CityInfo> getProvlist() {
		return prov_list;
	}
	public List<CityInfo> getCityList(String provCode) {
		return city_map.get(provCode);
	}
	public List<CityInfo> getCounyList(String cityCode) {
		return couny_map.get(cityCode);
	}
	public int[] LocCode(String code) {
		int[] ret={-1,-1,-1};
		if(code.length()==6){
			String provCode=code.substring(0, 2)+"0000";
			System.out.println("======provCode:"+provCode);
			String locProv=null;
			String locCity=null;
			int prov_count=prov_list.size();
			for(int i=0;i<prov_count;i++){
				CityInfo locateInfo=prov_list.get(i);
				if(locateInfo.getId().equals(provCode)){
					locProv=locateInfo.getId();
					System.out.println("======provName:"+locateInfo.getName());
					ret[0]=i;
					break;
				}
			}
			if(locProv!=null){
				String cityCode=code.substring(0, 4)+"00";
				System.out.println("======cityCode:"+cityCode);
				List<CityInfo> cityList=getCityList(locProv);
				int city_count=cityList.size();
				for(int i=0;i<city_count;i++){
					CityInfo locateInfo=cityList.get(i);
					if(locateInfo.getId().equals(cityCode)){
						locCity=locateInfo.getId();
						System.out.println("======cityName:"+locateInfo.getName());
						ret[1]=i;
						break;
					}
				}
			}
			if(locCity!=null){
				List<CityInfo> counyList=getCounyList(locCity);
				int couny_count=counyList.size();
				for(int i=0;i<couny_count;i++){
					CityInfo locateInfo=counyList.get(i);
					if(locateInfo.getId().equals(code)){
						System.out.println("======counyName:"+locateInfo.getName());
						ret[2]=i;
						break;
					}
				}
			}
		}
		return ret;
	}
	
	private class CityParser {

		public List<CityInfo> getJSONParserResult(String JSONString, String key) {
			List<CityInfo> list = new ArrayList<CityInfo>();
			JsonObject result = new JsonParser().parse(JSONString).getAsJsonObject().getAsJsonObject(key);

			Iterator<Entry<String, JsonElement>> iterator = result.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, JsonElement> entry = (Entry<String, JsonElement>) iterator.next();
				CityInfo cityinfo = new CityInfo();
				cityinfo.setName(entry.getValue().getAsString());
				cityinfo.setId(entry.getKey());
				list.add(cityinfo);
			}
			return list;
		}

		public HashMap<String, List<CityInfo>> getJSONParserResultArray(String JSONString, String key) {
			HashMap<String, List<CityInfo>> hashMap = new HashMap<String, List<CityInfo>>();
			JsonObject result = new JsonParser().parse(JSONString).getAsJsonObject().getAsJsonObject(key);

			Iterator<Entry<String, JsonElement>> iterator = result.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, JsonElement> entry = (Entry<String, JsonElement>) iterator.next();
				List<CityInfo> list = new ArrayList<CityInfo>();
				JsonArray array = entry.getValue().getAsJsonArray();
				for (int i = 0; i < array.size(); i++) {
					CityInfo cityinfo = new CityInfo();
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
