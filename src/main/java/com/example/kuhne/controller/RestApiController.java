package com.example.kuhne.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.kuhne.common.JsonKeys;
import com.example.kuhne.common.UrlSubdirectory;
import com.example.kuhne.util.IUtilityService;

@RestController
public class RestApiController {

	@Autowired
	IUtilityService utility;

	@GetMapping
	public ResponseEntity<String> getClientStat(@RequestParam(required = true) String country)
			throws URISyntaxException {

		RestTemplate restTemplate = new RestTemplate();
		String confirmed = "";
		String recovered = "";
		String deaths = "";
		String peopleVaccinatedPercentage = "";
		String variantOfLastRecord = "";
		boolean noError = true;

		URI uri = new URI(utility.buildUrl(country, UrlSubdirectory.CASES));
		String casesResponse = restTemplate.getForObject(uri, String.class);

		uri = new URI(utility.buildUrl(country, UrlSubdirectory.VACCINES));
		String vaccinesResponse = restTemplate.getForObject(uri, String.class);

		uri = new URI(utility.buildUrl(country, UrlSubdirectory.HISTORY));
		String historyResponse = restTemplate.getForObject(uri, String.class);

		try {
			confirmed = utility.getStringValueObject(casesResponse, JsonKeys.CONFIRMED.getKey());
			recovered = utility.getStringValueObject(casesResponse, JsonKeys.RECOVERED.getKey());
			deaths = utility.getStringValueObject(casesResponse, JsonKeys.DEATHS.getKey());

			peopleVaccinatedPercentage = utility.getVaccinatedLevel(vaccinesResponse,
					JsonKeys.PEOPLE_VACCINATED.getKey(), JsonKeys.POPULATION.getKey());

			variantOfLastRecord = utility.getVariantOfLastRecord(historyResponse, confirmed);
		} catch (JSONException e) {
			System.err.println("Please check country syntax");
			noError = false;
		}
		if (noError) {
			JSONObject response = new JSONObject();
			try {
				response.accumulate(JsonKeys.CONFIRMED.getKey(), confirmed);
				response.accumulate(JsonKeys.RECOVERED.getKey(), recovered);
				response.accumulate(JsonKeys.DEATHS.getKey(), deaths);
				response.accumulate(JsonKeys.VACCINATED_LEVEL.getKey(),
						peopleVaccinatedPercentage + " % of total population");
				response.accumulate(JsonKeys.VARIANT_OF_LAST_CONFIRMED_RECORD.getKey(), variantOfLastRecord);
				utility.printingResult(response);
				return new ResponseEntity<>(response.toString(), HttpStatus.OK);
			} catch (JSONException e) {
				// e.printStackTrace();
			}
		}
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

}
