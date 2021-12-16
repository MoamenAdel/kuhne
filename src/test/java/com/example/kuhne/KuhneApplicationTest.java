package com.example.kuhne;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.kuhne.config.RestConfig;
import com.example.kuhne.config.ServiceConfig;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { RestConfig.class, ServiceConfig.class })
@SpringBootTest
public class KuhneApplicationTest {
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testCountryEndpointWithGET() throws Exception {
		mockMvc.perform(get("/")
				.param("country", getTestingCountry()))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.confirmed").exists())
				.andExpect(jsonPath("$.recovered").exists())
				.andExpect(jsonPath("$.deaths").exists())
				.andExpect(jsonPath("$.Vaccinated_Level").exists())
				.andExpect(jsonPath("$.Variant_Of_Last_Confirmed_Record").exists());

	}
	
	@Test
	public void testWrongInputEndpointWithGET() throws Exception {
		mockMvc.perform(get("/")
				.param("country", "XYZ"))
				.andDo(print())
				.andExpect(status().is4xxClientError())
				;

	}

	private static String getTestingCountry() {
		List<String> countries = new ArrayList<>();
		countries.add("Egypt");
		countries.add("Estonia");
		countries.add("Germany");
		countries.add("France");
		countries.add("Italy");

		Random random = new Random();
		int index = random.ints(0, countries.size() - 1).findFirst().getAsInt();
		return countries.get(index);
	}
}
