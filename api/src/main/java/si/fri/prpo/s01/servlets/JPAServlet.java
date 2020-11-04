package si.fri.prpo.s01.servlets;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.prpo.s01.beans.RoomsBean;
import si.fri.prpo.s01.beans.StatesBean;
import si.fri.prpo.s01.entitete.Room;
import si.fri.prpo.s01.entitete.State;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {
    @Inject
    private RoomsBean roomsBean;
    @Inject
    private StatesBean statesBean;

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

        // izpis uporabnikov na spletno stran preko criteriaAPI
        rooms = roomsBean.getRoomsCriteriaAPI();
        writer.println("\nRoomsCriteriaAPI:");
        for (Room r: rooms){
            writer.println(String.format("- %s", r.getName()));
        }

        // izpis ene sobe
        writer.print("\nRoom id=1:\n- ");
        Room r = roomsBean.getRoom(1);
        writer.println(r);

        List<State> states = statesBean.getStatesForRoom(1);
        writer.println("States: " + Arrays.toString(states.toArray()));
    }
}
