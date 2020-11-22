package si.fri.prpo.s01.api.v1;

import si.fri.prpo.s01.api.v1.sources.EntranceSource;
import si.fri.prpo.s01.api.v1.sources.RoomSource;

import javax.ws.rs.ApplicationPath;
import java.util.Set;

@ApplicationPath("v1")
public class RoomCounterApplication extends javax.ws.rs.core.Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new
                java.util.HashSet<Class<?>>();
        resources.add(RoomSource.class);
        resources.add(EntranceSource.class);
        return resources;
    }
}
