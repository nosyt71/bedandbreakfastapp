package vttp2023.batch4.paf.assessment.services;

import java.io.StringReader;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class ForexService {

	private String search;

	private final String frankfurtUrl = "https://api.frankfurter.app/latest?";
	// https://api.frankfurt.app/latest?amount=10&from=AUD&to=SGD
	// https://api.frankfurter.app/latest?amount=10&from=AUD&to=SGD
	RestTemplate restTemplate = new RestTemplate();

	// TODO: Task 5 
	public float convert(String from, String to, float amount) {
	
	search = "amount=" + amount + "&from=" + from + "&to=" +to;
	String url_conversion = frankfurtUrl + search;
	// System.out.println(url_conversion);
	ResponseEntity<String> response = restTemplate.getForEntity(url_conversion, String.class);
	// System.out.println(response);
	// System.out.println("-----------------");
	JsonReader reader = Json.createReader(new StringReader(response.getBody()));
	JsonObject result = reader.readObject();
	// System.out.println(result);
	float toSGD = (float) result.getJsonObject("rates").getJsonNumber("SGD").doubleValue();

	return toSGD;
	}
}
// 	public float convert(String from, String to, float amount) {
// 		search = "amount=" + amount + "&from=" + from + "&to=" + to;
// 		String url_conversion = frankfurtUrl + search;

// 		ResponseEntity<String> response = restTemplate.getForEntity(url_conversion, String.class);
// 		JsonReader reader = Json.createReader(new StringReader(response.getBody()));
// 		JsonObject result = reader.readObject();

// 		// Access the "rates" object and directly get the "SGD" JSON number
// 		float toSGD = (float) result.getJsonObject("rates").getJsonNumber("SGD").doubleValue();

// 		return toSGD;
// }
