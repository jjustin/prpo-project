package si.fri.prpo.s01.entitete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity(name = "room")
@NamedQueries(value =
        {
                @NamedQuery(name = "Room.getAll", query = "SELECT r FROM room r"),
                @NamedQuery(name = "Room.get", query =  "SELECT r FROM room r WHERE r.id = :id"),
                @NamedQuery(name = "Room.getOwners", query =  "SELECT DISTINCT r.owner FROM room r")
                //se dodej neke te queries
        })
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer size;

    @Column(name="in_room")
    private Integer inRoom;

    private String owner;


    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Entrance> entranceList =  new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Entrance> getEntranceList() {
        return entranceList;
    }

    public void setEntranceList(List<Entrance> entranceList) {
        this.entranceList = entranceList;
    }

    public Integer getInRoom() {
        return inRoom;
    }

    public void setInRoom(Integer inRoom) {
        this.inRoom = inRoom;
    }


    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", inRoom='" +  inRoom + '\'' +
                ", size=" + size +
                ", owner='" + owner + '\'' +
                ", entranceList=" +  Arrays.toString(entranceList.toArray()) +
                '}';
    }
}
