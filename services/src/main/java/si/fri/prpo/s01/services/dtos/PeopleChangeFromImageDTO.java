package si.fri.prpo.s01.services.dtos;

public class PeopleChangeFromImageDTO {
    private String url;
    private boolean peopleEnter; // do people enter or leave
    private Integer entranceId;

    public Integer getEntranceId() {
        return entranceId;
    }

    public void setEntranceId(Integer entranceId) {
        this.entranceId = entranceId;
    }

    public boolean isPeopleEnter() {
        return peopleEnter;
    }

    public void setPeopleEnter(boolean peopleEnter) {
        this.peopleEnter = peopleEnter;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
