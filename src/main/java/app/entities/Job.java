package app.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Job {

	@Id
	private String id;
	private String company;
	private String title;
	private String description;
	private String link;
	private LocalDateTime datetimeCreated;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}

	public LocalDateTime getDatetimeCreated() {
		return datetimeCreated;
	}

	public void setDatetimeCreated(LocalDateTime datetimeCreated) {
		this.datetimeCreated = datetimeCreated;
	}
	
}
