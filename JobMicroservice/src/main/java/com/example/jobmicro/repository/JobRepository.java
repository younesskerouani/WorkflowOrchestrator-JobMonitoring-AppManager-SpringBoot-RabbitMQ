package com.example.jobmicro.repository;

import com.example.jobmicro.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {

}
