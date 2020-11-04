package si.fri.prpo.s01.beans;

import si.fri.prpo.s01.entitete.Entrance;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class EntrancesBean {

    @PersistenceContext(unitName = "room-counter-jpa")
    private EntityManager em;

    public List<Entrance> getEntrancesForRoom (int roomID) {

        List<Entrance> entrances = em.createNamedQuery("Entrance.getForRoom")
                .setParameter("roomID", roomID)
                .getResultList();

        return entrances;
    }
}
