package si.fri.prpo.s01.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity(name = "state")
@NamedQueries(value =
        {
                @NamedQuery(name = "State.getAll", query = "SELECT s FROM state s"),
                @NamedQuery(name = "State.getAllForEntrance", query = "SELECT s FROM state s WHERE s.entrance.id = :eID"),
                @NamedQuery(name = "State.getAllForRoom", query =  "SELECT s FROM state s WHERE s.entrance.room.id = :roomID"),
                @NamedQuery(name = "State.getOrderedDescForRoom", query = "SELECT s FROM state s WHERE s.entrance.room.id = :roomID ORDER BY s.date DESC, s.time DESC")
        })

public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer numberIn = 0; //how many enters
    private Integer numberOut = 0; //how many exits
    private Integer currentlyIn = 0; // how many people are in a room when this state is created
    private Date date;
    private Time time;

    @ManyToOne
    @JoinColumn(name = "entrance_id")
    private Entrance entrance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Entrance getEntrance() {
        return entrance;
    }

    public void setEntrance(Entrance entrance) {
        this.entrance = entrance;
    }

    public Integer getCurrentlyIn() { return currentlyIn; }

    public void setCurrentlyIn(Integer currentlyIn) { this.currentlyIn = currentlyIn; }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", numberIn=" + numberIn +
                ", numberOut=" + numberOut +
                ", currentlyIn=" + currentlyIn +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
