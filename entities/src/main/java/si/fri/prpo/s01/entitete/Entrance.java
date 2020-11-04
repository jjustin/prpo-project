package si.fri.prpo.s01.entitete;

import javax.persistence.*;
import java.util.List;

@Entity(name = "entrance")
@NamedQueries(value =
        {
                @NamedQuery(name = "Entrance.getAll", query = "SELECT e FROM entrance e")
        })
public class Entrance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "room_id") //poimenuje stolpec room_id v Entrance
    private Room room;

    @OneToMany(mappedBy = "entrance", cascade = CascadeType.ALL)
    private List<State> stateList;

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
}
