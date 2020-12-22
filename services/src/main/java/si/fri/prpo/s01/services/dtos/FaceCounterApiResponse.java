package si.fri.prpo.s01.services.dtos;

import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;

import java.util.ArrayList;
import java.util.List;

public class FaceCounterApiResponse {
    private Object[] faces;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object[] getFaces() {
        return faces;
    }

    public void setFaces(Object[] faces) {
        this.faces = faces;
    }

    public int len(){
        if (faces != null) {
            return faces.length;
        }
        throw new RuntimeException("Faces is null");
    }
}

