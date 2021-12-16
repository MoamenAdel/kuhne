package com.example.kuhne.common;

public enum JsonKeys {

	CONFIRMED("confirmed"),
	RECOVERED("recovered"),
	DEATHS("deaths"),
	PEOPLE_VACCINATED("people_vaccinated"),
	POPULATION("population"),
	VACCINATED_LEVEL("Vaccinated_Level"),
	VARIANT_OF_LAST_CONFIRMED_RECORD("Variant_Of_Last_Confirmed_Record");
	
	private String key;

	public String getKey() {
		return key;
	}
	
	private JsonKeys(String key) {
		this.key = key;
	}
}
