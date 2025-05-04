package com.example.jobmicro.service;

import com.example.jobmicro.model.Job;
import com.example.jobmicro.repository.JobRepo;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobService {

    @Autowired
    JobRepo repo;

    @Autowired
    MongoTemplate mongoTemplate;


    public Job insertJob(Job job)  {

        return mongoTemplate.save(job, "job");
    }

//    public Job updateJob(Job ixJob) throws InterruptedException {
//        return repo.updateJob(ixJob);
//    }

    public Job updateJob(Job job)  {

        int tentative = 0;

        while(tentative <6) {
            Query query = new Query();
            query.addCriteria(Criteria.where("jobID").is(job.getJobID()));
            Job jobFound = mongoTemplate.findOne(query, Job.class);

            if(jobFound != null) {
                Document doc = new org.bson.Document();
                mongoTemplate.getConverter().write(job, doc);
                Update update = Update.fromDocument(doc);
                 mongoTemplate.updateFirst(query,update,"job");

            }

            System.out.println("Tentative " + tentative);
            tentative++;
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return job;
    }

    public List<Document> getAlljobs(){
        return repo.getAllObjects("Job");
    }

    public List<Job> getByKey(String key, Object value, boolean sorted){
        Map<String, Object> criteria = new HashMap<>();
        criteria.put(key, value);
        return repo.getObjectBy(criteria, sorted);
    }

    public List<Job> getByKeys(Map<String, Object> keys, boolean sorted){
        return repo.getObjectBy(keys, sorted);
    }

    public List<Job> getSessionJobs(){
        return getByKey("jobType", "Session", true);
    }

    public List<Job> getSessionJobs(Map<String, Map<String, String>> filtreparams,int StartRow , int limit) throws ParseException {
//        Map<String, String> map = new HashMap<>();
//        map.put("Session", "=");
//        filtreparams.put("jobType", map);
//        filtreparams.remove("undefined");

        return repo.getObjectBy(filtreparams,StartRow,limit);
    }

    public Job getJobByID(String id){
        List<Job> jobs = getByKey("jobID", id,false);
        return jobs.get(0);
    }

    public List<Job> getChilds(String id){
        return getByKey("parentJobID", id,false);
    }


}