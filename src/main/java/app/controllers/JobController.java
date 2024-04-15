package app.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entities.Job;
import app.services.IJobService;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
	
	private final IJobService jobService;
	
	public JobController(IJobService jobService) {
		this.jobService = jobService;
	}

	@GetMapping
	public List<Job> getJobs() {
		return null;
	}
	
}
