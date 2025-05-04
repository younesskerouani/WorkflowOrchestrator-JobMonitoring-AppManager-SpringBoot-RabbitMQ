package com.example.jobmicro.receiver;

import com.example.jobmicro.config.MessagingConfig;
import com.example.jobmicro.model.JobEvent;
import com.example.jobmicro.service.JobService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;



@Component
@EnableAsync
public class JobReceiver {

    @Autowired
    JobService jobservice;

      @RabbitHandler
      @RabbitListener(queues = MessagingConfig.JOB)
      @Async

    public void receive(JobEvent jobevent) {

        try {
            if("CREATE".equals(jobevent.getEventType())) {
                 jobservice.insertJob(jobevent.getIxJob());
            }else if("UPDATE".equals(jobevent.getEventType())) {
                jobservice.updateJob(jobevent.getIxJob());
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}