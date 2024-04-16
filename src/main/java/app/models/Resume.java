package app.models;

import org.springframework.core.io.InputStreamResource;

public class Resume {

	private InputStreamResource resource;
	private long contentLength;
	
	public Resume(InputStreamResource resource, long contentLength) {
		super();
		this.resource = resource;
		this.contentLength = contentLength;
	}

	public InputStreamResource getResource() {
		return resource;
	}
	
	public long getContentLength() {
		return contentLength;
	}
	
}
