package si.fri.prpo.s01.api.servlets;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.prpo.s01.entitete.Entrance;
import si.fri.prpo.s01.entitete.State;
import si.fri.prpo.s01.services.beans.*;
import si.fri.prpo.s01.entitete.Room;
import si.fri.prpo.s01.services.dtos.AddRoomWithEntrancesDTO;
import si.fri.prpo.s01.services.dtos.CanMoreEnterDTO;
import si.fri.prpo.s01.services.dtos.PeopleEnterDTO;

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
    private EntrancesBean entrancesBean;
    @Inject
    private StatesBean statesBean;
    @Inject
    private OccupancyRateBean occupancyRateBean;
    @Inject
    private RoomManagerBean roomManagerBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final PrintWriter writer = resp.getWriter();
        ConfigurationUtil.getInstance().get("kumuluzee.name").ifPresent(r -> writer.println("Service name: " + r));

        List<Room> rooms = roomsBean.getRooms(null);

        writer.println("\n--------------------- ROOMS listing");

        // izpis uporabnikov na spletno stran
        writer.println("Rooms:");
        for (Room r : rooms) {
            writer.println(String.format("- %s", r));
        }

        // izpis uporabnikov na spletno stran preko criteriaAPI
        rooms = roomsBean.getRoomsCriteriaAPI();
        writer.println("\nRoomsCriteriaAPI:");
        for (Room r : rooms) {
            writer.println(String.format("- %s", r));
        }

        // izpis vhodov za sobo 1
        List<Entrance> entrances = entrancesBean.getEntrancesForRoom(1);
        writer.println("\nEntrances for room 1:");
        for (Entrance e : entrances) {
            writer.println(String.format("- %s", e.toString()));
        }
        List<State> states = statesBean.getStatesForRoom(1);
        writer.println("\nStates in room 1: " + Arrays.toString(states.toArray()));

        writer.println("\n--------------------- OWNERS");

        List<String> owners = roomsBean.getOwners();
        writer.write(Arrays.toString(owners.toArray()));

        writer.println("\n--------------------- ROOM CRUD");

        // Dodaj sobo
        Room r = new Room();
        r.setName("New Name");
        r.setInRoom(0);
        r.setSize(12);
        r.setOwner("Matjaz");
        r = roomsBean.addRoom(r);
        // izpis nove sobe
        writer.print("\nNew room:\n- ");
        r = roomsBean.getRoom(r.getId());
        writer.println(r);

        // Posodobi sobo
        r.setOwner("Tanja");
        r.setName("Novo ime");
        r = roomsBean.updateRoom(r.getId(), r);

        // izpis posodobljene sobe
        writer.print("\nUpdated room:\n- ");
        r = roomsBean.getRoom(r.getId());
        writer.println(r);

        writer.println("\nDeleted room");
        roomsBean.deleteRoom(r.getId());

        writer.println("\n--------------------- OCCUPACY RATE BEAN");

        // people enter through enterance 1
        PeopleEnterDTO peopleEnterDTO = new PeopleEnterDTO();
        peopleEnterDTO.setEntranceId(1);

        CanMoreEnterDTO canMoreEnterDTO = new CanMoreEnterDTO();
        canMoreEnterDTO.setRoomId(1);

        if (occupancyRateBean.canMoreEnter(canMoreEnterDTO)) {
            writer.println("Room is not full, 1 person will enter");
            peopleEnterDTO.setNumber(1);
            occupancyRateBean.peopleEnter(peopleEnterDTO);
        } else {
            writer.println("Room is full, 1 person will exit");
            peopleEnterDTO.setNumber(1);
            occupancyRateBean.peopleExit(peopleEnterDTO);
        }

        writer.println("\n--------------------- ROOM MANAGER BEAN");
        AddRoomWithEntrancesDTO sloncki = new AddRoomWithEntrancesDTO("Igralnica sloncki", 4, "Nada");
        sloncki.addEntrance("Igrisce");
        sloncki.addEntrance("Hodnik");

        Room slonckiRoom = roomManagerBean.addRoomWithEntrances(sloncki);

        writer.write("new room:");
        writer.write(slonckiRoom.toString());
    }
}
