package si.fri.prpo.s01.services.dtos;

public class PeopleChangeDTO {
    private Integer entranceId;
    private Integer number; // number of people entering/exiting

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
