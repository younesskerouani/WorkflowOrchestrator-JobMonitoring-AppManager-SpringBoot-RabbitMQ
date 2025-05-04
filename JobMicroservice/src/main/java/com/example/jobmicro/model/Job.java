package com.example.jobmicro.model;

import com.example.jobmicro.Type.StatusType;
import com.example.jobmicro.file.IxFile;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document
public class Job {

    private static final long serialVersionUID = -7776030191197992509L;

    private ObjectId _id;
    private String jobID;
   private String parentJobID;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    private String statusType;
    private String jobType;
    private String description;
    private Integer envID;
    private int fileIn;
    private int fileOut;
    private int fileWork;
    private Long duration;
    private String sessionID;
    private Boolean visible;
    private String connector;


    public Job() {
        super();
    }

    //New Job
    public Job(String jobType,  String description, String parentJobID, String sessionID ,Integer envID ) {
        super();
        this._id = new ObjectId();
       this.jobID = this._id.toHexString();

        if(sessionID == null){
            this.sessionID =this.jobID;}
        else{
            this.sessionID = sessionID;}

        this.parentJobID = parentJobID;
        this.jobType = jobType;
        this.description = description;
        this.startDate = new Date();
        this.statusType = StatusType.Processing.toString();
        this.envID = envID;
        this.visible = true;

    }


    private Set<String> files = new HashSet<>();

    public Integer getEnvID() {
        return envID;
    }
    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getParentJobID() {
        return parentJobID;
    }

    public void setParentJobID(String parentJobID) {
        this.parentJobID = parentJobID;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFileIn() {
        return fileIn;
    }

    public void setFileIn(int fileIn) {
        this.fileIn = fileIn;
    }

    public int getFileOut() {
        return fileOut;
    }

    public void setFileOut(int fileOut) {
        this.fileOut = fileOut;
    }

    public int getFileWork() {
        return fileWork;
    }

    public void setFileWork(int fileWork) {
        this.fileWork = fileWork;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public void endJob(String description, String statusJob) {
        endDate = new Date();
        duration = endDate.getTime() - startDate.getTime();
        this.statusType = statusJob;

        if(description != null)
            this.description = description;
    }

    //End Job
    public void endJob(String description) {
        endJob(description,StatusType.OK.toString() );
    }
    //End Job
    public void endJob() {
        this.endJob(null,StatusType.OK.toString());
    }

    public String getJobID() {
        return jobID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEnvID(Integer envID) {
        this.envID = envID;
    }
    public Set<String> getFiles() {
        return files;
    }
    public void setFiles(Set<String>	 files) {
        this.files = files;
    }



    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public void addFile(IxFile file) {
        files.add(file.get_id().toHexString());

        if("EDI".equals(file.getFileType()) && "IN".equals(file.getFileSens()))
            this.fileIn = 1;

        if(!"EDI".equals(file.getFileType()))
            this.fileWork = 1;

        if("EDI".equals(file.getFileType()) && "OUT".equals(file.getFileSens()))
            this.fileOut = 1;
    }

    //Update Jobs file
    public void updateFile(Job j) {
        if(j.fileIn > 0)
            this.fileIn = j.fileIn;
        if(j.fileOut > 0)
            this.fileOut = j.fileOut;
        if(j.fileWork > 0)
            this.fileWork = j.fileWork;

        this.files.addAll(j.getFiles());
    }
    @Override
    public String toString() {
        return "Job [_id=" + _id + ", jobID=" + jobID + ", parentJobID=" + parentJobID + ", startDate=" + startDate
                + ", endDate=" + endDate + ", statusType=" + statusType + ", jobType=" + jobType + ", description="
                + description + ", envID=" + envID + ", fileIn=" + fileIn + ", fileOut=" + fileOut + ", fileWork="
                + fileWork + ", duration=" + duration + ", sessionID=" + sessionID + ", visible=" + visible + ", files="
                + files + "]";
    }

}
