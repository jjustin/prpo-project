package si.fri.prpo.s01.services.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.logging.Logger;

public class OccupancyRateBean {

    private Logger log = Logger.getLogger(OccupancyRateBean.class.getName());

    @Inject
    private EntrancesBean entrancesBean;

    @Inject
    private RoomsBean roomsBean;

    @Inject
    private StatesBean statesBean;

    @PostConstruct
    private void Init(){
        log.info("Inicializing bean " + OccupancyRateBean.class.getSimpleName());
    }
    @PreDestroy
    private void Remove(){
        log.info("Distroying bean " + OccupancyRateBean.class.getSimpleName());
    }

    //at least 3 methods
}
