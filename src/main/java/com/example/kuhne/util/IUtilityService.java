package com.example.kuhne.util;

import org.json.JSONException;
import org.json.JSONObject;
import com.example.kuhne.common.UrlSubdirectory;

public interface IUtilityService {

	public String buildUrl(String countryName, UrlSubdirectory subdirectory);

	public String getStringValueObject(String jsonStr, String key) throws JSONException;

	public String getVaccinatedLevel(String jsonStr, String key1, String key2) throws JSONException;

	public JSONObject getJSONArrayValueObject(String jsonStr, String key) throws JSONException;

	public String getVariantOfLastRecord(String jsonStr, String currentConfirmed) throws JSONException;

	public void printingResult(JSONObject jsonObject);
}
