package si.fri.prpo.s01.servlets;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.prpo.s01.beans.RoomsBean;
import si.fri.prpo.s01.entitete.Room;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {
    @Inject
    private RoomsBean roomsBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final PrintWriter writer = resp.getWriter();
        ConfigurationUtil.getInstance().get("kumuluzee.name").ifPresent(r -> writer.println("Service name: "+ r));

        List<Room> rooms = roomsBean.getRooms();

        // izpis uporabnikov na spletno stran
        writer.println("Rooms:");

        for (Room r: rooms){
            writer.println(String.format("- %s", r.getName()));
        }

    }

}
