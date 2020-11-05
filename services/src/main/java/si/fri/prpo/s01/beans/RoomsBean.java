package si.fri.prpo.s01.beans;

import si.fri.prpo.s01.entitete.Room;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PostRemove;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class RoomsBean {
    @PersistenceContext(unitName = "room-counter-jpa")
    private EntityManager em;

    private Logger log = Logger.getLogger(RoomsBean.class.getName());

    @PostConstruct
    public void init() {
        log.info("Initialized bean");
    }

    @PreDestroy
    public void remove() {
        log.info("Destroying bean");
    }

    public List<Room> getRooms () {

        List<Room> rooms = em.createNamedQuery("Room.getAll").getResultList();

        return rooms;
    }


    public List<Room> getRoomsCriteriaAPI(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Room> q = cb.createQuery(Room.class);
        Root<Room> from = q.from(Room.class);
        q.select(from);

        return em.createQuery(q).getResultList();
    }

    public Room getRoom (int id) {
        Room room = em.find(Room.class, id);

        return room;
    }

    @Transactional
    public Room addRoom(Room room){
        if (room != null){
            em.persist(room);
        }

        return room;
    }

    @Transactional
    public Room updateRoom(int id, Room room){
        Room oldRoom = em.find(Room.class, id);

        if (room == null){
            return oldRoom;
        }

        room.setId(oldRoom.getId());
        if (room.getName()==null){room.setName(oldRoom.getName());}
        if (room.getInRoom()==null){room.setInRoom(oldRoom.getInRoom());}
        if (room.getSize()==null){room.setSize(oldRoom.getSize());}
        if (room.getOwner()==null){room.setOwner(oldRoom.getOwner());}
        room.getEntranceList().addAll(room.getEntranceList());

        em.merge(room);

        return room;
    }

    @Transactional
    public void deleteRoom(int id){
        em.remove(em.find(Room.class, id));
    }
}
