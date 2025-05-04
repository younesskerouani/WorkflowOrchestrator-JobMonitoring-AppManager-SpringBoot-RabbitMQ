package com.example.jobmicro.Type;

public enum StatusType {
    // Values
    OK(0), Fixed(10), Pending(40), Processing(50), Warning(80), Error(100);


    // Constructor
    public final int dbID;
    StatusType(int _dbID) { dbID = _dbID; }
}
