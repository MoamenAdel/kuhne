package com.example.kuhne.common;

public enum UrlSubdirectory {

	CASES("cases?country={country}"),
	VACCINES("vaccines?country={country}"),
	HISTORY("history?country={country}&status=confirmed");
	
	private String subdirectory;

	public String getSubdirectory() {
		return subdirectory;
	}

	private UrlSubdirectory(String subdirectory) {
		this.subdirectory = subdirectory;
	}

	
}
