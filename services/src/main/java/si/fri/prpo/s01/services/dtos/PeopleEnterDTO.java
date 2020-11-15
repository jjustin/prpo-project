package si.fri.prpo.s01.services.dtos;

public class PeopleEnterDTO {
    private Integer entranceId;
    private Integer number; // number of people entering

    public Integer getEntranceId() {
        return entranceId;
    }

    public void setEntranceId(Integer entranceId) {
        this.entranceId = entranceId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
