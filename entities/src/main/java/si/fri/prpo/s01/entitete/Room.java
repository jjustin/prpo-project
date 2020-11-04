package si.fri.prpo.s01.entitete;

import javax.persistence.*;
import java.util.List;

@Entity(name = "room")
@NamedQueries(value =
        {
                @NamedQuery(name = "Room.getAll", query = "SELECT r FROM room r")
                //se dodej neke te queries
        })
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer size;

    private String owner;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Entrance> entranceList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
