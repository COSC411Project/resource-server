package app.repositories;

import org.springframework.data.repository.ListCrudRepository;

import app.entities.Job;

public interface JobRepository extends ListCrudRepository<Job, String> {

}
