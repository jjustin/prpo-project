package si.fri.prpo.s01.services.dtos;

import java.sql.Time;
import java.util.Date;

public class StateDTO {

    private Integer stateId;
    private Integer numberIn;
    private Integer numberOut;
    private Date date;
    private Time time;

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getNumberIn() {
        return numberIn;
    }

    public void setNumberIn(Integer numberIn) {
        this.numberIn = numberIn;
    }

    public Integer getNumberOut() {
        return numberOut;
    }

    public void setNumberOut(Integer numberOut) {
        this.numberOut = numberOut;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
