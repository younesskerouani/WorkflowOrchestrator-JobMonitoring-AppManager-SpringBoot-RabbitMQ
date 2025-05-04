package com.example.jobmicro.repository;

import com.example.jobmicro.model.Job;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JobRepo {

    @Autowired
    MongoTemplate mongoTemplate;

    public static final String PROJECTION  = "{_id : 0, current : 0}";

//    public Job updateJob(Job job)  {
//
//        int tentative = 0;
//
//        while(tentative <6) {
//            Job jobFound = mongoTemplate.findOne(Query.query(Criteria.where("jobID").is(job.getJobID())), Job.class);
//
//            if(jobFound != null) {
//                return mongoTemplate.save(job);
//            }
//            System.out.println("Tentative " + tentative);
//            tentative++;
//            try {
//                Thread.sleep(300);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        return job;
//    }


    //Insert a generic object into a collection
//    public  Job insertIntoTable(Job job){
//        return mongoTemplate.save(job, "job");
//    }

    // Search by criterias
    public List<Job> getObjectBy(Map<String, Object> criterias , boolean sorted){
        Query query = new Query();
        for(Map.Entry<String, Object> entry : criterias.entrySet()) {
            query.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue()));
        }

        if(sorted)
            query.with(Sort.by(Sort.Direction.DESC, "_id"));

        return  mongoTemplate.find(query, Job.class);

    }

    // Search by criterias
    public  List<Job> getObjectBy(Map<String, Map<String, String>> criterias,int StartRow ,int limitrow) throws ParseException {
        Query query = new Query();
        List<Criteria> andExpression =  new ArrayList<>();
        Criteria crt = new Criteria();
        for(Map.Entry<String, Map<String, String>> entry : criterias.entrySet()) {
            List<Criteria> orExpression = new ArrayList<>();
            for(Map.Entry<String, String> secEntry : entry.getValue().entrySet()) {
                Object value = secEntry.getKey();
                if(entry.getKey().contains("Date")) {
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse((String)value);
                    value = date;
                }
                switch (secEntry.getValue().toLowerCase()) {
                    case "like" :
                        orExpression.add(Criteria.where(entry.getKey()).regex((String)value));
                        break;
                    case ">":
                        orExpression.add(Criteria.where(entry.getKey()).gt(value));
                        break;
                    case ">=" :
                        orExpression.add(Criteria.where(entry.getKey()).gte(value));
                        break;
                    case "<" :
                        orExpression.add(Criteria.where(entry.getKey()).lt(value));
                        break;
                    case "<=" :
                        orExpression.add(Criteria.where(entry.getKey()).lte(value));
                        break;
                    case "!=" :
                        orExpression.add(Criteria.where(entry.getKey()).ne(value));
                        break;
                    default:
                        orExpression.add(Criteria.where(entry.getKey()).is(value));
                        break;
                }

            }
            Criteria orCriteria = new Criteria();
            orCriteria.orOperator(orExpression);
            andExpression.add(orCriteria);

        }
        query.addCriteria(crt.andOperator(andExpression.toArray(new Criteria[andExpression.size()])));

        query.with(Sort.by(Sort.Direction.DESC, "_id")).skip(StartRow-1).limit(limitrow);

        // calculer temps de reponse
        long startTime = System.currentTimeMillis();

         List<Job> jobFound = mongoTemplate.find(query, Job.class);

        long endTime = System.currentTimeMillis();

        System.out.println("That took " + (endTime - startTime) + " milliseconds");

        return jobFound;
    }

    public List<Document> getAllObjects(String collection){
        return new ArrayList<>();

		/*  try(MongoClient mongo = new MongoClient("localhost" , 27017);){
		        MongoDatabase database = mongo.getDatabase("Job");

		        String filterJson = "{current : true } ";

				BsonDocument filter = BsonDocument.parse(filterJson);
				BsonDocument projection = BsonDocument.parse(PROJECTION);

				return database.getCollection(collection).find(filter).projection(projection).into(new ArrayList<Document>());
		  }*/

    }

    private JobRepo() {}
}