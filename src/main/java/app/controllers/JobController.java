package app.controllers;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.proxies.INLPProxy;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
	
	private final INLPProxy nlpProxy;
	
	public JobController(INLPProxy nlpProxy) {
		this.nlpProxy = nlpProxy;
	}

	@GetMapping
	public ResponseEntity<String> getJobs() throws URISyntaxException, IOException, InterruptedException {
		String jobString = nlpProxy.getJobs();
		
		return ResponseEntity.ok(jobString);
	}
}
