package com.example.jobmicro.model;

import java.io.Serializable;

public class JobEvent   {
    private static final long serialVersionUID = -7346800536926737114L;

    private String eventType;
    private Job ixJob;

    public JobEvent(String e, Job ixjob) {
        this.eventType = e;
        this.ixJob = ixjob;
    }
    public JobEvent() {

    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Job getIxJob() {
        return ixJob;
    }

    public void setIxJob(Job ixJob) {
        this.ixJob = ixJob;
    }

    @Override
    public String toString() {
        return "JobEvent [eventType=" + eventType + ", ixJob=" + ixJob + "]";
    }
}