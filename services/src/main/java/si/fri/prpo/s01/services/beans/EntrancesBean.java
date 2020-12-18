package si.fri.prpo.s01.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.s01.entities.Entrance;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class EntrancesBean {

    @PersistenceContext(unitName = "room-counter-jpa")
    private EntityManager em;

    private Logger log = Logger.getLogger(EntrancesBean.class.getName());

    @PostConstruct
    public void init(){
        log.info("Initializing bean "+ EntrancesBean.class.getSimpleName());
    }

    @PreDestroy
    public void remove(){
        log.info("Destroying bean" + EntrancesBean.class.getSimpleName());
    }


    public List<Entrance> getEntrances(QueryParameters query) {
        List<Entrance> entrances = JPAUtils.queryEntities(em, Entrance.class, query);
        return entrances;
    }

    public long countEntrances(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Entrance.class, query);
    }

    public List<Entrance> getEntrancesForRoom (int roomID) {

        List<Entrance> entrances = em.createNamedQuery("Entrance.getForRoom")
                .setParameter("roomID", roomID)
                .getResultList();

        return entrances;
    }

    public Entrance getEntrance(int id){

        Entrance entrance = em.find(Entrance.class, id);
        return entrance;
    }

    @Transactional
    public Entrance addEntrance(Entrance entrance){

        if(entrance != null)
            em.persist(entrance);
        return entrance;
    }

    @Transactional
    public Entrance updateEntrance(int id, Entrance entrance){

        Entrance e = em.find(Entrance.class, id);
        entrance.setId(e.getId());
        em.merge(entrance);
        return entrance;
    }

    @Transactional
    public void deleteEntrance(int id){

        Entrance entrance = getEntrance(id);
        if(entrance != null)
            em.remove(entrance);
    }
}
