package si.fri.prpo.s01.entities;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity(name = "entrance")
@NamedQueries(value =
        {
                @NamedQuery(name = "Entrance.getAll", query = "SELECT e FROM entrance e"),
                @NamedQuery(name = "Entrance.getForRoom", query = "SELECT e FROM entrance e WHERE e.room.id = :roomID")
        })
public class Entrance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "room_id") //poimenuje stolpec room_id v Entrance
    private Room room;

    private String name;

    @JsonbTransient
    @OneToMany(mappedBy = "entrance", cascade = CascadeType.ALL)
    private List<State> stateList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<State> getStateList() {
        return stateList;
    }

    public void setStateList(List<State> stateList) {
        this.stateList = stateList;
    }

    @Override
    public String toString() {
        return "Entrance{" +
                "id=" + id +
                ", name=" + name +
                ", stateList=" + Arrays.toString(stateList.toArray()) +
                '}';
    }
}
