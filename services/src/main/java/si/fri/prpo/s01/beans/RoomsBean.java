package si.fri.prpo.s01.beans;

import si.fri.prpo.s01.entitete.Room;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class RoomsBean {
    @Inject
    private EntrancesBean entrances;

    @PersistenceContext(unitName = "room-counter-jpa")
    private EntityManager em;

    public List<Room> getRooms () {

        List<Room> rooms = em.createNamedQuery("Room.getAll").getResultList();

        return rooms;
    }

    public Room getRoom (int id) {

        Room room = (Room)em.createNamedQuery("Room.get")
                .setParameter("id", id)
                .getSingleResult();

        room.setEntranceList(entrances.getEntrancesForRoom(id));

        return room;
    }


    public List<Room> getRoomsCriteriaAPI(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Room> q = cb.createQuery(Room.class);
        Root<Room> from = q.from(Room.class);
        q.select(from);

        return em.createQuery(q).getResultList();
    }


}
