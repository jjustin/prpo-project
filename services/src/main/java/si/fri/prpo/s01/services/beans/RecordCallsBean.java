package si.fri.prpo.s01.services.beans;

import si.fri.prpo.s01.services.annotations.RecordCalls;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class RecordCallsBean {

    private Logger log = Logger.getLogger(EntrancesBean.class.getName());
    private UUID uuid = UUID.randomUUID();
    private Map<String, Integer> numberOfCalls;

    @PostConstruct
    public void init(){

        log.info("Initializing bean "+ RecordCallsBean.class.getSimpleName());
        numberOfCalls = new HashMap<String, Integer>();
    }

    @PreDestroy
    public void remove(){
        log.info("Destroying bean" + RecordCallsBean.class.getSimpleName());
    }


    public void increase(String methodName){
        Integer currNo = numberOfCalls.get(methodName);

        if(currNo != null){
            numberOfCalls.replace(methodName, currNo + 1);
            log.info("Number of calls for method "+ methodName + "is"+ (currNo + 1));
        }
        else{
            numberOfCalls.put(methodName, 1);
            log.info("Number of calls for method "+ methodName + "is " + 1);
        }
    }
}
