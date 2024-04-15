package app.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import app.services.IResumeService;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {
	
	private IResumeService resumeService;
	
	public ResumeController(IResumeService resumeService) {
		this.resumeService = resumeService;
	}
	
	@GetMapping
	public List<Integer> getResumes() {
		return null;
	}
	
	@GetMapping("{id}")
	public InputStream getResume(@PathVariable int id) {
		return null;
	}

	@PostMapping
	public ResponseEntity<Object> uploadResume(Authentication authentication, @RequestParam("file") MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		int userId = (int) authentication.getPrincipal();
		resumeService.saveResume(file.getInputStream(), file.getName(), userId);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
