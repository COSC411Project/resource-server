package app.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import app.models.Resume;
import app.services.IResumeService;

@RestController
@RequestMapping("/api")
public class ResumeController {
	
	private IResumeService resumeService;
	
	public ResumeController(IResumeService resumeService) {
		this.resumeService = resumeService;
	}
	
	@GetMapping("/resumes")
	public List<String> getResumes(Authentication authentication) {
		int userId = (int) authentication.getPrincipal();
		return resumeService.getResumes(userId);
	}
	
	@GetMapping("/resume")
	public ResponseEntity<InputStreamResource> getResume(Authentication authentication, @RequestParam String name) throws IOException {
		int userId = (int) authentication.getPrincipal();
		
		Resume resume = resumeService.viewResume(name, userId);
		if (resume == null) {
			return ResponseEntity.badRequest().build();
		}
		
		if (name.contains(".pdf")) {
			return ResponseEntity.ok()
								 .contentLength(resume.getContentLength())
								 .contentType(MediaType.APPLICATION_PDF)
								 .body(resume.getResource());
		} else {
			String mediaType = getMediaType(name);
			return ResponseEntity.ok()
								 .contentLength(resume.getContentLength())
								 .contentType(MediaType.valueOf(mediaType))
								 .body(resume.getResource());
		}
	}
	
	private String getMediaType(String name) {
		if (name.contains(".docx")) {
			return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		} else {
			return "application/msword";
		}
	}

	@PostMapping("/resume")
	public ResponseEntity<Object> uploadResume(Authentication authentication, @RequestParam("file") MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		
		int userId = (int) authentication.getPrincipal();
		resumeService.saveResume(file.getInputStream(), file.getOriginalFilename(), userId);
		
		return ResponseEntity.ok().build();
	}
}
