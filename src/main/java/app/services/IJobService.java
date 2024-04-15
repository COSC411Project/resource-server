package app.services;

import java.util.List;

import app.entities.Job;

public interface IJobService {

	List<Job> getJobs();
	List<Job> getJobs(String links);
	List<Job> getJobs(int userId);
	
}
