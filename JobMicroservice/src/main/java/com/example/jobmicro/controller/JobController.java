package com.example.jobmicro.controller;



import com.example.jobmicro.config.MessagingConfig;
import com.example.jobmicro.model.Job;
import com.example.jobmicro.model.JobEvent;
import com.example.jobmicro.service.JobService;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.ServerEndpoint;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fasterxml.jackson.core.io.NumberInput.parseInt;

@RestController
@RequestMapping("/job")
public class JobController   {

    @Autowired
    private RabbitTemplate template;
    @Autowired
    JobService jobService;



    @PostMapping("/{jobname}")


    public String jobsender(@RequestBody Job job,@RequestParam(name = "EventType") String eventType,  @PathVariable String jobname){

//        for(int i=0 ;i<100 ; i++) {


        job.setJobID(RandomStringUtils.randomAlphabetic(12));
        job.setParentJobID(RandomStringUtils.randomAlphabetic(12));
        job.setStatusType("active");
        job.setJobType(RandomStringUtils.randomAlphabetic(12));
        job.setDescription(RandomStringUtils.randomAlphabetic(600));
        job.setEnvID(Integer.valueOf(RandomStringUtils.randomNumeric(6)));
        job.setFileIn(RandomUtils.nextInt(10000,300000));
        job.setFileOut(RandomUtils.nextInt(1000,30000));
        job.setFileWork(RandomUtils.nextInt(1000,12000));
        job.setDuration(Long.parseLong(RandomStringUtils.randomNumeric(4)));
        job.setSessionID(RandomStringUtils.randomAlphanumeric(7));
        job.setVisible(true);
        job.setConnector(RandomStringUtils.randomAlphabetic(4));

        JobEvent jobEvent = new JobEvent(eventType, job);
            template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, jobEvent);

//         }
        return "Success !!";
    }


    @GetMapping(value = "job/{id}/children")
    public ResponseEntity<List<Job>> getChilds(@PathVariable("id") String jobid){
        return new ResponseEntity<>(jobService.getChilds(jobid), HttpStatus.OK);
    }


    @PostMapping(value = "jobs")
    public ResponseEntity<List<Job>> getsessionJobs(HttpServletRequest request,
                                                    @RequestParam(name = "startrow", defaultValue = "0") Integer startIndex,
                                                    @RequestParam(name = "limit", defaultValue = "100") Integer numOfRows,
                                                    @RequestBody(required=false) Map<String, Map<String, String>> filtreparams) throws ParseException {
        return new ResponseEntity<>(jobService.getSessionJobs(filtreparams,startIndex,numOfRows), HttpStatus.OK);
    }

    @GetMapping(value = "job/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable("id") String id ){
        return new ResponseEntity<>(jobService.getJobByID(id),HttpStatus.OK);
    }

}
