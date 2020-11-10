package si.fri.prpo.s01.beans;

import si.fri.prpo.s01.entitete.Entrance;
import si.fri.prpo.s01.entitete.State;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class StatesBean {

    @PersistenceContext(unitName = "room-counter-jpa")
    private EntityManager em;

    private Logger log = Logger.getLogger(StatesBean.class.getName());

    @PostConstruct
    public void init(){
        log.info("Initializing bean "+ StatesBean.class.getSimpleName());
    }

    @PreDestroy
    public void remove(){
        log.info("Destroying bean" + StatesBean.class.getSimpleName());
    }

    public List<State> getStatesForRoom (int roomID) {

        List<State> states = em.createNamedQuery("State.getAllForRoom")
                .setParameter("roomID", roomID)
                .getResultList();

        return states;
    }

    public State getState(int id){

        State state = em.find(State.class, id);
        return state;
    }

    @Transactional
    public State addState(State state){

        if(state != null)
            em.persist(state);
        return state;
    }

    @Transactional
    public State updateState(int id, State state){

        State s = em.find(State.class, id);
        state.setId(s.getId());
        em.merge(state);
        return state;
    }

    @Transactional
    public void deleteState(int id){

        State state = getState(id);
        if(state != null)
            em.remove(state);
    }
}
