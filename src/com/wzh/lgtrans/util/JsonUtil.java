package com.wzh.lgtrans.util;

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
import com.wzh.lgtrans.struct.IdName;

/**
 * 
 * 城市代码
 * 
 * @author wzh
 * 
 */
public class JsonUtil {

	private static List<IdName> prov_list = new ArrayList<IdName>();
	private static HashMap<String, List<IdName>> city_map = new HashMap<String, List<IdName>>();
	private static HashMap<String, List<IdName>> couny_map = new HashMap<String, List<IdName>>();
	private static List<IdName> cartype_list = new ArrayList<IdName>();
	private static HashMap<String, List<String>> carlong_map = new HashMap<String, List<String>>();

	public static void loadCityData(Context ctx){
		CityParser cityParser = new CityParser();
		String area_str = FileUtil.readAssets(ctx, "area.json");
		prov_list = cityParser.getJSONParserResult(area_str, "province");
		city_map = cityParser.getJSONParserResultArray(area_str, "city");
		couny_map = cityParser.getJSONParserResultArray(area_str, "couny");
	}
	public static void loadCarData(Context ctx){
		CarParser carParser = new CarParser();
		String car_str = FileUtil.readAssets(ctx, "car.json");
		cartype_list = carParser.getJSONParserResult(car_str, "cartype");
		carlong_map = carParser.getJSONParserResultArray(car_str, "carlong");
	}

	public static List<IdName> getProvlist() {
		return prov_list;
	}
	public static List<IdName> getCarTypelist() {
		return cartype_list;
	}
	public static List<IdName> getCityList(String provCode) {
		return city_map.get(provCode);
	}
	public static List<IdName> getCounyList(String cityCode) {
		return couny_map.get(cityCode);
	}
	public static List<String> getCarLongList(String typeCode) {
		return carlong_map.get(typeCode);
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
				IdName locateInfo=prov_list.get(i);
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
				List<IdName> cityList=getCityList(locProv);
				int city_count=cityList.size();
				for(int i=0;i<city_count;i++){
					IdName locateInfo=cityList.get(i);
					if(locateInfo.getId().equals(cityCode)){
						locCity=locateInfo.getId();
						System.out.println("======cityName:"+locateInfo.getName());
						ret[1]=i;
						break;
					}
				}
			}
			if(locCity!=null){
				List<IdName> counyList=getCounyList(locCity);
				int couny_count=counyList.size();
				for(int i=0;i<couny_count;i++){
					IdName locateInfo=counyList.get(i);
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
	
	private static class CityParser {

		public List<IdName> getJSONParserResult(String JSONString, String key) {
			List<IdName> list = new ArrayList<IdName>();
			JsonObject result = new JsonParser().parse(JSONString).getAsJsonObject().getAsJsonObject(key);

			Iterator<Entry<String, JsonElement>> iterator = result.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, JsonElement> entry = (Entry<String, JsonElement>) iterator.next();
				IdName cityinfo = new IdName();
				cityinfo.setName(entry.getValue().getAsString());
				cityinfo.setId(entry.getKey());
				list.add(cityinfo);
			}
			return list;
		}

		public HashMap<String, List<IdName>> getJSONParserResultArray(String JSONString, String key) {
			HashMap<String, List<IdName>> hashMap = new HashMap<String, List<IdName>>();
			JsonObject result = new JsonParser().parse(JSONString).getAsJsonObject().getAsJsonObject(key);

			Iterator<Entry<String, JsonElement>> iterator = result.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, JsonElement> entry = (Entry<String, JsonElement>) iterator.next();
				List<IdName> list = new ArrayList<IdName>();
				JsonArray array = entry.getValue().getAsJsonArray();
				for (int i = 0; i < array.size(); i++) {
					IdName cityinfo = new IdName();
					cityinfo.setName(array.get(i).getAsJsonArray().get(0).getAsString());
					cityinfo.setId(array.get(i).getAsJsonArray().get(1).getAsString());
					list.add(cityinfo);
				}
				hashMap.put(entry.getKey(), list);
			}
			return hashMap;
		}
	}
	private static class CarParser {
		public List<IdName> getJSONParserResult(String JSONString, String key) {
			List<IdName> list = new ArrayList<IdName>();
			JsonObject result = new JsonParser().parse(JSONString).getAsJsonObject().getAsJsonObject(key);
			Iterator<Entry<String, JsonElement>> iterator = result.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, JsonElement> entry = (Entry<String, JsonElement>) iterator.next();
				IdName idName = new IdName();
				idName.setName(entry.getValue().getAsString());
				idName.setId(entry.getKey());
				list.add(idName);
			}
			return list;
		}

		public HashMap<String, List<String>> getJSONParserResultArray(String JSONString, String key) {
			HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
			JsonObject result = new JsonParser().parse(JSONString).getAsJsonObject().getAsJsonObject(key);
			Iterator<Entry<String, JsonElement>> iterator = result.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, JsonElement> entry = (Entry<String, JsonElement>) iterator.next();
				List<String> list = new ArrayList<String>();
				JsonArray array = entry.getValue().getAsJsonArray();
				for (int i = 0; i < array.size(); i++) {
					String temp=array.get(i).getAsString();
					list.add(temp);
				}
				hashMap.put(entry.getKey(), list);
			}
			return hashMap;
		}
	}
}
