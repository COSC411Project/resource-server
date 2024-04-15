package app.services;

import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;


public interface IResumeService {

	void saveResume(InputStream inputStream, String name, int userId);
	InputStreamResource viewResume(String name, int userId);
	void deleteResume(String name, int userId);
	
}
