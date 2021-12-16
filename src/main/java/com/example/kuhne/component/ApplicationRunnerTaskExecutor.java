package com.example.kuhne.component;

import java.net.URI;
import java.util.Scanner;

import org.json.JSONObject;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Profile("!test")
@Component
public class ApplicationRunnerTaskExecutor implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args)  {
		String input = "";
		System.out.println("To exit the application, enter -1");
		while (!input.equals("-1")) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter country name: ");
			input = scanner.nextLine(); // Read user input
			if (input.isEmpty())
				continue;
			if(input.equalsIgnoreCase("-1"))
				break;

			RestTemplate restTemplate = new RestTemplate();
			try{
				URI uri = new URI("http://localhost:8080?country=" + input);
			restTemplate.getForObject(uri, JSONObject.class);
			}catch (Exception e) {
				System.err.println("Please Enter a valid Country Name!");
			}

		}
		System.out.println("Bye Bye...");
		System.exit(100);
	}

}
