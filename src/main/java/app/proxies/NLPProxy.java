package app.proxies;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import app.dtos.ScoreRequestDTO;

@Component
public class NLPProxy implements INLPProxy {

	private HttpClient httpClient = HttpClient.newHttpClient();
	private Gson gson = new Gson();
	
	public String getJobs() throws URISyntaxException, IOException, InterruptedException {
		String uri = "http://localhost:3000/jobpostings";
		HttpRequest request = HttpRequest.newBuilder()
										 .uri(new URI(uri))
										 .GET()
										 .build();
		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
		return response.body();
	}
	
	public String getScores(String resumeText) throws URISyntaxException, IOException, InterruptedException {
		String uri = "http://localhost:3000/compare";
		
		ScoreRequestDTO requestDTO = new ScoreRequestDTO(resumeText);
		String body = gson.toJson(requestDTO);
		
		HttpRequest request = HttpRequest.newBuilder()
										 .uri(new URI(uri))
										 .header("Content-Type", "application/json")
										 .POST(BodyPublishers.ofString(body))
										 .build();
		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
		return response.body();
	}
}
