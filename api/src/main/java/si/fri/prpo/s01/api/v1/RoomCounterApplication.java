package si.fri.prpo.s01.api.v1;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;


import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;


@DeclareRoles({"user", "admin"})
@OpenAPIDefinition(info = @Info(title = "Room counter API", version = "v1",
            contact = @Contact(email = "prpo@fri.uni-lj.si"),
            license = @License(name = "dev"), description = "API for service Room counter"),
            servers = @Server(url = "http://localhost:8080"))
@ApplicationPath("v1")
@CrossOrigin(supportedMethods="GET, POST, HEAD, OPTIONS, PUT, DELETE")
public class RoomCounterApplication extends javax.ws.rs.core.Application {
}
