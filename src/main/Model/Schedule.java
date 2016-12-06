package main.Model;

import java.sql.Date;

/**
 * Created by blakejoynes on 12/1/16.
 */
public class Schedule extends Session {
    private int scheduleID;
    private String availability;


    public Schedule(){

    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
