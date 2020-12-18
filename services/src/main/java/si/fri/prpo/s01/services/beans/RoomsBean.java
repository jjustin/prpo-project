package si.fri.prpo.s01.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.s01.entities.Entrance;
import si.fri.prpo.s01.entities.Room;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@ApplicationScoped
public class RoomsBean {
    @PersistenceContext(unitName = "room-counter-jpa")
    private EntityManager em;

    private Logger log = Logger.getLogger(RoomsBean.class.getName());

    @PostConstruct
    public void init() {
        log.info("Initializing bean" + RoomsBean.class.getSimpleName());
    }

    @PreDestroy
    public void remove() {
        log.info("Destroying bean" + RoomsBean.class.getSimpleName());
    }

    public List<Room> getRooms(QueryParameters query) {
        List<Room> rooms = JPAUtils.queryEntities(em, Room.class, query);
        return rooms;
    }

    public long countRooms(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Room.class, query);
    }

    public List<String> getOwners() {
        List<String> owners = em.createNamedQuery("Room.getOwners").getResultList();

        return owners;
    }

    public List<Room> getRoomsCriteriaAPI() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Room> q = cb.createQuery(Room.class);
        Root<Room> from = q.from(Room.class);
        q.select(from);

        return em.createQuery(q).getResultList();
    }

    @Transactional
    public Room getRoom(int id) {
        Room room = em.find(Room.class, id);

        return room;
    }

    @Transactional
    public Room addRoom(Room room) {
        if (room != null) {
            em.persist(room);
        }

        return room;
    }

    @Transactional
    public Room updateRoom(int id, Room room) {
        Room oldRoom = em.find(Room.class, id);

        if (room == null) {
            return oldRoom;
        }

        room.setId(oldRoom.getId());
        if (room.getName() == null) {
            room.setName(oldRoom.getName());
        }
        if (room.getInRoom() == null) {
            room.setInRoom(oldRoom.getInRoom());
        }
        if (room.getSize() == null) {
            room.setSize(oldRoom.getSize());
        }
        if (room.getOwner() == null) {
            room.setOwner(oldRoom.getOwner());
        }

        // combine room's entrance list without duplicates
        Set<Entrance> set = new LinkedHashSet<>(oldRoom.getEntranceList());
        set.addAll(room.getEntranceList());
        room.setEntranceList(new ArrayList<>(set));

        em.merge(room);

        return room;
    }

    @Transactional
    public void deleteRoom(int id) {
        Room room = getRoom(id);
        if (room != null) {
            em.remove(room);
        }
    }
}
