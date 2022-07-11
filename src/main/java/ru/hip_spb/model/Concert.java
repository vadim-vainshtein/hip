package ru.hip_spb.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class Concert {

    private int concert_id;
    private LocalDateTime dateTime;
    private Place place;
    private String programName;
    private String programText;
    private String link;
    private Performer performers[];

    public Concert(int concert_id, LocalDateTime dateTime, Place place, String programName, String programText, String link, Performer[] performers) {
        this.concert_id = concert_id;
        this.dateTime = dateTime;
        this.place = place;
        this.programName = programName;
        this.programText = programText;
        this.link = link;
        this.performers = performers;
    }

    public Concert() {
    
    }
    
    public String getProgramText() {
        return programText;
    }
    
    public void setProgramText(String programText) {
        this.programText = programText;
    }

    public int getConcert_id() {
        return concert_id;
    }

    public void setConcert_id(int concert_id) {
        this.concert_id = concert_id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Performer[] getPerformers() {
        return performers;
    }

    public void setPerformers(Performer[] performers) {
        this.performers = performers;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Concert other = (Concert) obj;
        if (this.concert_id != other.concert_id) {
            return false;
        }
        if (!Objects.equals(this.programName, other.programName)) {
            return false;
        }
        if (!Objects.equals(this.link, other.link)) {
            return false;
        }
        if (!Objects.equals(this.dateTime, other.dateTime)) {
            return false;
        }
        if (!Objects.equals(this.place, other.place)) {
            return false;
        }
        return Arrays.deepEquals(this.performers, other.performers);
    }

    @Override
    public String toString() {
        return "Concert{" + "concert_id=" + concert_id + ", dateTime=" + dateTime + ", place=" + place + ", programName=" + programName + ", link=" + link + ", performers=" + performers + '}';
    }

}