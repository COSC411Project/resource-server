package app.controllers;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.proxies.INLPProxy;
import app.services.IResumeService;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
	
	private final INLPProxy nlpProxy;
	private final IResumeService resumeService;
	
	public JobController(INLPProxy nlpProxy, IResumeService resumeService) {
		this.nlpProxy = nlpProxy;
		this.resumeService = resumeService;
	}

	@GetMapping
	public ResponseEntity<String> getJobs() throws URISyntaxException, IOException, InterruptedException {
		String jobString = nlpProxy.getJobs();
		
		return ResponseEntity.ok(jobString);
	}
	
	@GetMapping("/scores")
	public ResponseEntity<String> getScores(Authentication authentication) throws URISyntaxException, IOException, InterruptedException {
		int userId = (int) authentication.getPrincipal();
		String resumeText = resumeService.getResume(userId);
		if (resumeText == null) {
			return ResponseEntity.badRequest().build();
		}
		
		String scoreString = nlpProxy.getScores(resumeText);
		return ResponseEntity.ok(scoreString);
	}
}
