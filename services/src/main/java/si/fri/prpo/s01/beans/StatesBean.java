package si.fri.prpo.s01.beans;

import si.fri.prpo.s01.entitete.Entrance;
import si.fri.prpo.s01.entitete.State;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class StatesBean {

    @PersistenceContext(unitName = "room-counter-jpa")
    private EntityManager em;

    public List<State> getStatesForRoom (int roomID) {

        List<State> states = em.createNamedQuery("State.getAllForRoom")
                .setParameter("roomID", roomID)
                .getResultList();

        return states;
    }
}
