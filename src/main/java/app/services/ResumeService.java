package app.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import app.models.Resume;

@Service
public class ResumeService implements IResumeService {
	
	private String basePath = System.getProperty("user.home") + "/resumes";
	
	@Override
	public List<String> getResumes(int userId) {
		String folderPath = basePath + "/" + userId;
		File folder = new File(folderPath);
		if (!folder.exists()) {
			return new ArrayList<>();
		}
		
		return Stream.of(folder.listFiles())
					 .map(file -> file.getName())
					 .filter(fileName -> fileName.contains(".doc") || fileName.contains(".pdf"))
					 .toList();
	}

	@Override
	public void saveResume(InputStream inputStream, String name, int userId) throws FileNotFoundException, IOException {
		String folderPath = basePath + "/" + userId;
		File folder = new File(folderPath);
		folder.mkdirs();
		
		try (FileOutputStream fos = new FileOutputStream(folderPath + "/" + name)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int bytesRead = inputStream.read(buffer);
				if (bytesRead <= 0) break;
				
				fos.write(buffer, 0, bytesRead);
			}
		}
	}

	@Override
	public Resume viewResume(String name, int userId) throws IOException {
		String filePath = basePath + "/" + userId + "/" + name;
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		
		InputStream inputStream = Files.newInputStream(Paths.get(filePath));
		long contentLength = file.length();
		
		return new Resume(new InputStreamResource(inputStream), contentLength);
	}

	@Override
	public boolean deleteResume(String name, int userId) {
		String filePath = basePath + "/" + userId + "/" + name;
		File file = new File(filePath);
		return file.delete();
	}

	@Override
	public String getResume(int userId) throws IOException {
		String folderPath = basePath + "/" + userId;
		File folder = new File(folderPath);
		if (!folder.exists()) {
			return null;
		}
		
		File[] resumes = folder.listFiles();
		if (resumes.length == 0) {
			return null;
		}
		
		File resume = resumes[0];
		
		if (resume.getName().contains(".pdf")) {
			return getTextFromPDF(resume);
		} else {
			return getTextFromDOC(resume);
		}
	}

	private String getTextFromPDF(File resume) throws IOException {
		try (PDDocument document = PDDocument.load(resume)) {
			PDFTextStripper stripper = new PDFTextStripper();
			return stripper.getText(document);
		} catch (Exception ex) {
			return null;
		}
	}

	private String getTextFromDOC(File resume) throws FileNotFoundException, IOException {
		try (FileInputStream inputStream = new FileInputStream(resume);
			 XWPFDocument doc = new XWPFDocument(inputStream);
			 XWPFWordExtractor extractor = new XWPFWordExtractor(doc);) {
			
			return extractor.getText();
		} catch (Exception ex) {
			return null;
		}
	}

}
