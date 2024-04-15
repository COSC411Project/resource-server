package app.services;

import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

@Service
public class ResumeService implements IResumeService {

	@Override
	public void saveResume(InputStream inputStream, String name, int userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputStreamResource viewResume(String name, int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteResume(String name, int userId) {
		// TODO Auto-generated method stub
		
	}

}
