package app.proxies;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.stereotype.Component;

@Component
public class NLPProxy implements INLPProxy {

	private HttpClient httpClient = HttpClient.newHttpClient();
	
	public String getJobs() throws URISyntaxException, IOException, InterruptedException {
		String uri = "http://localhost:3000/jobpostings";
		HttpRequest request = HttpRequest.newBuilder()
										 .uri(new URI(uri))
										 .GET()
										 .build();
		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
		return response.body();
	}
}
