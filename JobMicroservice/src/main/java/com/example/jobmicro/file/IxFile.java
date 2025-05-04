package com.example.jobmicro.file;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;

public class IxFile implements Serializable{

    private static final long serialVersionUID = 1L;

    private ObjectId _id;

    private String fileID;

    private String fileName;

    private String filePath;

    private long fileSize;

    private Date fileDate;

    // EDI , ATTACHEMENT...
    private String fileType;

    private String jobID;

    //IN , OUT
    private String fileSens;

    private int version;

    private boolean current;


    public IxFile(String filePath, String fileType, String fileSens, String jobID) throws IOException {
        this._id = new ObjectId();
        this.fileID = this._id.toHexString();
        this.current = true;
        File file = new File(filePath);
        this.fileName = file.getName();
        this.filePath = file.getCanonicalPath().replace("\\", "/");
        this.fileSize = file.length();
        this.fileDate = new Date(file.lastModified());
        this.fileType = fileType;
        this.fileSens = fileSens;
        this.jobID = jobID;
    }


    public IxFile() {
        super();
    }



    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId id) {
        this._id = id;
    }
    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }


    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getFileDate() {
        return fileDate;
    }

    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSens() {
        return fileSens;
    }

    public void setFileSens(String fileSens) {
        this.fileSens = fileSens;
    }


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }
}
