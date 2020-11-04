package si.fri.prpo.s01.entitete;

import javax.persistence.*;

@Entity(name = "state")
@NamedQueries(value =
        {
                @NamedQuery(name = "State.getAll", query = "SELECT e FROM state e")
        })

public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer number_In; //how many enters
    private Integer number_Out; //how many exits
    private Integer date;
    private Integer time;

    @ManyToOne
    @JoinColumn(name = "entrance_id")
    private Entrance entrance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber_In() {
        return number_In;
    }

    public void setNumber_In(Integer number_In) {
        this.number_In = number_In;
    }

    public Integer getNumber_Out() {
        return number_Out;
    }

    public void setNumber_Out(Integer number_Out) {
        this.number_Out = number_Out;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Entrance getEntrance() {
        return entrance;
    }

    public void setEntrance(Entrance entrance) {
        this.entrance = entrance;
    }
}
