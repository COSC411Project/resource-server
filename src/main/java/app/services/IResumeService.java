package app.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import app.models.Resume;


public interface IResumeService {

	List<String> getResumes(int userId);
	void saveResume(InputStream inputStream, String name, int userId) throws FileNotFoundException, IOException;
	Resume viewResume(String name, int userId) throws IOException;
	boolean deleteResume(String name, int userId);
	String getResume(int userId) throws IOException;
	
}
