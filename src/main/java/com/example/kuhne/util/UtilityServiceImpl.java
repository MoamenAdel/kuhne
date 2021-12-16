package com.example.kuhne.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.example.kuhne.common.JsonKeys;
import com.example.kuhne.common.UrlSubdirectory;

@Service
public class UtilityServiceImpl implements IUtilityService {

	private static final String BASE_URL = "https://covid-api.mmediagroup.fr/v1/";
	private static final DecimalFormat df = new DecimalFormat("0.00");

	public String buildUrl(String countryName, UrlSubdirectory subdirectory) {
		StringBuilder builder = new StringBuilder(BASE_URL);
		builder.append(subdirectory.getSubdirectory());

		return builder.toString().replace("{country}", countryName);
	}

	public String getStringValueObject(String jsonStr, String key) throws JSONException {

		JSONObject obj = new JSONObject(jsonStr);
		JSONObject obj2 = obj.getJSONObject("All");

		return obj2.getLong(key) + "";
	}

	public String getVaccinatedLevel(String jsonStr, String key1, String key2) throws JSONException {

		Long peopleVaccinated = 0L;
		Long population = 0L;

		JSONObject obj = new JSONObject(jsonStr);
		JSONObject obj2 = obj.getJSONObject("All");
		if (key1 != null && !key1.isEmpty())
			peopleVaccinated = obj2.getLong(key1);
		if (key2 != null && !key2.isEmpty())
			population = obj2.getLong(key2);

		df.setRoundingMode(RoundingMode.UP);
		if (peopleVaccinated != null && population != null && peopleVaccinated > 0 && population > 0)
			return df.format((peopleVaccinated.doubleValue() / population.doubleValue()) * 100);
		else
			return null;
	}

	public String getVariantOfLastRecord(String jsonStr, String currentConfirmed) throws JSONException {

		JSONObject jsonArrayDates = getJSONArrayValueObject(jsonStr, "dates");

		Iterator<String> keys = jsonArrayDates.keys();
		List<String> sortedKeys = new ArrayList<String>();

		while (keys.hasNext()) {
			sortedKeys.add(keys.next());
		}

		Collections.sort(sortedKeys);

		Long lastResult = jsonArrayDates.getLong(sortedKeys.get(sortedKeys.size() - 2)); // to get yesterday

		return (Long.valueOf(currentConfirmed) - lastResult) + "";
	}

	public JSONObject getJSONArrayValueObject(String jsonStr, String key) throws JSONException {

		JSONObject obj = new JSONObject(jsonStr);
		JSONObject obj2 = obj.getJSONObject("All");

		return obj2.getJSONObject(key);
	}

	@Override
	public void printingResult(JSONObject jsonObject) {
		System.out.println(JsonKeys.CONFIRMED.getKey() + ": " + jsonObject.get(JsonKeys.CONFIRMED.getKey()));
		System.out.println(JsonKeys.RECOVERED.getKey() + ": " + jsonObject.get(JsonKeys.RECOVERED.getKey()));
		System.out.println(JsonKeys.DEATHS.getKey() + ": " + jsonObject.get(JsonKeys.DEATHS.getKey()));
		System.out.println(
				JsonKeys.VACCINATED_LEVEL.getKey() + ": " + jsonObject.get(JsonKeys.VACCINATED_LEVEL.getKey()));
		System.out.println(JsonKeys.VARIANT_OF_LAST_CONFIRMED_RECORD.getKey() + ": "
				+ jsonObject.get(JsonKeys.VARIANT_OF_LAST_CONFIRMED_RECORD.getKey()));

	}
}
